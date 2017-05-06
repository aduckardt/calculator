package calculator.lexem;

/**
 * Represents the Functions. See {@link Lexem Lexem} class for details.
 *
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public class FunctionLexem extends StringLexem {
    
    public FunctionLexem(String value, int position) {
        super(value, LexemType.Function, position);
    }
}
