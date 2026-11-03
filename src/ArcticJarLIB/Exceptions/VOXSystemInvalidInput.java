package ArcticJarLIB.Exceptions;

/**
 * Code5.1: VOXSystem invalid input
 */
public class VOXSystemInvalidInput extends RuntimeException
{
    public VOXSystemInvalidInput() { super("Your input is invalid, check it. Code5.1"); }

    public VOXSystemInvalidInput(int code) { super("Your input is invalid, check it. Code5.1; " + code); }

    public VOXSystemInvalidInput(String message) { super(message); }

}