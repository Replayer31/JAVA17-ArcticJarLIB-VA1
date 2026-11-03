package ArcticJarLIB.Voxis.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXFile;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class Run implements VOXCommand<Object[], Object> {

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
    public String toString(Object[] args) {
        return "VOXCommand \"vox_run\" that runs the entered command";
    }

    @Override
    public Object[] runCommand(VOXSource source, LinkedHashMap<String, Object> registry, String[] packages, Object byWho, VOXSystem VOX, App App,
                               Object[] args) throws IOException {
        try {
            if (args == null || args.length == 0 || args[0] == null) {
                throw new VOXSystemCommandInvalidArgs();
            }

            String target = (String) args[0];
            return new Object[]{VOX.run(source, registry, packages, byWho, target).orElse(null)};

        } catch (Exception e) {
            VOX.log("Error when running VOXFile by \"vox_run\" VOXCommand: " + e.getMessage(), true);
        }
        return null;
    }

}
