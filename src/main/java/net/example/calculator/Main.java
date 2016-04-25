package net.example.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import net.example.calculator.exceptions.ComputeException;
import net.example.calculator.exceptions.ParsingException;
/**
 * This class defines the main method that should get at least 1 argument.
 * 
 * 
 * @author El-Yamine Kettal
 *
 */
public class Main {

    final private static Pattern ROOT_PATTERN = Pattern.compile("^(add|let|div|mult\\()([a-zA-Z0-9,\\-\\(\\)]+)\\)$");
    final private static Logger logger = LogManager.getLogger(Main.class);

    public static void main (String[] args) {
	/*
	 * add(1, 2)	                                        3
	 * add(1, mult(2, 3))	                                7
	 * mult(add(2, 2), div(9, 3))	                        12
	 * let(a, 5, add(a, a))	                                10
	 * let(a, 5, let(b, mult(a, 10), add(b, a)))	        55
	 * let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))	40
	 */
	//This regex pattern verifies that the expression meets a basic format
	//regarding the root command. The rest will be validated during the parsing.
	if (args == null || (args.length != 3 && args.length != 1)) {
	    System.out.println("Usage: program <command> [-log_level <level>]");
	    System.out.println("<command>: the expression to be evaluated.");
	    System.out.println("<level>: the log level. Expected values: 'debug', 'info', 'warn', 'error' or 'all'");
	    return;
	}
	if (args.length == 3 && args[1].equalsIgnoreCase("-log_level")) {
	    if (args[2].equalsIgnoreCase("debug")) {
		setLevel(Level.DEBUG);
	    } else if (args[2].equalsIgnoreCase("info")) {
		setLevel(Level.INFO);
	    } else if (args[2].equalsIgnoreCase("warn")){
		setLevel(Level.WARN);
	    } else if (args[2].equalsIgnoreCase("error")){
		setLevel(Level.ERROR);
	    } else if (args[2].equalsIgnoreCase("all")){
		setLevel(Level.ALL);
	    } else {
		logger.error("Unrecognized log level value. Should be \"debug\", \"info\", \"warn\", \"error\" or \"all\".");
		logger.error("Using default log level: \"error\".");
		setLevel(Level.ERROR);
	    }
	}
	String expr = args[0].trim();
	//Remove all white spaces.
	expr = expr.replaceAll("\\s+","");
	//Lower the cases. Therefore, the variables and expressions are case insensitive.  
	expr = expr.toLowerCase();
	String sub_expr = null;
	Matcher matcher = ROOT_PATTERN.matcher(expr);
	//Root command
	Expression command = null;
	//Validate the whole expression. This is to make sure that
	//we have let, add, div or mult at the beginning of the expression.
	//also it checks there are no other special characters except ')', '(', ',' and '-'
	logger.debug("Starting the parsing...");
	if (matcher.matches()) {
	    command = new OperatorExpression();
	    if (expr.startsWith("add")) {
		logger.info("add expression...");
		sub_expr = Util.stripCommand(expr, "add(");
		command.setExpectedNbrArgs(2);
		command.setExpressionType(Expression.ADD);
	    } else if (expr.startsWith("div")) {
		logger.info("div expression...");
		sub_expr = Util.stripCommand(expr, "div(");
		command.setExpectedNbrArgs(2);
		command.setExpressionType(Expression.DIV);
	    } else if (expr.startsWith("mult")) {
		logger.info("mult expression...");
		sub_expr = Util.stripCommand(expr, "mult(");
		command.setExpectedNbrArgs(2);
		command.setExpressionType(Expression.MULT);
	    } else {
		logger.info("let expression...");
		command.setExpectedNbrArgs(3);
		command.setExpressionType(Expression.LET);
		sub_expr = Util.stripCommand(expr, "let(");
	    }
	    //Parse the sub-expression and evaluate the whole expression.
	    try {
		logger.debug("Starting the parsing of sub-command...");
		StringTokenizer a = new StringTokenizer(sub_expr, "(),", true);
		command.parseStringToken(a);
		Map<String, Integer> varList = new HashMap<String, Integer>();
		logger.debug("Starting the evaluation of expression...");
		int val = command.compute(varList);
		//Printing result
		logger.info("Result= " +val);
		System.out.println(val);
	    } catch (ParsingException ex) {
		logger.error("Parsing exception occured: " + ex.getMessage());
	    } catch (ArithmeticException ex) {
		logger.error("Arithmetic exception occured:" + ex.getMessage());	    	 
	    } catch (ComputeException ex) {
		logger.error("Compute exception occured:" + ex.getMessage());	    	 
	    } catch (Exception e) {
		logger.error("Unexpected exception occured: " + e.getMessage());
	    }
	} else {
	    logger.error("Error invalid expression! ", expr);
	}
    }
    public static void setLevel (Level level) {
	logger.info(level);
	LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	Configuration config = ctx.getConfiguration();
	LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
	LoggerConfig loggerApp = config.getLoggerConfig("net.example.calculator");
	loggerConfig.setLevel(level);
	if (loggerApp != null) {
	    loggerApp.setLevel(level);
	} else {
	    logger.error("Error: logger name \"net.example.calculator\" was not found in the config file");
	}
	ctx.updateLoggers();
    }
}