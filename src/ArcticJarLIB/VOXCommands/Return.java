package ArcticJarLIB.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.util.LinkedHashMap;

public class Return implements VOXCommand {
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
        return "sys_return";
    }

    @Override
    public String toString(Object[] args) {
        return "System command \"sys_return\" that returns an target object.";
    }

    @Override
    public Object runCommand(VOXSource source, LinkedHashMap registry, String[] packages, Object byWho, VOXSystem VOX, App App, Object[] args)
            throws RuntimeException {
        for (Object obj : args) { if (obj == null) { throw new VOXSystemCommandInvalidArgs("Some args is null"); } }
        if (args.length < 2) {
            return VOX.setLastReturned(args[0]);
        } else {
            try {

                String joinedArgs = java.util.Arrays.stream(args)
                        .map(Object::toString)
                        .collect(java.util.stream.Collectors.joining(" "));

                return VOX.setLastReturned(VOX.run(source, registry, packages, byWho,
                        "sys_NewObj " + joinedArgs));

            } catch (Exception e) { throw new RuntimeException(e); }
        }
    }
}
