package ArcticJarLIB.Voxis.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.io.IOException;
import java.util.LinkedHashMap;

public class VOXLogMessage implements VOXCommand {

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
        return "vox_LogMessage";
    }

    @Override
    public String toString(Object[] args) {
        return "VOXCommand \"vox_LogMessage\" that print`s an message in VOXSystem log.";
    }

    @Override
    public Void runCommand(VOXSource source, LinkedHashMap registry,String[] packages, Object byWho, VOXSystem VOX, App App, Object[] args) throws RuntimeException, IOException {
        if (args == null || args.length < 1 || !(args[0] instanceof String) || byWho == null) {
            throw new VOXSystemCommandInvalidArgs();
        } else {
            VOX.log(args[0].toString(), false);
            VOX.log(getName() + " by " + byWho.getClass().getSimpleName() + ": " + args[0], true);
        }
        return null;
    }
}
