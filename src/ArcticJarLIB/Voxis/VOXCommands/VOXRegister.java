package ArcticJarLIB.Voxis.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXCommandArgs.TextArg;
import ArcticJarLIB.Voxis.VOXCommandArgs.VOXCommandArg;
import ArcticJarLIB.Voxis.VOXCommandArgument;
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
    public String toString(VOXCommandArgument[] args) {
        return "VOXCommand \"vox_register\" that adds your command to registry.";
    }

    @Override
    public VOXCommand runCommand(VOXSource source, LinkedHashMap registry,String[] packages, Object byWho, VOXSystem VOX, App App, VOXCommandArgument[] args) throws RuntimeException {

        if (args == null || args.length < 1 || !(args[0] instanceof TextArg || args[0] instanceof VOXCommandArg) || registry == null) {
            throw new VOXSystemCommandInvalidArgs();
        }
        if (!(args[0] instanceof VOXCommandArg) && !(args[1] instanceof VOXCommandArg)) {
            throw new VOXSystemCommandInvalidArgs();
        }

        if (!(args[0] instanceof VOXCommandArg)) {
            if (!registry.isEmpty() && registry.get(args[0]) != null) {
                return null;
            }
        }

        if (args[0] instanceof TextArg) {
            registry.put(args[0].getValue(), args[1].getValue());
            return (VOXCommand) registry.get(args[0].getValue());
        } else {

            VOXCommand voxCommand = (VOXCommand) args[0].getValue();
            registry.put(voxCommand.getName(), voxCommand);
            return voxCommand;

        }
    }

}
