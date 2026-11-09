package ArcticJarLIB.Voxis.VOXCommandArgs;

import ArcticJarLIB.Voxis.VOXCommand;
import ArcticJarLIB.Voxis.VOXCommandArgument;

public class VOXCommandArg extends VOXCommandArgument {
    private VOXCommand value;

    public VOXCommandArg(VOXCommand value) {
        setValue(value);
    }
    @Override
    public VOXCommand getType() {
        return value;
    }
    @Override
    public String getDescription() {
        return "Any object argument";
    }

    @Override
    public VOXCommand getValue() {
        return this.value;
    }

    @Override
    public VOXCommand setValue(Object value) {
        this.value = (VOXCommand) value;
        return this.value;
    }
}
