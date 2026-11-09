package ArcticJarLIB.Voxis.VOXCommandArgs;

import ArcticJarLIB.Voxis.VOXCommandArgument;

public class ObjArg extends VOXCommandArgument {
    private Object value;

    public ObjArg(Object value) {
        setValue(value);
    }
    @Override
    public Object getType() {
        return value;
    }
    @Override
    public String getDescription() {
        return "Any object argument";
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object setValue(Object value) {
        this.value = value;
        return this.value;
    }
}
