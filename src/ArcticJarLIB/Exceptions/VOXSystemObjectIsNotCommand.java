package ArcticJarLIB.Exceptions;

/**
 * Code5.5: VOXSystem your object is not command and can`t be run.
 */
public class VOXSystemObjectIsNotCommand extends RuntimeException
{
    public VOXSystemObjectIsNotCommand() { super("Your object is not command and can`t be run. Code5.5"); }

    public VOXSystemObjectIsNotCommand(int code) { super("Your object is not command and can`t be run. Code5.5; " + code); }

    public VOXSystemObjectIsNotCommand(String message) { super(message); }

}