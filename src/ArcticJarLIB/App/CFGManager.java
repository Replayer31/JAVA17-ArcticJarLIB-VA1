package ArcticJarLIB.App;

import ArcticJarLIB.AJSystem;
import ArcticJarLIB.Exceptions.VOXFileNotExists;
import ArcticJarLIB.SystemContainer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import static ArcticJarLIB.App.App.getMethod.PATHSTRING;

public class CFGManager extends AJSystem {

    private static boolean isDebugMode;
    private ArrayList<String> log = new ArrayList<>();

    private App app;
    private final Path cfgPath;
    private LinkedHashMap<String, CFGParam> cfgList;
    private Integer logMaxSize = 150;

    /**
     * Creates CFGManager
     * <p>CFGManager created for manage the settings of your application. It can get parameters, set parameters value, setup settings file end other</p>
     * @param cfgList Registry of parameters
     */
    public CFGManager(LinkedHashMap<String, CFGParam> cfgList, SystemContainer container) throws IOException {
        this.app = (App) container.get(SystemContainer.Target.APP).orElse(null);
        Path appPath = Path.of(this.app.get(PATHSTRING).toString());
        this.cfgPath = appPath.resolve("settings.cfg");
        this.cfgList = cfgList;
        container.add(SystemContainer.Target.CFG, this);
        log("Created", true);
    }

    /**
     * Set the CFGManager app
     */
    public void setApp(App app) { this.app = app; log("Another app selected", true); }

    /**
     * Add CFGParameter to registry
     */
    public void addParam(CFGParam target) { this.cfgList.put(target.getName(), target); log("Parameter \"" + target.getName() + "\" was added to registry", true); }
    /**
     * Set CFGParameter value
     */
    public void setParam(CFGParam target, Object value) { target.setVar(value); log("Parameter " + target.getName() + " changed it value to \"" + value + "\"", true); }
    /**
     * Set CFGParameter value
     */
    public void setParam(String targetName, Object value) { this.cfgList.get(targetName).setVar(value); log("Parameter " + targetName + " changed it value to \"" + value + "\"", true); }

    /**
     * Get CFGParameter from registry
     */
    public CFGParam getParam(String targetName) { log("Returning \"" + targetName + "\"...", true); return this.cfgList.get(targetName); }
    /**
     * Get CFGParameter value from registry
     */
    public Object getParamValue(String targetName) { log("Returning \"" + targetName + "\" value: \"" + this.cfgList.get(targetName).getVar() + "\"", true); return this.cfgList.get(targetName).getVar(); }

    public LinkedHashMap getCFGParamsList() { log("Returning  CFGList...", true); return this.cfgList; }

    /**
     * Setups CFG file.
     * <p>File creates in app folder root</p>
     */
    public void setupCFGFile() throws IOException {

        if (!Files.exists(this.cfgPath)) { Files.createFile(this.cfgPath); } else { Files.delete(this.cfgPath); Files.createFile(this.cfgPath); }

        if (this.cfgList != null) {

            List<String> content = new ArrayList<>();

            for (CFGParam param : this.cfgList.values().stream().toList()) {

                String line = param.getName() + "|" + param.getVar() + ";";
                content.add(line);

            }

            byte[] bytes = String.join("\n", content).getBytes(StandardCharsets.UTF_8);

            Files.write(this.cfgPath, bytes, StandardOpenOption.APPEND);
            log("Settings file updated", false);
        }

    }

    /**
     * Load all CFGParameters from settings file
     */
    public void loadData() throws IOException { loadData(null); }

    /**
     * Load target CFGParameter from settings file
     * @param targetParam Target CFGParameter
     */
    public void loadData(CFGParam targetParam) throws IOException {

        if (this.cfgPath == null) {
            throw new IllegalArgumentException("Null arguments detected");
        }
        if (!Files.exists(this.cfgPath) || !Files.isRegularFile(this.cfgPath)) {
            log("Failed to import settings file", false);
            throw new IOException();
        }

        File target = this.cfgPath.toFile();
        log("Imported settings file for load", false);

        String[] code = Pattern.compile(";").splitAsStream(Files.readString(this.cfgPath))
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        for (int i = 0; i < code.length; i++) {
            String codeLine = code[i];
            try {

                String paramName = Pattern.compile("\\|").splitAsStream(codeLine)
                        .findFirst().orElse(null);
                Object[] paramArgs = Pattern.compile("\\|").splitAsStream(codeLine)
                        .skip(1).toArray();

                if (targetParam == null || paramName.equals(targetParam.getName())) {

                    CFGParam t = this.cfgList.get(paramName);
                    t.setVar(paramArgs);

                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Log message to CFGManager log
     */
    public void log(String message, Boolean isDebugMessage) {

        if (isDebugMessage == false || getDebugStatus()) {
            message = logPrefix() + message;
            this.log.add(message);

            try {
                this.app.log(message, isDebugMessage);
            } catch (IOException e) {
                this.log.add("Fail to send log into your app. Exception: " + e);
            }

            while (this.log.size() > getLogMaxSize()) {
                this.log.remove(0);
            }

        }

    }

    @Override
    public void logUpdate() throws IOException {

    }

    @Override
    public void setLogMaxSize(Integer size) {
        logMaxSize = size;
    }

    @Override
    public Integer getLogMaxSize() {
        return logMaxSize;
    }

    @Override
    public String logPrefix() {
        return "CFGManager: ";
    }

    /**
     * Get CFGManager log
     */
    public ArrayList<String> getLog() { return this.log; }

    @Override
    public void setDebugStatus(boolean isLogDebug) {
        this.isDebugMode = isLogDebug;
    }

    @Override
    public boolean getDebugStatus() {
        return this.isDebugMode;
    }

}
