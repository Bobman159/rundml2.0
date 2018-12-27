package net.bobs.own.db.rundml.sql.builders;

public enum Operator {
		   EQ(" = "),
		   GT(" > "), 
		   GTE(" >= "), 
		   LT(" < "),
		   LTE(" <= "),
		   NOT(" ! "),
		   NOTEQ(" != ");
		   	   
		   private String operator;
		   
		   private Operator (String operator) {
		      this.operator = operator;
		   }
		   
		   public String getOperator() {
			   return this.operator;
		   }
}
