package calculator.function;

/**
 * Interface provides the ability to evaluate the value of the expression.
 * Each {@link calculator.lexem.Lexem Lexem} converts to the one of the implementation of {@link Function Function} interface and represented
 * as a function. Even arguments of any function in expression are represented as a {@link Function Function}.
 * The {@link calculator.lexem.ConstantLexem ConstantLexem} converted to {@link ConstantFunction ConstantFunction}
 * The {@link calculator.lexem.FunctionLexem FunctionLexem} converted to one of the following list of the types:
 *      {@link AddFunction AddFunction}
 *      {@link SubFunction SubFunction}
 *      {@link MultFunction MultFunction}
 *      {@link DivFunction DivFunction}
 * The {@link calculator.lexem.VariableLexem VariableLexem} converted to {@link ConstantFunction ConstantFunction}
 * The {@link calculator.lexem.VariableReferenceLexem VariableReferenceLexem} converted to {@link ConstantFunction ConstantFunction}
 *
 * Created by Alexander Dukkardt on 2017-05-03.
 */
public interface Function {
    int eval();
}
