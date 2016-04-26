package net.example.calculator;


import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class tests the following 
 * add(1, 2)	                                        3
 * add(1, mult(2, 3))	                                7
 * mult(add(2, 2), div(9, 3))	                        12
 * let(a, 5, add(a, a))	                                10
 * let(a, 5, let(b, mult(a, 10), add(b, a)))	        55
 * let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))	40
 */

public class OperatorExpressionTest {

   // Testing let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))
   // The returned result should be equal to 40. 
   @Test
   public void test1() {
      OperatorExpression command = new OperatorExpression ();
      command.setExpectedNbrArgs(3);
      command.setExpressionType(Expression.LET);
      //Remove white spaces and trim the command with its parentheses
      String sub_expr = Util.stripCommand("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))".replaceAll("\\s+",""), "let(");
      StringTokenizer strToken = new StringTokenizer(sub_expr, "(),", true);
      try {
	  command.parseStringToken(strToken);
          Map<String, Integer> varList = new HashMap<String, Integer>();
          int val = command.compute(varList);
          Assert.assertEquals(val, 40);
      } catch (Exception e) {
	  //Error could be logged but is just printed to the console.
	  System.out.println("test1 error: " + e.getMessage());
	  Assert.fail();
      }
   }
   // Testing let(a, 5, let(b, mult(a, 10), add(b, a)))
   // The returned result should be equal to 55. 
   @Test
   public void test2() {
      OperatorExpression command = new OperatorExpression ();
      command.setExpectedNbrArgs(3);
      command.setExpressionType(Expression.LET);
      //Remove white spaces and trim the command with its parentheses
      String sub_expr = Util.stripCommand("let(a, 5, let(b, mult(a, 10), add(b, a)))".replaceAll("\\s+",""), "let(");
      StringTokenizer strToken = new StringTokenizer(sub_expr, "(),", true);
      try {
	  command.parseStringToken(strToken);
          Map<String, Integer> varList = new HashMap<String, Integer>();
          int val = command.compute(varList);
          Assert.assertEquals(val, 55);
      } catch (Exception e) {
	  //Error could be logged but for now it is just printed to the console.
	  System.out.println("test2 error: " + e.getMessage());
	  Assert.fail();
      }
   }
   // Testing let(a, 5, add(a, a))
   // The returned result should be equal to 10. 
   @Test
   public void test3() {
      OperatorExpression command = new OperatorExpression ();
      command.setExpectedNbrArgs(3);
      command.setExpressionType(Expression.LET);
      //Remove white spaces and trim the command with its parentheses
      String sub_expr = Util.stripCommand("let(a, 5, add(a, a))".replaceAll("\\s+",""), "let(");
      StringTokenizer strToken = new StringTokenizer(sub_expr, "(),", true);
      try {
	  command.parseStringToken(strToken);
          Map<String, Integer> varList = new HashMap<String, Integer>();
          int val = command.compute(varList);
          Assert.assertEquals(val, 10);
      } catch (Exception e) {
	  //Error could be logged but for now it is just printed to the console.
	  System.out.println("test3 error: " + e.getMessage());
	  Assert.fail();
      }
   }
   // Testing mult(add(2, 2), div(9, 3))
   // The returned result should be equal to 10. 
   @Test
   public void test4() {
      OperatorExpression command = new OperatorExpression ();
      command.setExpectedNbrArgs(2);
      command.setExpressionType(Expression.MULT);
      //Remove white spaces and trim the command with its parentheses
      String sub_expr = Util.stripCommand("mult(add(2, 2), div(9, 3))".replaceAll("\\s+",""), "mult(");
      StringTokenizer strToken = new StringTokenizer(sub_expr, "(),", true);
      try {
	  command.parseStringToken(strToken);
          Map<String, Integer> varList = new HashMap<String, Integer>();
          int val = command.compute(varList);
          Assert.assertEquals(val, 12);
      } catch (Exception e) {
	  //Error could be logged but for now it is just printed to the console.
	  System.out.println("test4 error: " + e.getMessage());
	  Assert.fail();
      }
   }
   // Testing add(1, mult(2, 3))
   // The returned result should be equal to 7. 
   @Test
   public void test5() {
      OperatorExpression command = new OperatorExpression ();
      command.setExpectedNbrArgs(2);
      command.setExpressionType(Expression.ADD);
      //Remove white spaces and trim the command with its parentheses
      String sub_expr = Util.stripCommand("add(1, mult(2, 3))".replaceAll("\\s+",""),"add(");
      StringTokenizer strToken = new StringTokenizer(sub_expr, "(),", true);
      try {
	  command.parseStringToken(strToken);
          Map<String, Integer> varList = new HashMap<String, Integer>();
          int val = command.compute(varList);
          Assert.assertEquals(val, 7);
      } catch (Exception e) {
	  //Error could be logged but for now it is just printed to the console.
	  System.out.println("test5 error: " + e.getMessage());
	  Assert.fail();
      }
   }
   // Testing add(1, 2)
   // The returned result should be equal to 3. 
   @Test
   public void test6() {
      OperatorExpression command = new OperatorExpression ();
      command.setExpectedNbrArgs(2);
      command.setExpressionType(Expression.ADD);
      //Remove white spaces and trim the command with its parentheses
      String sub_expr = Util.stripCommand("add(1, 2)".replaceAll("\\s+",""),"add(");
      StringTokenizer strToken = new StringTokenizer(sub_expr, "(),", true);
      try {
	  command.parseStringToken(strToken);
          Map<String, Integer> varList = new HashMap<String, Integer>();
          int val = command.compute(varList);
          Assert.assertEquals(val, 3);
      } catch (Exception e) {
	  //Error could be logged but for now it is just printed to the console.
	  System.out.println("test6 error: " + e.getMessage());
	  Assert.fail();
      }
   }
}
