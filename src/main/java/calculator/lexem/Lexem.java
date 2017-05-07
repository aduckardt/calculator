package calculator.lexem;

import java.util.LinkedList;

/**
 * Abstract class that represents lexem in the provided expression. In terms of this application lexems are the objects
 * that expression is built of. The lexem represent a meaningful part of the expression. Any symbols like comma and parenthesis
 * are excluded from the list of lexems.
 *
 * For example, expression <b>add(1, 2)</b> consists of  the following lexems:
 * <b>add</b>,<b>3</b>,<b>2</b>.
 *
 * Moreover, the lexems are arranged as a tree structure to simplify parsing expression process and further processing.
 *
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public abstract class Lexem<T> {
    /**
     * The actual value of the lexem. Might be a String or Integer. The type depends on {@link LexemType LexemType}
     * and might be {@link String String} or {@link Integer Integer}.
     */
    private T value;
    /**
     * The {@link LexemType LexemType} of the lexem.
     */
    private LexemType type;
    /**
     * The start position of this lexem in the provided expression.
     */
    private int position;
    /**
     * the level of this lexem in the whole hierarchy. Needs only for beautifully the logs
     */
    private int level;
    /**
     * The reference to the top level lexem in the hierarchy
     */
    private Lexem parent = null;
    /**
     * The list of the nodes of this lexem
     */
    private LinkedList<Lexem> childNodes = new LinkedList<>();
    
    public Lexem(T value, LexemType type, int position) {
        this.value = value;
        this.type = type;
        this.position = position;
    }
    
    /**
     * Returns the actual value of this lexem.
     *
     * @return type is depends on realization. Might be {@link String String} or {@link Integer Integer}.
     * See {@link StringLexem StringLexem}, {@link ConstantLexem ConstantLexem}
     */
    public T getValue(){
        return value;
    }
    
    /**
     * Returns the position of this lexem in the expression.
     *
     * @return integer
     */
    public int getPosition(){
        return position;
    }
    
    /**
     * Checks if this Lexem is a Function.
     *
     * @return <b>true</b> if this Lexem is a function, otherwise <b>false</b>
     */
    public boolean isFunction(){
        return type == LexemType.Function;
    }
    /**
     * Checks if this Lexem is a Variable reference.
     *
     * @return <b>true</b> if this Lexem is a Variable reference, otherwise <b>false</b>
     */
    public boolean isVariableReference(){
        return type == LexemType.VariableReference;
    }
    /**
     * Checks if this Lexem is a Variable declaration.
     *
     * @return <b>true</b> if this Lexem is a Variable declaration, otherwise <b>false</b>
     */
    public boolean isVariable(){
        return type == LexemType.Variable;
    }
    /**
     * Checks if this Lexem is a Constant.
     *
     * @return <b>true</b> if this Lexem is a Constant, otherwise <b>false</b>
     */
    public boolean isConstant(){
        return type == LexemType.Constant;
    }
    
    /**
     * Returns the top level lexem in the hierarchy
     * @return {@link Lexem Lexem}
     */
    public Lexem getParent() {
        return parent;
    }
    /**
     * Returns the nodes of this lexem as a list
     * @return {@link LinkedList LinkedList}
     */
    public LinkedList<Lexem> getChildNodes() {
        return childNodes;
    }
    
    /**
     * Sets the level in the hierarchy for beautifully the logs
     * @param level - int value
     */
    public void setLevel(int level) {
        this.level = level;
    }
    
    /**
     * Add a node to this lexem.
     *
     * @param lexem
     */
    public void addChild(Lexem lexem){
        if(lexem!=null){
            lexem.parent = this;
            this.childNodes.add(lexem);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder tabsBuilder = new StringBuilder();
        for (int i=0; i<level; i++){
            tabsBuilder.append("    ");
        }
        final String tabs = tabsBuilder.toString();
        
        final StringBuilder sb = new StringBuilder();
        sb.append(tabs);
        sb.append("(");
        sb.append(" value=").append(value);
        sb.append(", type=").append(type);
        sb.append(", position=").append(position);
        if(childNodes.size()>0) {
            sb.append(", childNodes=[");
            sb.append("\n");
    
            childNodes.forEach(l -> {
                sb.append(tabs).append(l.toString()).append("\n");
            });
            sb.append(tabs).append("])");
        } else {
            sb.append(")");
        }
        
        return sb.toString();
    }
}
