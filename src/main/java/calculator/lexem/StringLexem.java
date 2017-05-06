package calculator.lexem;

/**
 * The abstraction of the lexem that contains the String value.
 * See {@link Lexem Lexem}, {@link FunctionLexem FunctionLexem}, {@link VariableLexem VariableLexem},
 * {@link VariableReferenceLexem VariableReferenceLexem} class for details.
 *
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public abstract class StringLexem extends Lexem<String> {
    public StringLexem(String value, LexemType type, int position) {
        super(value, type, position);
    }
}
