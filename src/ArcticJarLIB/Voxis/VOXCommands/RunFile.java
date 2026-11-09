package ArcticJarLIB.Voxis.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.*;
import ArcticJarLIB.Voxis.VOXCommandArgs.PathArg;
import ArcticJarLIB.Voxis.VOXCommandArgs.TextArg;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class RunFile implements VOXCommand<Object[]> {

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
        return "vox_runFile";
    }

    @Override
    public String toString(VOXCommandArgument[] args) {
        return "VOXCommand \"vox_runFile\" that runs the VOXFile";
    }

    @Override
    public Object[] runCommand(VOXSource source, LinkedHashMap<String, Object> registry, String[] packages, Object byWho, VOXSystem VOX, App App,
                               VOXCommandArgument[] args) throws IOException {
        try {
            if (args == null || args.length == 0 || args[0] == null) {
                throw new VOXSystemCommandInvalidArgs();
            }

            Path path;
            if (!(args[0] instanceof TextArg) || !(args[0] instanceof PathArg)) {
                throw new VOXSystemCommandInvalidArgs();
            } else if (args[0] instanceof PathArg) {
                path = (Path) args[0].getValue();
            } else {
                path = Path.of(String.valueOf(args[0].getValue()));
            }

            return VOXFile.runVOXFile(source, registry, packages, byWho, VOX, path);

        } catch (Exception e) {
            VOX.log("Error when running VOXFile by \"vox_run\" VOXCommand: " + e.getMessage(), true);
        }
        return null;
    }

}
