package calculator.function;

/**
 * Represent the constants in expression.  See {@link Function Function} for details
 *
 * Created by Alexander Dukkardt on 2017-05-06.
 */
public class ConstantFunction implements Function{
    private int value;
    
    public ConstantFunction(int value) {
        this.value = value;
    }
    
    @Override
    public int eval() {
        return value;
    }
}
