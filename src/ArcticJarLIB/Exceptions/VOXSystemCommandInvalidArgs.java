package ArcticJarLIB.Exceptions;

/**
 * Code5.7: VOXSystem given args to command is invalid.
 */
public class VOXSystemCommandInvalidArgs extends RuntimeException
{
    public VOXSystemCommandInvalidArgs() { super("VOXSystem given args to command is invalid. Code5.7"); }

    public VOXSystemCommandInvalidArgs(int code) { super("VOXSystem given args to command is invalid. Code5.7; " + code); }

    public VOXSystemCommandInvalidArgs(Void unused) { super("VOXSystem given args to command is null. Code5.7"); }

    public VOXSystemCommandInvalidArgs(String message) { super(message); }

}