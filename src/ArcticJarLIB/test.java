package ArcticJarLIB;

import ArcticJarLIB.App.App;
import ArcticJarLIB.App.CFGManager;
import ArcticJarLIB.App.CFGParam;
import ArcticJarLIB.App.TestParam1;
import ArcticJarLIB.Voxis.*;
import ArcticJarLIB.Voxis.VOXCommands.VOXRegister;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static ArcticJarLIB.App.App.appDirectoryType.*;

public class test {
    public static void main(String[] args) throws IOException {

        SystemContainer container = new SystemContainer();

        App test = new App("Test App", ".testappid", DOC, "V1", null, "F:\\.minecraft files", null, null, null, null, container);
        CFGManager cfgManager = (CFGManager) test.get(App.getMethod.CFGManager);
        cfgManager.loadData();
        cfgManager.setupCFGFile();

        test.setupAppFolder();
        test.setupAppPassport();
        test.setupLogFile();

        String[] commandsPackets = {
                "ArcticJarLIB.VOXCommands.",
                "ArcticJarLIB.Graphein.VOXCommands.",
                "ArcticJarLIB.Voxis.VOXCommands.",
                "ArcticJarLIB.App.VOXCommands."};

        VOXSystem VOX = new VOXSystem(true, 150, container);
        LinkedHashMap<String, Object> VOXRegistry = new LinkedHashMap<>();

        VOX.VOXRegister(VOXRegistry);
        Object[] regArray = VOXFile.runAssetVOXFile(VOXSource.PROGRAM, VOXRegistry, commandsPackets,  VOX, container, "Scripts/RegisterAll.VOX");


        VOX.run(VOXSource.PROGRAM, VOXRegistry, commandsPackets,  VOX, "vox_debug 1");

        VOXFile.runAssetVOXFile(VOXSource.PROGRAM, VOXRegistry, commandsPackets,  VOX, container, "Scripts/RegisterAll.VOX");

        for (Object t : container.getContainer().values().stream().toList()) {
            System.out.println("Abobku: " + t.getClass().getName());
        }
        for (String line : VOX.getLog()) {
            System.out.println(line);
        }
        for (String line : cfgManager.getLog()) {
            System.out.println(line);
        }
        for (Object cmd : VOXRegistry.values().stream().toList()) {
            System.out.println("Cmd: " + cmd.getClass().getSimpleName());
        }
    }
}