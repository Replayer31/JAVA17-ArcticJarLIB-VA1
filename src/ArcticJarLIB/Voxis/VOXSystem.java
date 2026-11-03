package ArcticJarLIB.Voxis;

import ArcticJarLIB.AJSystem;
import ArcticJarLIB.Exceptions.*;
import ArcticJarLIB.App.*;
import ArcticJarLIB.SystemContainer;
import ArcticJarLIB.Voxis.VOXCommands.VOXRegister;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.lang.reflect.Constructor;

public class VOXSystem extends AJSystem {

    private static boolean isDebugMode = false;
    private ArrayList<String> log = new ArrayList<>();
    private boolean cheats;
    private SystemContainer container;
    private Object lastReturned;
    private Integer logMaxSize = 150;
    private App app;

    /**
     * VOXSystem - console system that can run commands
     * @param cheats VOXSystem has cheats variable. Some commands can`t be run if cheats are disabled
     * @param VOXLogMaxSize Max size of VOXSystem log
     * @param container Your system container
     */
    public VOXSystem
            (
                    boolean cheats,
                    Integer VOXLogMaxSize,
                    SystemContainer container
            )
    {
        this.cheats = cheats;
        this.setLogMaxSize(VOXLogMaxSize);
        this.container = container;
        this.app = (App) container.get(SystemContainer.Target.APP).orElse(null);
        container.add(this);
    }

