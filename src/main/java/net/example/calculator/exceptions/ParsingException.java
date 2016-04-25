package net.example.calculator.exceptions;
/**
 * Sub-class of exception. It is thrown when an exception is encountered 
 * during the parsing.
 * 
 * @author El-Yamine Kettal
 *
 */
public class ParsingException extends Exception {
    
    private static final long serialVersionUID = 2256477558314496227L;
    
    public ParsingException (String msg) {
	super(msg);
    }
}
