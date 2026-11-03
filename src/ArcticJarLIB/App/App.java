//JAVA17
package ArcticJarLIB.App;

import ArcticJarLIB.AJSystem;
import ArcticJarLIB.Exceptions.AppAdminsPermissionsRequired;
import ArcticJarLIB.Exceptions.AppFolderNotExists;
import ArcticJarLIB.Exceptions.ConfirmRequested;
import ArcticJarLIB.Exceptions.LogFileIsNotExists;
import ArcticJarLIB.SystemContainer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

public class App extends AJSystem {

    private static boolean isDebugMode;
    private ArrayList<String> log = new ArrayList<>();
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String dateTime = LocalDateTime.now().format(formatterTime);
    String date =  LocalDateTime.now().format(formatterDate);

    private final String userHome = System.getProperty("user.home");
    private final String programFiles = System.getenv("ProgramFiles");
    private final String programFiles86 = System.getenv("ProgramFiles(x86)");
    private final String appDataLocal = System.getenv("LOCALAPPDATA");
    private final String appDataRoaming = System.getenv("APPDATA");

    private final String appName;
    private final String appIdName;
    private final String version;
    private final String company;

    /**
     * Type of app folder path
     */
    public enum appDirectoryType {
        PATH,
        USERFOLDER,
        PROGRAMFILES,
        PROGRAMFILES86,
        DOC,
        APPDATA_LOCAL,
        APPDATA_ROAMING
    }

    private Path appPath;

    private ImageIcon appIcon;
    private ImageIcon squareBan;
    private ImageIcon verticalBan;
    private ImageIcon horizontalBan;

    private CFGManager cfgManager;
    private LinkedHashMap<String, CFGParam> appConfigData;

    /**
     * App - register the app.
     * @param appName Name of the app
     * @param appIdName Name of the app folder and related items
     * @param version Version of your app
     * @param company Name of your company/Delevopers Team/related items. Your program's folder will be created as a subfolder of your company's folder.; Optional
     * @param appDirectoryType Type of app folder path
     * @param appPath Path of app WITHOUT APP NAME
     * @param appIcon Icon of your app; Optional
     * @param squareBan Icon of square form banner; Optional
     * @param verticalBan Icon of vertical form banner; Optional
     * @param horizontalBan Icon of horizontal form banner; Optional
     */
    public App(String appName,
               String appIdName,
               appDirectoryType appDirectoryType,
               String version,
               String company,
               String appPath,
               ImageIcon appIcon,
               ImageIcon squareBan,
               ImageIcon verticalBan,
               ImageIcon horizontalBan,
               SystemContainer container) throws IOException {

        this(appName,
                appIdName,
                appDirectoryType,
                version,
                company,
                appPath,
                appIcon,
                squareBan,
                verticalBan,
                horizontalBan,
                null,
                null,
                container);

    }


