package ArcticJarLIB.Voxis.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Exceptions.VOXSystemErrorsRoot;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXCommandArgument;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.util.LinkedHashMap;

public class DebugMode implements VOXCommand<Void> {

    @Override
    public boolean isCheat() {
        return false;
    }

    @Override
    public VOXSource getPermission() {
        return VOXSource.NONAME;
    }

    @Override
    public String getName() {
        return "vox_debug";
    }

    @Override
    public String toString(VOXCommandArgument[] args) {
        return "VOXCommand \"vox_debug\" that toggles the cheats boolean variable of VOXSystem.";
    }

    @Override
    public Void runCommand(VOXSource source, LinkedHashMap<String, Object> registry, String[] packages, Object byWho, VOXSystem VOX, App App, VOXCommandArgument[] args) {

        try {

            if (args == null || args.length == 0) {
                VOX.setDebugStatus(!VOX.getDebugStatus());
                return null;
            }

            if (args[0].getType() instanceof Integer intArg0) {
                boolean Tb;
                if (intArg0 == 1) {
                    Tb = false;
                } else {
                    Tb = true;
                }
                VOX.setDebugStatus(Tb);
                return null;
            }

            if (args[0].getType() instanceof Boolean booleanArg0) {
                boolean Tb = booleanArg0;
                VOX.setDebugStatus(Tb);
                return null;
            } else {throw new VOXSystemCommandInvalidArgs();}

        } catch (VOXSystemErrorsRoot e) {
            System.out.println(e);
            throw new VOXSystemErrorsRoot(e.toString());
        }
    }
}