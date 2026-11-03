package ArcticJarLIB.Exceptions;

/**
 * Code4.2: App folder can`t be created in target directory, needs Admins permissions.;
 */
public class AppAdminsPermissionsRequired extends AppFolderErrorsRoot
{
    public AppAdminsPermissionsRequired() { super("App folder can`t be created in target directory, needs Admins permissions. Code4.2"); }

    public AppAdminsPermissionsRequired(int code) {
        super("App folder can`t be created in target directory, needs Admins permissions. Code4.2; " + code); }

    public AppAdminsPermissionsRequired(String message) {
        super(message);
    }

}