package net.example.calculator.exceptions;

public class ParsingException extends Exception {
	
	private static final long serialVersionUID = 2256477558314496227L;
	
     public ParsingException (String msg) {
    	 super(msg);
     }
}
