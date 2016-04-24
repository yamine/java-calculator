package net.example.calculator;

import java.util.Map;

import net.example.calculator.exceptions.ParsingException;

public class ValueExpression extends Expression {
	//Value of the value-expression (integer).
	Integer value = null;
	
	public ValueExpression (int val) {
		value = val;
		nbrArgs = 0;
		exprType = Expression.VALUE;
	}
	
    /**
     * Returns the value.
	 * @return the value
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
	 * Returns the value for this integer value.
	 * @param args
	 */
	public Integer compute(Map<String, Integer> args) throws ParsingException {
    	return value.intValue();
    }
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "value: " + value ;
	}
}
