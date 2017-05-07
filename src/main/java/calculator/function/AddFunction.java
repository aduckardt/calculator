package calculator.function;

/**
 * Represent the add() function in expression.  See {@link Function Function} for details
 *
 * Created by Alexander Dukkardt on 2017-05-06.
 */
public class AddFunction extends AbstractFunction {
    
    public AddFunction(Function left, Function right) {
        super(left, right);
    }
    
    @Override
    public int eval() {
        return this.evalLeft() + this.evalRight();
    }
}
