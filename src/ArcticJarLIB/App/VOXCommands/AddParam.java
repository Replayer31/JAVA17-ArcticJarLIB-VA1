package ArcticJarLIB.App.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.App.CFGManager;
import ArcticJarLIB.App.CFGParam;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXCommandArgument;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.util.LinkedHashMap;

public class AddParam implements VOXCommand {

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
        return "cfg_add";
    }

    @Override
    public String toString(VOXCommandArgument[] args) {
        return "CFGCommand \"cfg_add\" that adds your command to cfg params list.";
    }

    @Override
    public Object runCommand(VOXSource source, LinkedHashMap registry, String[] packages, Object byWho, VOXSystem VOX, App App, VOXCommandArgument[] args) throws RuntimeException {

        if (args == null || args.length < 1 || !(args[0].getType() instanceof CFGParam || args[1].getType() instanceof CFGManager) || registry == null) {
            throw new VOXSystemCommandInvalidArgs();
        }

        if (!(args[0].getType() instanceof CFGParam)) {
            if (!registry.isEmpty() && registry.get(args[0]) != null) {
                return null;
            }
        }

        CFGManager cfgManager = (CFGManager) args[1].getValue();
        CFGParam target = (CFGParam) args[0].getValue();
        cfgManager.getCFGParamsList().put(target.getName(), target);
        return target;
    }

}
