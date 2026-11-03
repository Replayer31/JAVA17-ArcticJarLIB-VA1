package ArcticJarLIB.Exceptions;

/**
 * Code5: Errors with VOXSystem
 */
public class VOXSystemErrorsRoot extends RuntimeException {

    public VOXSystemErrorsRoot() { super("VOXSystem error"); }
    public VOXSystemErrorsRoot(String message) {
        super(message);
    }

}
