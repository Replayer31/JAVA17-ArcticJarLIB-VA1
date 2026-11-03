package ArcticJarLIB.App.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.App.CFGManager;
import ArcticJarLIB.App.CFGParam;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.util.LinkedHashMap;

public class SetParam implements VOXCommand {

    @Override
    public boolean isCheat() {
        return true;
    }

    @Override
    public VOXSource getPermission() {
        return VOXSource.NONAME;
    }

    @Override
    public String getName() {
        return "cfg_set";
    }

    @Override
    public String toString(Object[] args) {
        return "CFGCommand \"cfg_set\" that setts your command value.";
    }

    @Override
    public Object runCommand(VOXSource source, LinkedHashMap registry, String[] packages, Object byWho, VOXSystem VOX, App App, Object[] args) throws RuntimeException {

        if (args == null || args.length < 1 || !(args[0] instanceof String || args[0] instanceof CFGParam || args[2] instanceof CFGManager) || registry == null) {
            throw new VOXSystemCommandInvalidArgs();
        }
        if (!(args[0] instanceof CFGParam) && !(args[2] instanceof CFGManager)) {
            throw new VOXSystemCommandInvalidArgs();
        }

        if (!(args[0] instanceof CFGParam)) {
            if (!registry.isEmpty() && registry.get(args[0]) != null) {
                return null;
            }
        }

        CFGManager cfgManager = (CFGManager) args[2];
        if (args[0] instanceof String) {

            CFGParam target = cfgManager.getParam((String) args[0]);
            cfgManager.setParam(target, args[1]);
            return target;

        } else {

            CFGParam target = (CFGParam) args[0];
            cfgManager.setParam(target, args[1]);
            return target;

        }

    }

}