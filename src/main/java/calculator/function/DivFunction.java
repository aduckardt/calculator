package calculator.function;

/**
 * Represent the div() function in expression. See {@link Function Function} for details
 *
 * Created by Alexander Dukkardt on 2017-05-06.
 */
public class DivFunction extends AbstractFunction {
    
    public DivFunction(Function left, Function right) {
        super(left, right);
    }
    
    @Override
    public int eval() {
        return this.evalLeft() / this.evalRight();
    }
}
