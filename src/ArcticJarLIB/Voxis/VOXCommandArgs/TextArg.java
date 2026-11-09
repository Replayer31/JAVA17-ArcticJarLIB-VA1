package ArcticJarLIB.Voxis.VOXCommandArgs;

import ArcticJarLIB.Voxis.VOXCommandArgument;

public class TextArg extends VOXCommandArgument {
    private String value;

    public TextArg(String value) {
        setValue(value);
    }
    @Override
    public String getType() {
        return "";
    }
    @Override
    public String getDescription() {
        return "String argument";
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String setValue(Object value) {
        this.value = String.valueOf(value);
        return this.value;
    }
}
