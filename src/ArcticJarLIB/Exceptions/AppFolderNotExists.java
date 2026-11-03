package ArcticJarLIB.Exceptions;

/**
 * Code4.1: App folder not exists;
 */
public class AppFolderNotExists extends AppFolderErrorsRoot
{
    public AppFolderNotExists() { super("Create the app folder first or check the path. Code4.1"); }

    public AppFolderNotExists(int code) {
        super("Create the app folder first or check the path. Code4.1; " + code); }

    public AppFolderNotExists(String message) {
        super(message);
    }

}