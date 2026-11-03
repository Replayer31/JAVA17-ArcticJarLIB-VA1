package ArcticJarLIB.Exceptions;

/**
 * Code4: Errors with app folder
 */
public class AppFolderErrorsRoot extends RuntimeException {

    public AppFolderErrorsRoot() {
        super("App folder error");
    }
    public AppFolderErrorsRoot(String message) {
        super(message);
    }

}