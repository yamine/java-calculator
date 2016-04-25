package net.example.calculator;

import java.util.Map;

import net.example.calculator.exceptions.ParsingException;
/**
 * Defines a value expression which should be an Integer value.
 * 
 * @author El-Yamine Kettal
 *
 */
public class ValueExpression extends Expression {
    //Value of the value-expression (integer).
    Integer value = null;

    public ValueExpression (int val) {
	value = val;
	nbrArgs = 0;
	exprType = Expression.VALUE;
    }

    /**
     * Returns the value of the ValueExpression.
     * @return Integer 
     */
    public Integer getValue() {
	return value;
    }

    /**
     * Sets the integer value for this ValueExpression
     * @param value 
     */
    public void setValue(Integer value) {
	this.value = value;
    }
    /**
     * Returns the value for this integer value during the compute.
     * In this class there is no computation to be done. The value itself is returned.
     * @param args
     * @param Integer
     */
    @Override
    public Integer compute(Map<String, Integer> args) throws ParsingException {
	return value;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "value: " + value ;
    }
}
