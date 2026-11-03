package ArcticJarLIB.Exceptions;

/**
 * Code2: Errors with app
 */
public class AppErrorsRoot extends RuntimeException {

    public AppErrorsRoot() {
        super("App error");
    }
    public AppErrorsRoot(String message) {
        super(message);
    }

}
