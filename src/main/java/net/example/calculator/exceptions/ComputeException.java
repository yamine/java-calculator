package net.example.calculator.exceptions;
/**
 * Defines the Compute exception that could happen during the computing of the expressions.
 * 
 * @author El-Yamine Kettal
 *
 */
public class ComputeException extends Exception {

    private static final long serialVersionUID = 2256477558314496117L;

    public ComputeException (String msg) {
	super(msg);
    }
}