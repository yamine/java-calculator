package net.example.calculator;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.example.calculator.exceptions.ComputeException;
import net.example.calculator.exceptions.ParsingException;

public class OperatorExpression extends Expression {
	
	final static Logger logger = LogManager.getLogger(OperatorExpression.class);
	
	public OperatorExpression() {
	    listArgs = new HashMap<Integer, Expression>();
	}
	@Override
	public Integer compute(Map<String, Integer> varList) throws ParsingException, ComputeException, ArithmeticException {
		
		if (this.exprType == LET) {
			VariableExpression a = (VariableExpression) listArgs.get(1);
			//LET should have a variable expression as the first argument.
			//Therefore, the cast above may fail.
			if (a == null || a.getExpressionType() != Expression.VARIABLE) {
				throw new ParsingException("Parsing error: expected a variable for the first parameter in LET command.");
			}
			if (varList == null) {
				throw new ParsingException("Variable list should not be null.");
			}
	        //Get the second expression of LET
			Expression b = listArgs.get(2);
			//Set the value to the variable.
			varList.put(a.varName, b.compute(varList));
			
			Integer res = listArgs.get(3).compute(varList);
			if (res == null) {
				throw new ParsingException("Internal error occured: unexpected null value");
			}
			return res;
		} else {
			//Case of add/div or mult.
			if (varList == null) {
				throw new ParsingException("Variable list should not be null.");
			}
			Expression a = listArgs.get(1);
			int val = 0;
			if (a.exprType == Expression.VARIABLE) {
				Integer value1 = varList.get(((VariableExpression)a).getVariableName());
				if (value1 == null) {
					throw new ParsingException("Parsing error: unexpected null value for variable " + ((VariableExpression)a).getVariableName());
				}
				val = value1.intValue();
			} else {
				val = a.compute(varList);
			}
	        //Get the second argument 
			Expression b = listArgs.get(2);
			if (b.exprType == Expression.VARIABLE) {
				Integer value1 = varList.get(((VariableExpression)b).getVariableName());
				if (value1 == null) {
					throw new ParsingException("Parsing error: unexpected null value for variable " + ((VariableExpression)b).getVariableName());
				}
				if (this.exprType == Expression.ADD) {
					try {
						//The addExact will check if there is any overflow.
						val = Math.addExact(val, value1.intValue());
					} catch (ArithmeticException e) {
						throw e;
					}
				} else if (this.exprType == Expression.MULT) {
					try {
						//The multiplyExact will check if there is any overflow.
						val = Math.multiplyExact(val, value1.intValue());
					} catch (ArithmeticException e) {
						logger.error("Arithmetic exception " + e.getMessage());
						throw e;
					}
				} else {
					if (value1.intValue() == 0) {
						throw new ComputeException("Compute error: unexpected 0 value for division.");
					}
					val = val / value1.intValue();
				}
			} else {
				//Get the 3rd argument.
				b = listArgs.get(2);
				if (b.exprType == Expression.VARIABLE) {
					Integer value1 = varList.get(((VariableExpression)b).getVariableName());
					if (value1 == null) {
						throw new ParsingException("Parsing error: unexpected null value for variable " + ((VariableExpression)b).getVariableName());
					}
					if (this.exprType == Expression.ADD) {
						try {
							//The addExact will check if there is any overflow.
							val = Math.addExact(val, value1.intValue());
						} catch (ArithmeticException e) {
							throw e;
						}
					} else if (this.exprType == Expression.MULT) {
						//Multiplication
						try {
							//The multiplyExact will check if there is any overflow.
							val = Math.multiplyExact(val, value1.intValue());
						} catch (ArithmeticException e) {
							logger.error("Arithmetic exception during 'mult': " + e.getMessage());
							throw e;
						}
					} else {
						//Division
						if (value1.intValue() == 0) {
							throw new ComputeException("Arithmetic exception: unexpected 0 value for division.");
						}
						val = val / value1.intValue();
					}
				} else {
					if (this.exprType == Expression.ADD) {
						try {
							//The addExact will check if there is any overflow.
							val = Math.addExact(val, b.compute(varList));
						} catch (ArithmeticException e) {
							logger.error("Arithmetic exception during 'add': " + e.getMessage());
							throw e;
						}
						
					} else if (this.exprType == Expression.MULT) {
						try {
							//The multiplyExact will check if there is any overflow.
							val = Math.multiplyExact(val, b.compute(varList));
						} catch (ArithmeticException e) {
							logger.error("Arithmetic exception during 'mult': " + e.getMessage());
							throw e;
						}
					} else {
						if (b.compute(varList) == 0) {
							throw new ComputeException("Arithmetic exception: unexpected 0 value for division.");
						}
						val = val / b.compute(varList);;
					}
				}
			}
			//Set the value to the variable.
			return val;
		}
	}

}
