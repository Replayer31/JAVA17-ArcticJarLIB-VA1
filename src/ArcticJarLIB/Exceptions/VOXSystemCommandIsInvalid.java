package ArcticJarLIB.Exceptions;

/**
 * Code5.3: VOXSystem invalid command
 */
public class VOXSystemCommandIsInvalid extends RuntimeException
{
    public VOXSystemCommandIsInvalid() { super("Your command is null or invalid, check it. Code5.3"); }

    public VOXSystemCommandIsInvalid(int code) { super("Your command is null or invalid, check it. Code5.3; " + code); }

    public VOXSystemCommandIsInvalid(String message) { super(message); }

}