    /**
     * Run something command
     * @param source Permission value of source
     * @param registry Registry of commands objects
     * @param byWho Who is the runner?
     * @param input Your command
     * @return Optional containing command result if present, empty Optional otherwise
     */
    public Optional<Object> run(VOXSource source, LinkedHashMap<String, Object> registry, String[] commandsPackages, Object byWho, String input) throws IOException {
        try {
            String commandObject = Pattern.compile(" ").splitAsStream(input.trim())
                    .findFirst().orElse("");

            String argsPart = Pattern.compile(" ").splitAsStream(input.trim())
                    .skip(1).collect(Collectors.joining(" ")).lines().collect(Collectors.joining(""));

            Object[] args = Pattern.compile(" ")
                    .splitAsStream(argsPart)
                    .filter(s -> !s.isBlank())
                    .map(s -> {
                        try {
                            return parseAuto(s, commandsPackages);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray();

            return run(source, registry, commandsPackages, byWho, commandObject, args);
        } catch (Exception e) {
            log("Failed to run \"" + input + "\" Exception: " + e.getMessage(), false);
        }
        return Optional.empty();
    }

    private Object parseAuto(String valueInput, String[] commandsPackages) throws IOException {

        if (valueInput.startsWith("<IGNORE>")) {
            valueInput = valueInput.replaceFirst("<IGNORE>", "");
            log("parseAuto() command argument " + valueInput + " has been ignored", true);
            return valueInput;
        }


        if (valueInput.equals("null") || valueInput.startsWith("<NULL>")) {
            log("parseAuto() according to plan returns NULL", true);
            return null;
        }

        try {
            if (valueInput.startsWith("<int>")) {
                valueInput = valueInput.replaceFirst("<int>", "");
                int target = Integer.parseInt(valueInput);
                log("parseAuto() returns int", true);
                return target;
            }
        } catch (NumberFormatException ignored) {  }

        Object value = valueInput;

        if (valueInput.startsWith("<get.")) {
            try {
                String get = Pattern.compile("\\.")
                        .splitAsStream(valueInput)
                        .skip(2).collect(Collectors.joining()).replaceAll(">", ""); //.collect(Collectors.joining()) .String()

                log("parseAuto() <get. detected", true);

                if (valueInput.startsWith("<get.last")) {

                    value = getLastReturned();
                    log("parseAuto() <get.last detected", true);

                }

                if (valueInput.startsWith("<get.vox.")) {

                    //value = valueInput.replace("<get.vox." + get + ">", "");

                    switch (get.toLowerCase()) {
                        case "vox" -> { value = this; }
                        case "voxlog" -> { value = this.log; }
                        case "cheats" -> { value = this.cheats; }
                        case "voxlogmaxsize" -> { value = this.getLogMaxSize(); }
                        case "source.program" -> { value = VOXSource.PROGRAM; }
                        case "source.host" -> { value = VOXSource.HOST; }
                        case "source.noname" -> { value = VOXSource.NONAME; }
                        default -> {  }
                    }
                }

                if (valueInput.startsWith("<get.app.")) {

                    //value = valueInput.replace("<get.app." + get + ">", "");

                    switch (get.toLowerCase()) {
                        case "app" -> { value = app; }
                        case "appname" -> { value = app.get(App.getMethod.NAME); valueInput = String.valueOf(app.get(App.getMethod.NAME)); }
                        case "appidname", "appid" -> { value = app.get(App.getMethod.IDNAME); valueInput = String.valueOf(app.get(App.getMethod.IDNAME)); }
                        case "apppath.string" -> { value = app.get(App.getMethod.PATH); valueInput = String.valueOf(app.get(App.getMethod.PATHSTRING)); }
                        case "apppath" -> { value = app.get(App.getMethod.PATH); }
                        case "appicon" -> { value = app.get(App.getMethod.ICON); }
                        case "appsquare" -> { value = app.get(App.getMethod.SQUAREBANNER); }
                        case "appvert" -> { value = app.get(App.getMethod.VERTICALBANNER); }
                        case "apphorisont" -> { value = app.get(App.getMethod.HORIZONTALBANNER); }
                        case "cfg", "cfgman", "cfgmanager" -> { value = app.get(App.getMethod.CFGManager); }
                        case "cfglist", "cfgmanlist", "cfgmanagerlist" -> { value = app.get(App.getMethod.CFGList); }
                        case "log" -> { value = app.getLog(); }
                        case "logfile" -> { value = app.getLogFile(); }
                        default -> {  }
                    }

                }

                if (valueInput.startsWith("<get.new.")) {

                    //value = valueInput.replace("<get.new." + get + ">", "");

                    log("parseAuto() <get.new detected", true);

                    String getClazz = Pattern.compile("\\.")
                            .splitAsStream(valueInput.replaceAll("<", "").replaceAll(">", ""))
                            .skip(2).collect(Collectors.joining("."));

                    value = Class.forName(getClazz).getDeclaredConstructor().newInstance();

                    log("parseAuto() returns \"" + value + "\" as " + value.getClass().getName(), true);
                    return value;
                }

                log("parseAuto() returns \"" + value + "\" as " + value.getClass().getName(), true);
                return value;

            } catch (Exception e) {
                log("Failed to parse argument \"" + valueInput + "\": " + e.getMessage(), true);
                String retVar = Pattern.compile("\\.")
                        .splitAsStream(valueInput.replaceAll("<", "").replaceAll(">", ""))
                        .skip(2).collect(Collectors.joining("."));
                log("parseAuto() returns \"" + retVar + "\" as String", true);
                return retVar; }
        }

        if (value.toString().startsWith("<")) {
            int closingAngle = value.toString().indexOf(">");
            if (closingAngle != -1) {

                String className = value.toString().substring(1, closingAngle).trim();

                String argsPart = value.toString().substring(closingAngle + 1).trim();

                String args = argsPart
                        .replaceAll("^(List|Set|Arrays)\\.of\\((.*)\\)$", "$2")
                        .replaceAll("^\"|\"$", "");

                List<String> parsedArgs = Pattern.compile(",")
                        .splitAsStream(args)
                        .map(String::trim)
                        .map(s -> s.replaceAll("^\"|\"$", ""))
                        .filter(s -> !s.isBlank())
                        .collect(Collectors.toList());

                try {

                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(className);
                    }
                    catch (ClassNotFoundException ignored) {}

                    if (clazz != null) {

                        if (clazz.isEnum()) {
                            String enumValName = parsedArgs.isEmpty() ? argsPart : parsedArgs.get(0);

                            if (enumValName.contains(".")) {
                                enumValName = enumValName.substring(enumValName.lastIndexOf(".") + 1);
                            }

                            Class<Enum> enumClass = (Class<Enum>) clazz;
                            log("parseAuto() returns " + Enum.valueOf(enumClass, enumValName).getClass().getName(), true);
                            return Enum.valueOf(enumClass, enumValName);
                        }

                        if (List.class.isAssignableFrom(clazz)) {
                            log("parseAuto() returns " + parsedArgs.getClass().getName(), true);
                            return parsedArgs;
                        }
                    }
                } catch (Exception ignored) {}

                if (parsedArgs.size() == 1) {
                    log("parseAuto() returns " + parsedArgs.get(0).getClass().getName(), true);
                    return parsedArgs.get(0);
                }
                log("parseAuto() returns " + parsedArgs.getClass().getName(), true);
                return parsedArgs;
            }
        }

        if (valueInput.startsWith("VOXCommand.")) {
            log("parseAuto() returns VOXCommand", true);
            return parseVOXCommand(valueInput.replace("VOXCommand.", ""), commandsPackages).orElse(null);
        }

        if ("true".equalsIgnoreCase(valueInput) || "false".equalsIgnoreCase(valueInput)) {
            log("parseAuto() returns Boolean", true);
            return Boolean.parseBoolean(valueInput);
        }

        try {
            return Integer.parseInt(valueInput);
        } catch (NumberFormatException ignored) {  }

        try {
            return Double.parseDouble(valueInput);
        } catch (NumberFormatException ignored) {  }

        log("parseAuto() returns String: " + value, true);
        return value;
    }

    private Optional<VOXCommand> parseVOXCommand(String cmdName, String[] commandsPackages) throws IOException {
        if (cmdName == null || cmdName.isBlank()) {
            return Optional.empty();
        }

        for (String pkg : commandsPackages) {
            try {
                Class<?> clazz = Class.forName(pkg + cmdName);

                if (VOXCommand.class.isAssignableFrom(clazz)) {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    VOXCommand<?, ?> instance = (VOXCommand<?, ?>) constructor.newInstance();
                    return Optional.of(instance);
                }
            } catch (ClassNotFoundException ignored) {}
            catch (Exception e) {
                log("Failed to instantiate VOXCommand \"" + cmdName + "\": " + e, true);
                break;
            }
        }

        return Optional.empty();
    }

    /**
     * Run something command
     * @param source Permission value of source
     * @param registry Registry of commands objects
     * @param byWho Who is the runner?
     * @param commandObject Command object name from registry
     * @param args Command object arguments
     * @return Optional containing command result if present, empty Optional otherwise
     */
    public <A> Optional<Object> run(VOXSource source, LinkedHashMap<String, Object> registry, String[] commandsPackages, Object byWho, String commandObject, A[] args)

            throws VOXSystemErrorsRoot, VOXSystemInvalidInput, VOXSystemInsufficientPermissions, VOXSystemCommandIsInvalid
            , VOXSystemCommandNotExists, VOXSystemObjectIsNotCommand, VOXSystemCheatCommandIsNotAllowed, IOException {
        log("Run method used. Running " + commandObject + "...", false);
        try {
            if (commandObject != null && !commandObject.isEmpty() && !commandObject.isBlank()) {

                if (registry.containsKey(commandObject)) {

                    Object command = registry.get(commandObject);

                    if (command == null) {
                        throw new VOXSystemCommandIsInvalid();
                    }
                    if (command instanceof VOXCommand<?, ?> commandTarget) {

                        if (commandTarget.isCheat() && !isCheatsAllowed() && source != VOXSource.PROGRAM) {
                            throw new VOXSystemCheatCommandIsNotAllowed();
                        }

                        @SuppressWarnings("unchecked")
                        VOXCommand<?, A> typedCommand = (VOXCommand<?, A>) commandTarget;

                        boolean isSourceHavePermission;
                        if (source != VOXSource.PROGRAM) {
                            if (typedCommand.getPermission() != VOXSource.PROGRAM) {

                                if (source == VOXSource.NONAME && typedCommand.getPermission() == VOXSource.HOST) {
                                    isSourceHavePermission = false;
                                } else {
                                    isSourceHavePermission = true;
                                }

                            } else {
                                isSourceHavePermission = false;
                            }
                        } else {
                            isSourceHavePermission = true;
                        }

                        if (isSourceHavePermission) {
                            Object claimedResult = typedCommand.runCommand(source, registry, commandsPackages, byWho, this, this.app, args);

                            log(typedCommand.getName() + " (" + typedCommand.getClass().getSimpleName() + ".class) " + "has be run.", false);
                            if (claimedResult != null) {
                                log(typedCommand.getName() + " (" + typedCommand.getClass().getSimpleName() + ".class)" + " result: " + claimedResult.getClass().getName(), false);
                            }

                            return Optional.ofNullable(claimedResult);
                        } else {
                            throw new VOXSystemInsufficientPermissions();
                        }

                    } else {
                        throw new VOXSystemObjectIsNotCommand();
                    }

                } else {
                    throw new VOXSystemCommandNotExists();
                }

            } else {
                throw new VOXSystemInvalidInput("Command prompt is empty");
            }
        } catch (Exception e) {
            log("Failed to run \"" + commandObject + "\". Exception detected " + e.getMessage(), false);
            return Optional.empty();
        }
    }

    /**
     * Adds "vox_register" VOXCommand to registry
     */
    public VOXCommand VOXRegister(LinkedHashMap<String, Object> registry) {
        VOXRegister target = new VOXRegister();
        registry.put(target.getName(), target);
        return target;
    }

    /**
     * Set cheats variable for console
     * @param isAllowed Is cheats allowed?; Value for cheats variable
     */
    public void setCheats(boolean isAllowed, VOXSource source) throws VOXSystemInsufficientPermissions {
        if (!(source == VOXSource.NONAME)) {
            this.cheats = isAllowed;
        } else { throw new VOXSystemInsufficientPermissions(); }
    }

    /**
     * Get the cheats boolean variable value
     */
    public boolean isCheatsAllowed() {
        return this.cheats;
    }

    /**
     * Set last returned object
     * @param obj Target
     * @return Returns lastReturned var object
     */
    public Object setLastReturned(Object obj) { lastReturned = obj; return lastReturned; }

    /**
     * Get last returned object
     * @return Returns the last System command "sys_return" result
     */
    public Object getLastReturned() { return lastReturned; }

    @Override
    public String logPrefix() {
        return "VOXSystem: ";
    }


    /**
     * Get the VOXLog list
     */
    @Override
    public ArrayList<String> getLog() {
        return this.log;
    }


    /**
     * Add message to VOXLog
     * @param message Your message
     */
    @Override
    public void log(String message, Boolean isDebugMessage) throws IOException {

        if (isDebugMessage == false || getDebugStatus()) {
            message = logPrefix() + message;
            this.log.add(message);

            App app = (App) container.get(SystemContainer.Target.APP).orElse(null);

            try {
                app.log(message, isDebugMessage);
            } catch (IOException e) {
                this.log.add("Fail to send log into your app. Exception: " + e);
            }

            while (this.log.size() > getLogMaxSize()) {
                this.log.remove(0);
            }

        }

    }

    @Override
    public void logUpdate() {}

    @Override
    public void setLogMaxSize(Integer size) {
        logMaxSize = size;
    }

    @Override
    public Integer getLogMaxSize() {
        return logMaxSize;
    }

    @Override
    public void setDebugStatus(boolean isLogDebug) {
        this.isDebugMode = isLogDebug;
    }

    @Override
    public boolean getDebugStatus() {
        return this.isDebugMode;
    }

}