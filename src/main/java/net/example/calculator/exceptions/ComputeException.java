package net.example.calculator.exceptions;

public class ComputeException extends Exception {
	
	private static final long serialVersionUID = 2256477558314496117L;
	
     public ComputeException (String msg) {
    	 super(msg);
     }
}