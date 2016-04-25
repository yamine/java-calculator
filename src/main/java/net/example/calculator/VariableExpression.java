package net.example.calculator;

import java.util.Map;

import net.example.calculator.exceptions.ParsingException;
/**
 * Defines the variable expression
 * This class has a variable name and no value.
 * The value is defined through the LET during the computation.
 * 
 * @author EL-Yamine Kettal
 *
 */
public class VariableExpression extends Expression {

    private String varName = null;

    public VariableExpression(String name) {
	nbrArgs = 0;
	listArgs = null;
	varName = name;
	exprType = Expression.VARIABLE;
    }
    /**
     * Returns the variable name for this expression.
     * @return String. The variable name defined in the LET expression.
     */
    public String getVariableName () {
	return varName;
    }
    /**
     * Returns the value for this variable.
     * The value is found in the map that should have been set in
     * the LET.
     * @param args
     * @return Integer. variable value.
     */
    public Integer compute(Map<String, Integer> args) throws ParsingException {
	return args.get(varName);
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "varName: " + varName ;
    }
}
