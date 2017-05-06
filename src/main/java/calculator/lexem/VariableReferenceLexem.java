package calculator.lexem;

/**
 * Represents the references to the Variables. See {@link Lexem Lexem} class for details.
 *
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public class VariableReferenceLexem extends StringLexem {
    
    public VariableReferenceLexem(String value, int position) {
        super(value, LexemType.VariableReference, position);
    }
}
