package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.expressions.impl.StringExpression;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * SQL String expression such as concatenation in an SQL expression.  
 * 
 */
public interface IStringExpression {
	
	/**
	 * Define a concatenation operation between two <code>SQLStrings</code>
	 * SQLStrings are automatically escaped with ''
	 * <p> example : Expression.string("abc").concat("def") =&lt; "'abcdef'"
	 * 
	 * @param rightExpr the string to the right of the concat operator
	 * @return An expression representing the concatenated strings
	 */
	public StringExpression concat(String rightExpr);

	/**
	 * Define a concatenation operation between an SQL String and a Column
	 * <p> ex : Column col1 = new Column("Col01",Types.CHAR); 
	 * <p>	colLhs.concat("def") =&lt; COL01 || 'def'
	 * 
	 * @param rightExpr the column to the right of the concat operator
	 * @return An expression representing the concatenated strings
	 */
	public StringExpression concat(ISQLType rightExpr);
	
}
