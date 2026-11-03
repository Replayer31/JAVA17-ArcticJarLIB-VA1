package ArcticJarLIB.Exceptions;

/**
 * Code1: ConfirmRequested;
 * You need to confirm your choice.
 */
public class ConfirmRequested extends RuntimeException
{
    public ConfirmRequested() { super("You need confirm this method beforehand. Example: example(\"CONFIRM\"). Code: 1"); }

    public ConfirmRequested(int code) {
        super("You need confirm this method beforehand. Example: example(\"CONFIRM\"). Code: 1; " + code); }

    public ConfirmRequested(String message) {
        super(message);
    }

}