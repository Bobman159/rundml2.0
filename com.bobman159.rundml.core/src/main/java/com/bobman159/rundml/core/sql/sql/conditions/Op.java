package com.bobman159.rundml.core.sql.sql.conditions;

/** 
 * Operator for SQL conditions 
 * 
 * <p> Comparison Operators</p>
 * <ul>
 * 	
 * 	<li>Op.EQ results in an "=" sign</li>
 * 	<li>Op.GT results in an "&gt;" sign</li>
 * 	<li>Op.GTE results in an "&gt;=" sign</li>
 * 	<li>Op.LT results in an "&lt;" sign</li>
 * 	<li>Op.LTE results in an "&lt;=" sign</li>
 * 	<li>Op.NOT results in an "!" sign</li>
 * 	<li>Op.NOTEQ results in an "&lt;&gt;" sign</li>
 *  </ul>
 *
 *  <p> Mathmatical operators
 *  <ul>
 * 	<li>Op.ADD results in an "+" sign</li>
 *  <li>Op.SUB results in an "-" sign</li>
 *  <li>Op.MULT results in an "*" sign</li>
 *  <li>Op.DIVIDE results in an "/" sign</li>
 *</ul>
 *
 *
 */
public enum Op {
		   EQ("="),
		   GT(">"), 
		   GTE(">="), 
		   LT("<"),
		   LTE("<="),
		   NOT("!"),
		   NOTEQ("<>"),
		   ADD("+"),
		   SUB("-"),
		   MULT("*"),
		   DIVIDE("/"),
		   CONCAT("||");
		   	   
		   private String operator;
		   
		   private Op (String operator) {
		      this.operator = operator;
		   }
		   
		   /**
		    * Return the string representation for the operator.
		    * @return the operator
		    */
		   public String opToString() {
			   return this.operator;
		   }
}
