package ArcticJarLIB.Voxis.VOXCommandArgs;

import ArcticJarLIB.Voxis.VOXCommandArgument;

public class IntArg extends VOXCommandArgument {
    private int value;

    public IntArg(int value) {
        setValue(value);
    }
    @Override
    public Integer getType() {
        return 0;
    }
    @Override
    public String getDescription() {
        return "Integer argument";
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public Integer setValue(Object value) {
        this.value = (Integer) value;
        return this.value;
    }
}
