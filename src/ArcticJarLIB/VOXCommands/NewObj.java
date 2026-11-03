package ArcticJarLIB.VOXCommands;

import ArcticJarLIB.App.App;
import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;
import ArcticJarLIB.Exceptions.VOXSystemErrorsRoot;
import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXSource;
import ArcticJarLIB.Voxis.VOXSystem;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class NewObj implements VOXCommand<Object, Object> {
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
        return "sys_NewObj";
    }

    @Override
    public String toString(Object[] args) {
        return "System command \"sys_NewObj\" that creates and returns an target object.";
    }

    @Override
    public Object runCommand(VOXSource source, LinkedHashMap<String, Object> registry,String[] packages, Object byWho, VOXSystem VOX, App App, Object[] args) throws VOXSystemCommandInvalidArgs, VOXSystemErrorsRoot {

        if (args == null) { throw new VOXSystemCommandInvalidArgs("Some args is null"); }

        Object first = Arrays.stream(args)
                .findFirst()
                .orElse(null);

        if (first == null) { throw new VOXSystemCommandInvalidArgs(".findFirst() Stream API method don`t returned first arg"); }

        Object[] rest = Arrays.stream(args)
                .skip(1)
                .toArray(Object[]::new);

        try {
            Class<?> clazz = (first instanceof Class<?>) ? (Class<?>) first : Class.forName(first.toString());
            Class<?>[] paramTypes = Arrays.stream(rest)
                    .map(arg -> arg == null ? Object.class : arg.getClass())
                    .toArray(Class<?>[]::new);

            Constructor<?> constructor = null;
            for (Constructor<?> c : clazz.getDeclaredConstructors()) {
                Class<?>[] types = c.getParameterTypes();



                if (types.length == paramTypes.length) {

                    boolean match = true;

                    for (int i = 0; i < types.length; i++) {

                        if (!types[i].isAssignableFrom(paramTypes[i])) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        constructor = c;
                        break;
                    }
                }
            }

            if (constructor != null) {
                constructor.setAccessible(true);
                return constructor.newInstance(rest);
            }

            throw new NoSuchMethodException("No matching constructor found for " + clazz.getName() + " when running sys_NewObj");

        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
