package calculator.lexem;

/**
 * Lexem Type, helps to determine what this {@link Lexem Lexem} represent int the given expression
 *
 *
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public enum LexemType {
    /**
     * Lexem represents one of the functions
     */
    Function,
    /**
     * Lexem represents the variable. This type is used to define the variable
     */
    Variable,
    /**
     * Lexem represents the variable. This type is used to reference to the variable that has been defined before
     */
    VariableReference,
    /**
     * Lexem represents the constant expression. Typically it is an {@link Integer Integer} value
     */
    Constant
}
