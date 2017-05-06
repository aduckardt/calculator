package calculator.parser;


import calculator.lexem.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parser class that provide the functionality to transform the expression from String representation into the tree of {@link Lexem Lexem} objects
 *
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public class Parser {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * The expression to parse
     */
    private String stringExpression;
    /**
     * Current position of the cursor in the expression.
     */
    private int currentPosition=-1;
    /**
     * Current character at the cursor position.
     */
    private int character;
    /**
     *  Fake node that represents whole expression.
     */
    private Lexem rootLexem ;
    
    /**
     * Constructor- creates an instance of the Parser object
     * @param expression - expression that has to be parsed.
     */
    public Parser(String expression){
        this.stringExpression = expression;
    }
    
    /**
     * Parse the expression into the tree of the {@link Lexem Lexem} objects.
     */
    public void parse(){
        nextChar();
        rootLexem = new FunctionLexem("Root", -1);
        parseFunction(rootLexem,1 );
        if(log.isDebugEnabled()) {
            printLexems();
        }
    }
    
    /**
     * Return the Root Lexem of the expression. It is a fake node that represents whole expression.
     * @return {@link Lexem Lexem}
     */
    public Lexem getRootLexem() {
        return rootLexem;
    }
    
    /**
     * Moves cursor to the nex char in the expression
     */
    private void nextChar(){
        this.character = (++this.currentPosition < this.stringExpression.length()) ? this.stringExpression.charAt(currentPosition) : -1;
    }
    
    /**
     * Checks if the charToValidate is the current char that cursor points to and move to the next if <b>true</b>.
     * If current char is space then skips it.
     *
     * @param charToValidate
     * @return boolean
     */
    private boolean validateAndSkip(int charToValidate){
        while (this.character == ' ') {
            nextChar();
        }
        if (this.character == charToValidate) {
            nextChar();
            return true;
        }
        return false;
    }
    
    /**
     * Parse the {@link FunctionLexem FunctionLexem} from the current cursor position.
     * @param parent - the top level {@link FunctionLexem FunctionLexem} where parsed {@link FunctionLexem FunctionLexem} is going to be added
     * @param level - the level at which parsed {@link FunctionLexem FunctionLexem} is added.
     */
    private void parseFunction(Lexem parent, int level){
        int startPos = this.currentPosition;
        String functionName = parseName();
       // System.out.println("Function: " + functionName);
        
        Lexem function = new FunctionLexem(functionName, startPos);
        // need to parse args of the function
        if(validateAndSkip('(')){
            while(!validateAndSkip(')') && character!=-1) {
                parseArgs(function, level);
            }
        }
        function.setLevel(level);
        parent.addChild(function);
    }
    /**
     * Parse the arguments for {@link FunctionLexem FunctionLexem}
     * @param parent - the {@link FunctionLexem FunctionLexem} for which arguments are being parsed
     * @param level - the level at which {@link FunctionLexem FunctionLexem} is added.
     */
    private void parseArgs(Lexem parent, int level){
        int startPos = this.currentPosition;
        if(character=='-' || ((character >= '0' && character <= '9'))){
            // parse constant
            while (character=='-' || ((character >= '0' && character <= '9'))) {
                nextChar();
            }
            int x = Integer.parseInt(stringExpression.substring(startPos, this.currentPosition));
           // System.out.println("Constant: " + x);
            Lexem lexem = new ConstantLexem(x, startPos);
            lexem.setLevel(level+1);
            parent.addChild(lexem);
        } else  {
            // new function here or variable here
            String name = parseName();
            this.currentPosition -=(name.length()+1);
            if(validateAndSkip('(')){
                // parse nested function
                parseFunction(parent, level+1);
            } else {
                // parse variable
                nextChar();
                parseVariable(parent, level);
            }
        }
        // skip comma character
        validateAndSkip(',');
    }
    /**
     * Parse the the variable and put them into {@link VariableLexem VariableLexem} or {@link VariableReferenceLexem VariableReferenceLexem}
     * @param parent - the {@link FunctionLexem FunctionLexem} for which arguments are being parsed
     * @param level - the level at which {@link FunctionLexem FunctionLexem} is added.
     */
    private void parseVariable(Lexem parent, int level){
        int startPos = this.currentPosition;
        String name = parseName();
       // System.out.println("varName: " + name);
        Lexem lexem;
        if("let".equals(parent.getValue())) {
            // it is declaration. the declaration is only in let function
            lexem = new VariableLexem(name, startPos);
        } else {
            // it is the reference to the previously added variable
            lexem = new VariableReferenceLexem(name, startPos);
        }
        lexem.setLevel(level+1);
        parent.addChild(lexem);
    }
    
    /**
     * Parse the sequences of character as a function or variable names
     * @return String - the parsed name
     */
    private String parseName(){
        int startPos = this.currentPosition;
        // count how many characters are in the name
        while (character >= 'a' && character <= 'z') {
            nextChar();
        }
        // get substring
        String name = stringExpression.substring(startPos, this.currentPosition);
        return name;
    }
    
    
    /**
     * Prints the whole tree of the {@link Lexem Lexem} only in DEBUG log level.
     */
    private void printLexems(){
        final StringBuilder sb = new StringBuilder("Lexems: \n");
        if(rootLexem!=null) {
            sb.append(rootLexem.toString()).append("\n");
            sb.setLength(sb.length()-1);
        }
       log.debug(sb.toString());
    }
}
