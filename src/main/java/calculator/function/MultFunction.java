package calculator.function;

/**
 * Represent the mult() function in expression. See {@link Function Function} for details
 *
 * Created by Alexander Dukkardt on 2017-05-06.
 */
public class MultFunction extends AbstractFunction {
    
    public MultFunction(Function left, Function right) {
        super(left, right);
    }
    
    @Override
    public int eval() {
        return this.evalLeft() * this.evalRight();
    }
}
