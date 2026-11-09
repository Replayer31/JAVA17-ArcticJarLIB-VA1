package ArcticJarLIB.Voxis;

import ArcticJarLIB.Exceptions.*;
import ArcticJarLIB.AssetsReader;
import ArcticJarLIB.SystemContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VOXFile {

    /**
     * Run VOXFile from user PC
     * @param source Permission of object who run this command
     * @param registry Your VOXRegistry of commands
     * @param byWho Who run this command?
     * @param vox Your VOXSystem
     * @param path Path of VOXFile
     */

    public static Object[] runVOXFile(VOXSource source, LinkedHashMap<String, Object> registry, String[] packages, Object byWho, VOXSystem vox, Path path)
            throws IOException {

        if (path == null || vox == null) {
            throw new IllegalArgumentException("Null arguments detected");
        }
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            vox.log("Failed to import VOXFile \"" + path + "\"", false);
            throw new VOXFileNotExists();
        }

        File VOXFile = path.toFile();
        vox.log("Imported for run VOXFile \"" + VOXFile.getName() + "\" from path \"" + String.valueOf(path) + "\"", false);

        if (!VOXFile.getName().endsWith(".VOX")) {
            vox.log("Fail to run VOXFile; Invalid file format", false);
            throw new VOXFileNotExists();
        }

        String[] code = Pattern.compile("\n").splitAsStream(Files.readString(path))
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        Object[] results = new Object[code.length];

        for (int i = 0; i < code.length; i++) {
            String codeLine = code[i].trim();
            try {
                if (!codeLine.startsWith("//")) { results[i] = vox.run(source, registry, packages, byWho, codeLine).orElse(null);}
                else { vox.log("Detected commentary: \"" + codeLine.replaceAll("//", "") + "\"", false); }
            }
            catch (Exception e) {
                vox.log("Failed to run command \"" + codeLine.replaceAll("\\r?\\n", " ").trim() + "\" from VOXFile, " + e, false);
                results[i] = null;
            }
        }

        return results;
    }

    public static Object[] runAssetVOXFile(VOXSource source, LinkedHashMap<String, Object> registry, String[] packages, Object byWho, SystemContainer container, String path)
            throws IOException {

        VOXSystem vox = (VOXSystem) container.get(SystemContainer.Target.VOX).orElse(null);

        if (path == null || vox == null) {
            throw new IllegalArgumentException("Null arguments detected");
        }

        File VOXFile = new File(path);
        vox.log("Imported for run VOXFile \"" + VOXFile.getName() + "\" from assets with path \"" + path + "\"", false);

        if (!VOXFile.getName().endsWith(".VOX")) {
            vox.log("Fail to run VOXFile; Invalid file format", false);
            throw new VOXFileNotExists();
        }

        AssetsReader targetReader = new AssetsReader(path, container);

        String[] code = Arrays.stream(targetReader.readFromRoot().orElse(null))
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining("\n"))
                .split("\n");

        Object[] results = new Object[code.length];

        for (int i = 0; i < code.length; i++) {
            String codeLine = code[i].trim();
            try {
                if (!codeLine.startsWith("//")) { results[i] = vox.run(source, registry, packages, byWho, codeLine).orElse(null);}
                else { vox.log("Detected commentary: \"" + codeLine.replaceAll("//", "") + "\"", false); }
            }
            catch (Exception e) {
                vox.log("Failed to run command \"" + codeLine.replaceAll("\\r?\\n", " ").trim() + "\" from VOXFile, " + e, false);
                results[i] = null;
            }
        }

        return results;

    }

        /**
         * Import VOXFile
         * @param source Permission of object who run this command
         * @param registry Your VOXRegistry of commands
         * @param vox Your VOXSystem
         * @param path Path of VOXFile
         */
    public static File importVOXFile(VOXSource source, LinkedHashMap<String, Object> registry,
                                     VOXSystem vox, Path path) throws IOException {

        if (path == null || vox == null) {
            throw new IllegalArgumentException("Null arguments detected");
        }
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            vox.log("Failed to import VOXFile", false);
            throw new VOXFileNotExists();
        }

        File VOXFile = path.toFile();
        vox.log("Imported VOXFile: " + VOXFile.getName(), false);

        if (!VOXFile.getName().endsWith(".VOX")) {
            vox.log("Fail to run VOXFile; Invalid file format", false);
            throw new VOXFileNotExists();
        }
        return VOXFile;
    }
    /**
     * Import VOXFile content
     * @param source Permission of object who run this command
     * @param registry Your VOXRegistry of commands
     * @param vox Your VOXSystem
     * @param path Path of VOXFile
     * @return Returns content from VOXFile as String[]
     */
    public static String[] importVOXFileContent(VOXSource source, LinkedHashMap<String, Object> registry,
                                                VOXSystem vox, Path path) throws IOException {

        if (path == null || vox == null) {
            throw new IllegalArgumentException("Null arguments detected");
        }
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            vox.log("Failed to import VOXFile", false);
            throw new VOXFileNotExists();
        }

        File VOXFile = path.toFile();
        vox.log("Imported VOXFile content: " + VOXFile.getName(), false);

        if (!VOXFile.getName().endsWith(".VOX")) {
            vox.log("Fail to run VOXFile; Invalid file format", false);
            throw new VOXFileNotExists();
        }

        vox.log("Exported VOXFile content to: " + path, false);
        return Pattern.compile("\n")
                .splitAsStream(Files.readString(path))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

    }

    /**
     * Export VOXFile on user computer
     * @param source Permission of object who run this command
     * @param registry Your VOXRegistry of commands
     * @param vox Your VOXSystem
     * @param path Path of VOXFile
     * @param args VOXFile content
     */
    public static void exportVOXFile(VOXSource source, LinkedHashMap<String, Object> registry,
                                     VOXSystem vox, Path path, String[] args) throws IOException {

        if (args != null && args.length > 0 && vox != null) {

            if (!path.toString().endsWith(".VOX")) {
                vox.log("Failed to export VOXFile; Invalid export format", false);
                throw new IllegalArgumentException();
            }

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            Files.write(path, Arrays.asList(args));

        } else { throw new IllegalArgumentException("Null arguments detected"); }

    }

}