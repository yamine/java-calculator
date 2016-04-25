package net.example.calculator;

import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.example.calculator.exceptions.ComputeException;
import net.example.calculator.exceptions.ParsingException;
/**
 * This class implement the basic expression class and defines
 * some common methods.
 * The parseStringToken method is defined in this class and all sub-classes will use.
 * However, the compute method is overridden by each sub-class.
 * 
 * @author El-Yamine Kettal
 *
 */
public class Expression {

    final private static Logger logger = LogManager.getLogger(Expression.class);

    public static int LET = 0;
    public static int ADD = 1;
    public static int DIV = 2;
    public static int MULT = 3;
    public static int VARIABLE = 4;
    public static int VALUE = 5;

    //Expression type. can be LET, ADD, DIV, MULT or VARIABLE or VALUE ()
    //Only LET expression can define a variable.
    //This variable should be the first argument of the LET.
    protected Integer exprType = null;
    protected Map<Integer, Expression> listArgs = null;
    protected int nbrArgs = 0;


    /**
     * @return the exprType
     */
    public int getExpressionType() {
	return exprType;
    }
    /**
     * Sets the expression type.
     * @param exprType: the expression type of the expression.
     */
    public void setExpressionType(int exprType) {
	this.exprType = exprType;
    }
    /**
     * Return list of expressions of the current one.
     * @return Map<Integer, Expression>
     */
    public Map<Integer, Expression> getListArgs() {
	return listArgs;
    }
    /**
     * Set args for the current expression.
     * These args could be any types of another expression.
     * LET, ADD, MULT, DIV, VARIABLE OR VALUE
     * @param args the args to set
     */
    public void setListArgs(Map<Integer, Expression> args) {
	this.listArgs = args;
    }
    /**
     * @return the nbrArgs: expected args for this expression.
     */
    public int getExpectedNbrArgs() {
	return nbrArgs;
    }
    /**
     * Set nbrArgs.
     * @param val
     */
    public void setExpectedNbrArgs(int val) {
	this.nbrArgs = val;
    }
    /**
     * Parse the stringtokenizer tokens passed as argument.
     * Once it find a closing bracket or no more token, then
     * it will return. If it was called by a parent expression
     * this latter will continue parsing if there are more tokens.
     * This method is called recursively each time it finds a new 
     * command expression (add, mult or div).
     * The last bracket could be omitted and it will not throw
     * an error. i.e. this expression is valid although it is missing a closing ')': add(2, add(3,5)   
     * @param str
     * @throws ParsingException
     */
    public void parseStringToken(StringTokenizer str) throws ParsingException {
	String token ;
	int argNumber = 0;
	OperatorExpression arg = null;
	ValueExpression valArg = null;
	VariableExpression varArg = null;
	String lastToken = null;
	while (str.hasMoreTokens()) {
	    token = str.nextToken();
	    if (")".equals(token)) {
		logger.debug("Found \")\" for current expression: " + this.toString());
		if (argNumber < this.getExpectedNbrArgs()) {
		    throw new ParsingException("Error parsing: number of arguments is less than expected.");
		}
		return ;
	    } else if ("add".equals(token)) {
		logger.debug("Found \"add\" for current expression: " + this.toString());
		arg = new OperatorExpression();
		arg.setExpectedNbrArgs(2);
		arg.setExpressionType(Expression.ADD);
		arg.parseStringToken(str);
		argNumber ++;
		if (argNumber > this.getExpectedNbrArgs()) {
		    throw new ParsingException("Error parsing: number of arguments is greater than expected.");
		}
		lastToken = null;
		listArgs.put(argNumber, arg);
	    } else if ("mult".equals(token)) {
		logger.debug("Found \"mult\" for current expression: " + this.getExpressionType());
		arg = new OperatorExpression();
		arg.setExpectedNbrArgs(2);
		arg.setExpressionType(Expression.MULT);
		arg.parseStringToken(str);
		argNumber ++;
		if (argNumber > this.getExpectedNbrArgs()) {
		    throw new ParsingException("Error parsing: number of arguments is greater than expected.");
		}
		lastToken = null;
		listArgs.put(argNumber, arg);
	    } else if ("div".equals(token)) {
		logger.debug("Found \"div\" for current expression type: " + this.toString());
		arg = new OperatorExpression();
		arg.setExpectedNbrArgs(2);
		arg.setExpressionType(Expression.DIV);
		arg.parseStringToken(str);
		argNumber ++;				
		if (argNumber > this.getExpectedNbrArgs()) {
		    throw new ParsingException("Error parsing: number of arguments is greater expected.");
		}
		lastToken = null;
		listArgs.put(argNumber, arg);
	    } else if ("let".equals(token)) {
		logger.debug("Found \"let\" for current expression type: " + this.toString());
		arg = new OperatorExpression();
		arg.setExpectedNbrArgs(3);
		arg.setExpressionType(Expression.LET);
		arg.parseStringToken(str);
		argNumber ++;				
		if (argNumber > this.getExpectedNbrArgs()) {
		    throw new ParsingException("Error parsing: number of arguments is greater than expected.");
		}
		lastToken = null;
		listArgs.put(argNumber, arg);
	    } else if (",".equals(token)) {
		//do nothing. Get the next argument.
		logger.debug("Found \",\" for current expression type: " + this.toString());
		if (lastToken != null && lastToken.equals(",")) {
		    logger.error("Unexpected double \",\" found");
		    throw new ParsingException("Unexpected double \",\" found");
		} else {
		    lastToken = token;
		}
	    } else if ("(".equals(token)) {
		//do nothing. Get the next argument.
		logger.debug("Found \",\" for current expression type: " + this.toString());
		if (lastToken != null && lastToken.equals("(")) {
		    logger.error("Unexpected double \",\" found");
		    throw new ParsingException("Unexpected double \",\" found");
		} else {
		    lastToken = token;
		}
	    } else if(token.matches("[a-z]+")) {
		logger.debug("Found \"" + token + "\" for current expression type: " + this.toString());
		varArg = new VariableExpression(token);
		argNumber ++;				
		if (argNumber > this.getExpectedNbrArgs()) {
		    throw new ParsingException("Error parsing: number of arguments is greater than expected.");
		}
		lastToken = null;
		listArgs.put(argNumber, varArg);
	    } else if (token.matches("[\\-]{0,1}[0-9]+")) {
		//matched a number.
		logger.debug("Found \"" + token + "\" for current expression type: " + this.toString());
		//the parseInt will eventually throw an exception if the integer is not in the limit
		//of Integer.MIN_VALUE and Integer.MAX_VALUE.
		try {
		    valArg = new ValueExpression(Integer.parseInt(token));
		} catch (NumberFormatException ex) {
		    throw new ParsingException("Parsing error. Number is not a valid integer: " + ex.getMessage());
		}
		argNumber ++;				
		if (argNumber > this.getExpectedNbrArgs()) {
		    throw new ParsingException("Parsing error. Encountered more arguments than expected:  " + argNumber + " . Expected: " +
			    this.getExpectedNbrArgs() + " Expression type =" + this.toString());
		}
		lastToken = null;
		listArgs.put(argNumber, valArg);
	    } else {
		logger.error("Exception unexpected token \"" + token + "\" for expression type: " + this.toString());
		throw new ParsingException("Error parsing: encountered unknown token!.");
	    }
	}
    }

    /**
     * Compute the value for the given expression.
     * @param args
     * @return Integer, the calculated value.
     * @throws ParsingException, ComputeException, ArithmeticException
     */
    public Integer compute(Map<String, Integer> args) throws ParsingException, ComputeException, ArithmeticException {
	//No-op method
	logger.error("Unexpected call to Expression.compute()");
	return null;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	if (exprType == LET) {
	    return "let";
	} else if (exprType == ADD) {
	    return "add";
	} else if (exprType == DIV) {
	    return "div";
	} else if (exprType == MULT) {
	    return "mult";
	} else if (exprType == VARIABLE) {
	    return "variable";
	} else if (exprType == LET) {
	    return "value";
	} else {
	    return super.toString();
	}
    }


}
