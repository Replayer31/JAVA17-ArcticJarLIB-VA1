package ArcticJarLIB.Voxis.VOXCommandArgs;

import ArcticJarLIB.Voxis.VOXCommandArgument;

public class BooleanArg extends VOXCommandArgument {
    private Boolean value;

    public BooleanArg(Boolean value) {
        setValue(value);
    }
    @Override
    public Boolean getType() {
        return true;
    }
    @Override
    public String getDescription() {
        return "Boolean argument";
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public Boolean setValue(Object value) {
        this.value = Boolean.valueOf(String.valueOf(value));
        return this.value;
    }
}
