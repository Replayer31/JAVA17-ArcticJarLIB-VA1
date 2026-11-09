package ArcticJarLIB.Voxis.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.*;
import ArcticJarLIB.Voxis.VOXCommandArgs.TextArg;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class Run implements VOXCommand<Object[]> {

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
        return "vox_run";
    }

    @Override
    public String toString(VOXCommandArgument[] args) {
        return "VOXCommand \"vox_run\" that runs the entered command";
    }

    @Override
    public Object[] runCommand(VOXSource source, LinkedHashMap<String, Object> registry, String[] packages, Object byWho, VOXSystem VOX, App App,
                               VOXCommandArgument[] args) throws IOException {
        try {
            if (args == null || args.length == 0 || args[0] == null) {
                throw new VOXSystemCommandInvalidArgs();
            }
            if (!(args[0] instanceof TextArg)) {
                throw new VOXSystemCommandInvalidArgs();
            } else {
                TextArg textArg = (TextArg) args[0];
                String target = textArg.getValue();
                return new Object[]{VOX.run(source, registry, packages, byWho, target).orElse(null)};
            }

        } catch (Exception e) {
            VOX.log("Error when running VOXCommand by \"vox_run\" VOXCommand: " + e.getMessage(), true);
        }
        return null;
    }

}
