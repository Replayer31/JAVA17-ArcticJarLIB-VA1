package ArcticJarLIB.Voxis.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Exceptions.VOXSystemErrorsRoot;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXCommandArgument;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.util.LinkedHashMap;

public class Cheats implements VOXCommand<Void> {

    @Override
    public boolean isCheat() {
        return false;
    }

    @Override
    public VOXSource getPermission() {
        return VOXSource.HOST;
    }

    @Override
    public String getName() {
        return "vox_cheats";
    }

    @Override
    public String toString(VOXCommandArgument[] args) {
        return "VOXCommand \"vox_cheats\" that toggles the cheats boolean variable of VOXSystem.";
    }

    @Override
    public Void runCommand(VOXSource source, LinkedHashMap<String, Object> registry, String[] packages, Object byWho, VOXSystem VOX, App App, VOXCommandArgument[] args) {

        try {

            if (args == null || args.length == 0) {
                VOX.setCheats(!VOX.isCheatsAllowed(), source);
                return null;
            }


            if (!(args[0].getType() instanceof Integer) || args.length >= 1) {
                throw new VOXSystemCommandInvalidArgs();
            }


            if (args[0].getType() instanceof Integer intArg0) {
                boolean Tb = intArg0 != 0;
                VOX.setCheats(Tb, source);
            }

            if (args[0].getType() instanceof Boolean booleanArg0) {
                boolean Tb = booleanArg0;
                VOX.setCheats(Tb, source);
            }

        } catch (VOXSystemErrorsRoot e) {
            System.out.println(e);
            throw new VOXSystemErrorsRoot(e.toString());
        }

        return null;
    }
}