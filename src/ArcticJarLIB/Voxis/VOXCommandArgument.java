package ArcticJarLIB.Voxis;

public abstract class VOXCommandArgument<T> {

    /**
     * What type of your command argument?
     * @return new Object();
     */
    public abstract T getType();

    /**
     * Describe your argument
     * @return new Object();
     */
    public abstract String getDescription();

    /**
     *
     * @return value that your argument has
     */
    public abstract T getValue();

    /**
     * Set value to your argument
     * @return optional: that same value
     */
    public abstract T setValue(T value);

}
