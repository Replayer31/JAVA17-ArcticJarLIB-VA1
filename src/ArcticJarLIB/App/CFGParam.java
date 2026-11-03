package ArcticJarLIB.App;

/**
 * App settings parameter
 */
public interface CFGParam<T , R, A> {

    /**
     * What your variable type is?
     * @return Return class of your variable
     */
    public T getType();

    public String getName();

    /**
     * What if convert your parameter to string?
     */
    public String toString(A[] args);

    /**
     * Set value to parameter
     * @param args Value
     * @return Return; You can write null here
     */
    public R setVar(A[] args);

    /**
     * Set value to parameter
     * @param value Value as string
     * @return Return; You can write null here
     */
    public R setVar(Object value);

    /**
     * Return your variable
     */
    public T getVar();
}
