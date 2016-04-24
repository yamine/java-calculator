package net.example.calculator;

final class Util
{
    /**
     * Remove the hyphens from the beginning of <code>str</code> and
     * return the new String.
     *
     * @param str The string from which the hyphens should be removed.
     *
     * @return the new String.
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
