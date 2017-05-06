package calculator.lexem;

/**
 * Represents the constant lexems in the expression. See {@link Lexem Lexem} class for details.
 *
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public class ConstantLexem extends Lexem<Integer> {
    public ConstantLexem(Integer value, int position) {
        super(value, LexemType.Constant, position);
    }
}
