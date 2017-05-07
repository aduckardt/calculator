package calculator.function;

/**
 * Represent the value of the variable in expression, because the value of the variable might be defined as a function.
 * The instance of this object is created when let() function is met in the expression.
 *
 * See {@link Function Function} for details
 *
 * Created by Alexander Dukkardt on 2017-05-06.
 */
public class VariableFunction extends AbstractFunction {
    
    public VariableFunction(Function right) {
        super(null, right);
    }
    
    /**
     * Variable are defined by only one argument: let(var_name, var_expr, expr);
     * Here var_name is not required. Only var_expr is required to determine the value of the variable
     * @return
     */
    @Override
    public int eval() {
        return this.evalRight();
    }
}