    /**
     * App - register the app.
     * @param appName Name of the app
     * @param appIdName Name of the app folder and related items
     * @param version Version of your app
     * @param company Name of your company/Delevopers Team/related items. Your program's folder will be created as a subfolder of your company's folder.; Optional
     * @param appDirectoryType Type of app folder path
     * @param appPath Path of app WITHOUT APP NAME
     * @param appIcon Icon of your app; Optional
     * @param squareBan Icon of square form banner; Optional
     * @param verticalBan Icon of vertical form banner; Optional
     * @param horizontalBan Icon of horizontal form banner; Optional
     */
    public App(String appName,
               String appIdName,
               appDirectoryType appDirectoryType,
               String version,
               String company,
               String appPath,
               ImageIcon appIcon,
               ImageIcon squareBan,
               ImageIcon verticalBan,
               ImageIcon horizontalBan,
               LinkedHashMap<String, CFGParam> appConfigData,
               CFGManager cfgManager,
               SystemContainer container) throws IOException {

        this.appName = appName;
        this.appIdName = appIdName;
        this.version = version;
        this.company = company;

        if (!(appPath == null) && appDirectoryType == App.appDirectoryType.PATH) {
            if (this.company == null) {
                this.appPath = Path.of(appPath, this.appIdName);
            } else {this.appPath = Path.of(appPath, this.company, this.appIdName);}
        } else {
            switch (appDirectoryType) {
                case PATH -> { throw new NullPointerException(); }
                case USERFOLDER -> {
                    this.appPath = Path.of(userHome);
                }
                case PROGRAMFILES -> {
                    if (!isAdmin()) { throw new AppAdminsPermissionsRequired(); }
                    this.appPath = Path.of(programFiles);
                }
                case PROGRAMFILES86 -> {
                    if (!isAdmin()) { throw new AppAdminsPermissionsRequired(); }
                    this.appPath = Path.of(programFiles86);
                }
                case DOC -> {
                    this.appPath = Path.of(userHome, "Documents");
                }
                case APPDATA_LOCAL -> {
                    this.appPath = Path.of(appDataLocal);
                }
                case APPDATA_ROAMING -> {
                    this.appPath = Path.of(appDataRoaming);
                }
            }
            if (this.company == null) { this.appPath = Path.of(String.valueOf(this.appPath), this.appIdName); }
            else { this.appPath = Path.of(String.valueOf(this.appPath), this.company, this.appIdName); }
        }

        this.appIcon = appIcon;
        this.squareBan = squareBan;
        this.verticalBan = verticalBan;
        this.horizontalBan = horizontalBan;
        if (appConfigData != null) {
            this.appConfigData = appConfigData;
        } else { this.appConfigData = new LinkedHashMap<>(); }

        try {
            log("/--------------------------------------------------------------------=", false);
            log("| App has been created; NEW PROGRAM START: " + appName + " (" + appIdName + ")", false);
            log("\\--------------------------------------------------------------------=", false);

        } catch (IOException ignore) {}
        container.put(SystemContainer.Target.APP, this);
        if (cfgManager != null) {
            this.cfgManager = cfgManager;
        } else {
            try {

                this.cfgManager = new CFGManager(appConfigData, container);

            } catch (IOException ignore) {}
        }
    }

    public enum getMethod {
        NAME,
        IDNAME,
        PATH,
        PATHSTRING,
        ICON,
        SQUAREBANNER,
        VERTICALBANNER,
        HORIZONTALBANNER,
        CFGList,
        CFGManager
    }
    public Object get(getMethod getMethod) throws IOException {
        log("Method get() been summoned", true);
        switch (getMethod) {
            case NAME -> { return this.appName; }
            case IDNAME -> { return this.appIdName; }
            case PATH -> { return this.appPath; }
            case PATHSTRING -> { return String.valueOf(this.appPath); }
            case ICON -> { return this.appIcon; }
            case SQUAREBANNER -> { return this.squareBan; }
            case VERTICALBANNER -> { return this.verticalBan; }
            case HORIZONTALBANNER -> { return this.horizontalBan; }
            case CFGList -> { return this.appConfigData; }
            case CFGManager -> { return this.cfgManager; }
            default -> { return null; }
        }
    }

    /**
     * Setups app folder
     */
    public void setupAppFolder() throws IOException {
        if (!Files.exists(this.appPath)) { Files.createDirectories(this.appPath); log("App folder has been created", false); }
    }

    /**
     * Setups app password .txt document
     */
    public void setupAppPassport() throws IOException, AppFolderNotExists {

        if (Files.exists(this.appPath)) {

            List<String> setupAppPasswordContent = List.of(this.appName, this.appIdName, this.appPath.toString(),
                    String.valueOf(this.appIcon), String.valueOf(this.squareBan),
                    String.valueOf(this.verticalBan), String.valueOf(this.horizontalBan));

            Files.write(this.appPath.resolve("AppPassport.txt"), setupAppPasswordContent);

            log("App passport has been created", false);
        } else {
            throw new AppFolderNotExists();
        }

    }

