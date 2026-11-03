package ArcticJarLIB.Exceptions;

/**
 * Code3.1: Log file not exists; Create the log file first.
 */
public class LogFileIsNotExists extends LogErrorsRoot {

    public LogFileIsNotExists() { super("Create the log file first or check the path. Code3.1"); }

    public LogFileIsNotExists(int code) { super("Create the log file first or check the path. Code3.1; " + code); }

    public LogFileIsNotExists(String message) { super(message); }

}
