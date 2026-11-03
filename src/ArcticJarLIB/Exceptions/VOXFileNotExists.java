package ArcticJarLIB.Exceptions;

/**
 * Code6.1: VOXSystem file not exists
 */
public class VOXFileNotExists extends VOXFileErrorsRoot {

    public VOXFileNotExists() { super("VOXSystem file not exists, check the path. Code6.1"); }

    public VOXFileNotExists(int code) { super("VOXSystem file not exists, check the path. Code6.1; " + code); }

    public VOXFileNotExists(String message) { super(message); }

}
