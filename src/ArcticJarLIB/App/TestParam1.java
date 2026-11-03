package ArcticJarLIB.App;

import ArcticJarLIB.Exceptions.VOXSystemCommandInvalidArgs;

public class TestParam1 implements CFGParam<Boolean, Boolean, Object> {

    private Boolean var;

    @Override
    public Boolean getType() {
        return true;
    }

    @Override
    public String getName() {
        return "test_param1";
    }

    @Override
    public String toString(Object[] args) {
        return "Test Parameter";
    }

    @Override
    public Boolean setVar(Object[] args) {
        if (args != null && args.length > 0 && args[0] != null) { setVar(args[0]); } else { throw new IllegalArgumentException("Null args detected"); }
        return null;
    }

    @Override
    public Boolean setVar(Object value) {
        if (value instanceof Boolean) {
            var = (Boolean) value;
            return null;
        }
        if( value instanceof String ) {

            if ("true".equalsIgnoreCase((String) value) || "false".equalsIgnoreCase((String) value)) {
                var = Boolean.parseBoolean((String) value);
                return null;
            } else { throw new IllegalArgumentException(); }

        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Boolean getVar() {
        return var;
    }
}
