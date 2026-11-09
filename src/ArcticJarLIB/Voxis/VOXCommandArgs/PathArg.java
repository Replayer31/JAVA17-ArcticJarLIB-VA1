package ArcticJarLIB.Voxis.VOXCommandArgs;

import ArcticJarLIB.Voxis.VOXCommandArgument;

import java.nio.file.Path;

public class PathArg extends VOXCommandArgument {
    private Path value;

    public PathArg(String value) {
        setValue(value);
    }
    @Override
    public Path getType() {
        return Path.of("");
    }
    @Override
    public String getDescription() {
        return "Path argument";
    }

    @Override
    public Path getValue() {
        return this.value;
    }

    @Override
    public Path setValue(Object value) {
        this.value = (Path) value;
        return this.value;
    }
}
