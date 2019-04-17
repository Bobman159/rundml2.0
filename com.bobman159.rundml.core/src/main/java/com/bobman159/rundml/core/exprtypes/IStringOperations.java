package com.bobman159.rundml.core.exprtypes;

import com.bobman159.rundml.core.sql.sql.conditions.Op;

/**
 * Defines String operations for SQL string expressions.  Default implementations
 * are defined to provide a mixin style implementation for the string 
 * operations.
 *
 */
public interface IStringOperations {
	
	/**
	 * Define a concatenation operation between two <code>SQLStrings</code>
	 * SQLStrings are automatically escaped with ''
	 * <p> example : Expression.string("abc").concat("def") =&lt; "'abcdef'"
	 * 
	 * @param rhs the string to the right of the concat operator
	 * @return An expression representing the concatenated strings
	 */
	public default StringOperation concat(String rhs) {
		return ExprTypeHelper.stringOperation((IExpression) this, rhs, Op.CONCAT);
	}
	
	/**
	 * Define a concatenation operation between an SQL String and a Column
	 * <p> ex : Column col1 = new Column("Col01",Types.CHAR); 
	 * <p>	colLhs.concat("def") =&lt; COL01 || 'def'
	 * 
	 * @param rhs the column to the right of the concat operator
	 * @return An expression representing the concatenated strings
	 */
	public default StringOperation concat (IExpression rhs) {
		return ExprTypeHelper.stringOperation((IExpression) this, rhs, Op.CONCAT);
	}
	
}
