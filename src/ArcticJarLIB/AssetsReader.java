package ArcticJarLIB;

import ArcticJarLIB.App.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AssetsReader implements loggable{

    private static boolean isDebugMode;
    private ArrayList<String> log = new ArrayList<>();

    private String path;
    private SystemContainer container;

    public AssetsReader(String path, SystemContainer container) {this.path = path; this.container = container;}

    public Optional<String[]> readFromRoot() throws IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(this.path);

        if (inputStream == null) { return Optional.empty(); }

        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        List<String> linesList = new ArrayList<>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            linesList.add(line);
        }

        bufferedReader.close();

        String[] linesArray = linesList.toArray(String[]::new);
        return Optional.of(linesArray);

    }

    public Optional<byte[]> readFromRootAsBytes() throws IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(this.path);

        if (inputStream == null) { return Optional.empty(); }

        return Optional.of(inputStream.readAllBytes());

    }

    @Override
    public String logPrefix() {
        return "Assets reader: ";
    }

    @Override
    public ArrayList<String> getLog() {
        return this.log;
    }

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
    public void logUpdate() throws IOException {}

    @Override
    public void setLogMaxSize(Integer size) {}

    @Override
    public Integer getLogMaxSize() { return 50; }

    @Override
    public void setDebugStatus(boolean isLogDebug) {
        this.isDebugMode = isLogDebug;
    }

    @Override
    public boolean getDebugStatus() {
        return this.isDebugMode;
    }

}