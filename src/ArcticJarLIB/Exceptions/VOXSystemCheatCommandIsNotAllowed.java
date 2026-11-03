package ArcticJarLIB.Exceptions;

/**
 * Code5.6: VOXSystem Cheat Command Is Not Allowed
 */
public class VOXSystemCheatCommandIsNotAllowed extends RuntimeException
{
    public VOXSystemCheatCommandIsNotAllowed() { super("Your command is can`t be run because cheats are not allowed. Code5.6"); }

    public VOXSystemCheatCommandIsNotAllowed(int code) { super("Your command is can`t be run because cheats are not allowed. Code5.6; " + code); }

    public VOXSystemCheatCommandIsNotAllowed(String message) { super(message); }

}