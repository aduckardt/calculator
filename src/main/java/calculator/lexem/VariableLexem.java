package calculator.lexem;

/**
 * Represents the defining the Variables. See {@link Lexem Lexem} class for details.
 *
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public class VariableLexem extends StringLexem {
    
    public VariableLexem(String value, int position) {
        super(value, LexemType.Variable, position);
    }
}
