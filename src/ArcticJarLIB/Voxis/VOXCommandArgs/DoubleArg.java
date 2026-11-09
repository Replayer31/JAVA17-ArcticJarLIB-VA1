package ArcticJarLIB.Voxis.VOXCommandArgs;

import ArcticJarLIB.Voxis.VOXCommandArgument;

public class DoubleArg extends VOXCommandArgument {
    private double value;

    public DoubleArg(double value) {
        setValue(value);
    }
    @Override
    public Double getType() {
        return 0d;
    }
    @Override
    public String getDescription() {
        return "Double argument";
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public Double setValue(Object value) {
        this.value = (Integer) value;
        return this.value;
    }
}
