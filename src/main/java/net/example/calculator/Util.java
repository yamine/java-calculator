package net.example.calculator;
/**
 * Utility class that defines only one method for now.
 * 
 * @author El-Yamine Kettal
 *
 */
final class Util
{
    /**
     * This class removes the string passed as argument and the last character.
     * Then returns the resulting string.
     * As an example: this 'add(a,b)' will be returned as a,b if the 2nd argument
     * is 'add('.
     * 
     * @param str
     * @param cmd
     * @return String
     */
    static String stripCommand(String str, String cmd)
    {
	if (str == null)
	{
	    return null;
	} else if (cmd == null || cmd.isEmpty()) 
	{
	    return str;
	} else {
	    return str.substring(cmd.length(), str.length()-1);
	}
    }

}
