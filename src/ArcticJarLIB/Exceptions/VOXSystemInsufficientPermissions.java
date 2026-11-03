package ArcticJarLIB.Exceptions;

/**
 * Code5.2: VOXSystem Insufficient Permissions
 */
public class VOXSystemInsufficientPermissions extends RuntimeException
{
    public VOXSystemInsufficientPermissions() { super("You don`t have permissions for this. Code5.2"); }

    public VOXSystemInsufficientPermissions(int code) { super("You don`t have permissions for this. Code5.2; " + code); }

    public VOXSystemInsufficientPermissions(String message) { super(message); }

}