    /**
     * Creates you log file
     */
    public void setupLogFile() throws IOException, AppFolderNotExists {

        if (Files.exists(this.appPath)) {

            date = LocalDateTime.now().format(formatterDate);
            Files.createDirectories(this.appPath.resolve("Logs"));
            if (!Files.exists(this.appPath.resolve(Path.of("Logs", (date + ".log"))))) {Files.createFile(this.appPath.resolve(Path.of("Logs", (date + ".log")))); }
            log("Log file has been created", false);

        } else {
            throw new AppFolderNotExists();
        }

    }

    @Override
    public String logPrefix() { return ""; }

    public ArrayList<String> getLog() { return this.log; }
    public File getLogFile() {
        return this.appPath.resolve(Path.of("Logs", (date + ".log"))).toFile();
    }

    /**
     * Log your message
     */
    public void log(String message, Boolean isDebugMessage) throws IOException {
        if (isDebugMessage == false || getDebugStatus()) {
            dateTime = LocalDateTime.now().format(formatterTime);
            this.log.add("[" + dateTime + "]" + " " + message);
        }

        logUpdate();
    }

    /**
     * Updates your log
     */
    public void logUpdate() throws IOException, LogFileIsNotExists {

        if (Files.exists(this.appPath.resolve("Logs"))) {
            Path lastLog;

            try (var stream = Files.list(this.appPath.resolve("Logs"))) {
                lastLog = stream
                        .filter(Files::isRegularFile)
                        .max(Comparator.comparingLong(p -> {
                            try {
                                return Files.getLastModifiedTime(p).toMillis();
                            } catch (IOException e) {
                                return 0L;
                            }
                        }))
                        .orElse(null);
            }

            if (lastLog != null && Files.exists(lastLog)) {
                Files.write(lastLog, this.log, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                this.log.clear();
            }
        }
    }

    @Override
    public void setLogMaxSize(Integer size) {}

    @Override
    public Integer getLogMaxSize() { return null; }

    /**
     * Uninstall all logs from user computer and logs folder
     */
    public void logsClear(String CONFIRM) throws ConfirmRequested, IOException, AppFolderNotExists {
        if (Files.exists(this.appPath.resolve("logs"))) {
            if (CONFIRM.equals("CONFIRM")) {

                try (var stream = Files.walk(this.appPath.resolve("logs"))) {
                    stream.sorted(Comparator.reverseOrder())
                            .forEach(p -> {
                                try {
                                    Files.delete(p);
                                } catch (IOException ignore) {
                                }
                            });
                }
            } else {
                throw new ConfirmRequested();
            }
        } else {
            throw new AppFolderNotExists("You can`t uninstall your log folder if it not exists");
        }
    }

    @Override
    public void setDebugStatus(boolean isLogDebug) {
        this.isDebugMode = isLogDebug;
    }

    @Override
    public boolean getDebugStatus() {
        return this.isDebugMode;
    }

    /**
     * Uninstall your program folder (appPath)
     * @param CONFIRM,ConfirmRequested You need to confirm your choose.
     */
    public void uninstall(String CONFIRM) throws ConfirmRequested, IOException, AppFolderNotExists {
        if (Files.exists(this.appPath)) {
            if (CONFIRM.equals("CONFIRM")) {

                try (var stream = Files.walk(this.appPath)) {
                    stream.sorted(Comparator.reverseOrder())
                            .forEach(p -> {
                                try {
                                    Files.delete(p);
                                } catch (IOException ignore) {
                                }
                            });
                }
            } else {
                throw new ConfirmRequested();
            }
        } else {
            throw new AppFolderNotExists("You can`t uninstall your app if it not exists");
        }
    }

    /**
     * Check is your app has admins permissions
     * @return boolean true or false.
     */
    public boolean isAdmin() throws IOException {
        try {
            Process process = new ProcessBuilder("cmd.exe", "/c", "net session")
                    .start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) { log("Failed to check is app has admins permissions:" + e.getMessage() + "Returns false", false); }
        return false;
    }
}