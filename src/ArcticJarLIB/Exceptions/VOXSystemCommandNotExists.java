package ArcticJarLIB.Exceptions;

/**
 * Code5.4: VOXSystem command not exists
 */
public class VOXSystemCommandNotExists extends RuntimeException
{
    public VOXSystemCommandNotExists() { super("Your command is not in registry, check it. Code5.4"); }

    public VOXSystemCommandNotExists(int code) { super("Your command is not in registry, check it. Code5.4; " + code); }

    public VOXSystemCommandNotExists(String message) { super(message); }

}