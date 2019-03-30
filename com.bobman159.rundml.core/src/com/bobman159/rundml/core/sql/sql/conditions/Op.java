package com.bobman159.rundml.core.sql.sql.conditions;

/** 
 * Operator for SQL conditions 
 * 
 * <ul>
 * 	<p> Comparison Operators
 * 	<li>Op.EQ results in an "=" sign
 * 	<li>Op.GT results in an ">" sign
 * 	<li>Op.GTE results in an ">=" sign
 * 	<li>Op.LT results in an "<" sign
 * 	<li>Op.LTE results in an "<=" sign
 * 	<li>Op.NOT results in an "!" sign
 * 	<li>Op.NOTEQ results in an "<>" sign
 *  </ul>
 *
 *  <p> Mathmatical operators
 *  <ul>
 * 	<li>Op.ADD results in an "+" sign
 *  <li>Op.SUB results in an "-" sign
 *  <li>Op.MULT results in an "*" sign
 *  <li>Op.DIVIDE results in an "/" sign
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
		   PARMMARK("?"),
		   CONCAT("||");
		   	   
		   private String operator;
		   
		   private Op (String operator) {
		      this.operator = operator;
		   }
		   
		   /**
		    * Return the string representation for the operator.
		    * @return - the operator
		    */
		   public String getOperator() {
			   return this.operator;
		   }
}
