package ArcticJarLIB.Voxis.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.util.LinkedHashMap;

public class VOXRegister implements VOXCommand {

    @Override
    public boolean isCheat() {
        return true;
    }

    @Override
    public VOXSource getPermission() {
        return VOXSource.PROGRAM;
    }

    @Override
    public String getName() {
        return "vox_register";
    }

    @Override
    public String toString(Object[] args) {
        return "VOXCommand \"vox_register\" that adds your command to registry.";
    }

    @Override
    public VOXCommand runCommand(VOXSource source, LinkedHashMap registry,String[] packages, Object byWho, VOXSystem VOX, App App, Object[] args) throws RuntimeException {

        if (args == null || args.length < 1 || !(args[0] instanceof String || args[0] instanceof VOXCommand) || registry == null) {
            throw new VOXSystemCommandInvalidArgs();
        }
        if (!(args[0] instanceof VOXCommand) && !(args[1] instanceof VOXCommand)) {
            throw new VOXSystemCommandInvalidArgs();
        }

        if (!(args[0] instanceof VOXCommand)) {
            if (!registry.isEmpty() && registry.get(args[0]) != null) {
                return null;
            }
        }

        if (args[0] instanceof String) {
            registry.put(args[0], args[1]);
            return (VOXCommand) registry.get(args[0]);
        } else {

            VOXCommand voxCommand = (VOXCommand) args[0];
            registry.put(voxCommand.getName(), voxCommand);
            return voxCommand;

        }
    }

}
