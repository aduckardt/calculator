package calculator.function;

/**
 * Abstract class that implements {@link Function Function} interface and defines the property of each realization.
 * See {@link Function Function} for details
 *
 * Created by Alexander Dukkardt on 2017-05-06.
 */
public abstract class AbstractFunction implements Function {
    /**
     * The first argument of function
     */
    private Function left;
    /**
     * The second argument of function
     */
    private Function right;
    
    public AbstractFunction(Function left, Function right) {
        this.left = left;
        this.right = right;
    }
    
    /**
     * Evaluates the value of the first argument
     * @return int - the value of the first argument
     */
    protected int evalLeft(){
        return left.eval();
    }
    /**
     * Evaluates the value of the second argument
     * @return int - the value of the second argument
     */
    protected int evalRight(){
        return right.eval();
    }
}
