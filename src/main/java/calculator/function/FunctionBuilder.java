package calculator.function;


import calculator.lexem.ConstantLexem;
import calculator.lexem.Lexem;
import calculator.lexem.VariableLexem;
import calculator.lexem.VariableReferenceLexem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Converts the parsed {@link Lexem Lexem} hierarchy into {@link Function Function} representation to evaluate the final value of expression
 *
 * Created by Alexander Dukkardt on 2017-05-06.
 */
public class FunctionBuilder {
    /**
     * The root element of {@link Lexem Lexem} tree.
     */
    private Lexem rootLexem;
    /**
     * map to store {@link VariableFunction VariableFunction} for the references in other functions.
     */
    private Map<String, Function> varFuncMap = new HashMap<>();
    
    /**
     * Constructor
     * @param rootLexem - The root element of {@link Lexem Lexem} tree.
     */
    public FunctionBuilder(Lexem rootLexem) {
        this.rootLexem = rootLexem;
    }
    
    /**
     * Build {@link Function Function} representation of the expression from the {@link Lexem Lexem} tree.
     * @return
     */
    public Function build(){
        // due to root lexem is fake we have to get the first child. This child is an expression that was parsed
        Lexem functionLex = getLexem(rootLexem, 0);
        
        return buildFunction(functionLex);
    }
    
    /**
     * Gets the child {@link Lexem Lexem} node of the provided parent {@link Lexem Lexem}
     * @param parent - the {@link Lexem Lexem} where to look for the child
     * @param index - the index of the child element
     * @return {@link Lexem Lexem} - the child lexem
     */
    private Lexem getLexem(Lexem parent, int index){
        return (Lexem)parent.getChildNodes().get(index);
    }
    
    /**
     * Converts {@link Lexem Lexem} into {@link Function Function}
     * @param lexem - {@link Lexem Lexem} to convert
     * @return {@link Function Function}
     */
    private Function buildFunction(Lexem lexem) {
        if (lexem.isConstant()){
            return buildConstantFunction((ConstantLexem)lexem);
        } else if (lexem.isVariableReference()){
            return buildReferenceFunction((VariableReferenceLexem)lexem);
        } else if (lexem.isFunction()){
            if(FunctionName.LET.equals(lexem.getValue())){
                // let function
                // 2 first children are variable functions
                buildVariableFunction(lexem);
                // the last one is a function that must be built using variable one
                return buildFunction(getLexem(lexem, 2));
            } else  {
                return createFunctionInstance(lexem);
            }
        }
        throw new RuntimeException(String.format("Invalid expression. Unknown %s function name", lexem.getValue()));
    }
    
    /**
     * Creates {@link Function Function}
     * @param lexem - {@link Lexem Lexem} to convert
     * @return {@link Function Function}
     */
    private Function createFunctionInstance(Lexem lexem){
        Lexem fArg = getLexem(lexem, 0);
        Lexem sArg = getLexem(lexem, 1);
        
        if(FunctionName.ADDITION.equals(lexem.getValue())) {
            return new AddFunction(buildFunction(fArg), buildFunction(sArg));
        }else if(FunctionName.SUBSTITUTION.equals(lexem.getValue())) {
            return new SubFunction(buildFunction(fArg), buildFunction(sArg));
        }else if(FunctionName.MULTIPLICATION.equals(lexem.getValue())) {
            return new MultFunction(buildFunction(fArg), buildFunction(sArg));
        }else if(FunctionName.DIVISION.equals(lexem.getValue())) {
            return new DivFunction(buildFunction(fArg), buildFunction(sArg));
        }
        throw new RuntimeException(String.format("Invalid expression. Unknown %s function name", lexem.getValue()));
    }
    
    
    /**
     * Finds and returns the {@link VariableFunction VariableFunction} by name of the variable set in {@link Lexem Lexem}
     * @param lexem
     * @return {@link Function Function}
     */
    private Function buildReferenceFunction(VariableReferenceLexem lexem) {
        // need to up through the lexem tree and found the nearest variable lexem that contains the same variable name
        VariableLexem var = findVariable(lexem.getParent(), lexem.getValue());
        if(var!=null) {
            // here check if the function is already created.
            String functionKey = buildKey(var);
            if (varFuncMap.containsKey(functionKey)) {
                // function is already created
                return varFuncMap.get(functionKey);
            }
        }
        throw new RuntimeException(String.format("Invalid expression. Variable %s is not declared", lexem.getValue()));
    }
    /**
     * Finds the {@link VariableLexem VariableLexem} that contains the variable definition. It is the closest ascenders lexem to this one.
     * @param lexem
     * @return {@link Lexem Lexem}
     */
    private VariableLexem findVariable(Lexem lexem, String varToFind) {
        VariableLexem result = null;
        boolean isFound = false;
        if(lexem!=null){
            LinkedList<Lexem> children = lexem.getChildNodes();
            for (Lexem child: children) {
                if(child.isVariable() && child.getValue().equals(varToFind)){
                    result = (VariableLexem)child;
                    isFound=true;
                    break;
                }
            }
            if(!isFound && lexem.getParent()!=null) {
                // if variable not found go to the upper level
                result = findVariable(lexem.getParent(), varToFind);
            }
        }
        return result;
    }
    /**
     * Converts {@link ConstantLexem ConstantLexem} into {@link ConstantFunction ConstantFunction}
     * @param lexem - {@link Lexem Lexem} to convert
     * @return {@link Function Function}
     */
    private Function buildConstantFunction(ConstantLexem lexem) {
        return new ConstantFunction(lexem.getValue());
    }
    /**
     * Converts {@link calculator.lexem.FunctionLexem FunctionLexem} that represents let function in expression
     * into {@link VariableFunction VariableFunction}. The first 2 children of Let Lexem are: variable name and varible expression.
     * To determine the value of variable and build {@link VariableFunction VariableFunction} we use only the 2nd child
     * The 1st one is used to build a key to store this Function into the local map for future references.
     *
     * @param let - {@link Lexem Lexem} to convert
     */
    private void buildVariableFunction(Lexem let) {
        Lexem var = getLexem(let, 0);
        Lexem varExp = getLexem(let, 1);
        
        Function varFunc = new VariableFunction(buildFunction(varExp));
    
        varFuncMap.put(buildKey(var), varFunc);
    }
    
    /**
     * Builds the key for local map for {@link VariableFunction VariableFunction}
     * @param var - {@link calculator.lexem.VariableLexem VariableLexem} contains the name of the variable.
     * @return {@link String String} - the key value
     */
    private String buildKey(Lexem var){
        return var.getValue().toString()+"_"+var.getPosition();
    }
}
