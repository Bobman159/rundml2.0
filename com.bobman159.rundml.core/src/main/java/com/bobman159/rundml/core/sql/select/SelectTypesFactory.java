package com.bobman159.rundml.core.sql.select;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.impl.OrderByEntry;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Base Factory class for SQL SELECT statement clause types
 *
 */
public class SelectTypesFactory extends SQLTypeFactory {
	
	private static SelectTypesFactory self;
	
	/**
	 * 
	 * @return an instance of this factory
	 */
	public static SelectTypesFactory getInstance() {
		if (self == null) {
			self = new SelectTypesFactory();
		}
		return self;
	}
	
	private SelectTypesFactory() {
		super();
	}

	/**
	 * Create an ORDER BY number clause entry IE ORDER BY 1 
	 * @param position number argument
	 * @return an <code>OrderByEntry</code>
	 */
	public IOrderByEntry orderByEntry(int position) {

		IOrderByEntry entry = null;
		entry = new OrderByEntry(constant(position));

		return entry;
		
	}
	
	/**
	 * Create an ORDER BY sql type clause entry IE ORDER BY COL01
	 * @param expr SQL column argument
	 * @return an <code>OrderByEntry</code>
	 */
	public IOrderByEntry orderByEntry(ISQLType expr) {
		
		IOrderByEntry entry = null;
		entry = new OrderByEntry(expr);
		return entry;
	}
	
	/**
	 * Create an ORDER BY expression clause entry IE ORDER BY 10 + 10 
	 * @param expr SQL expression argument
	 * @return an <code>OrderByEntry</code>
	 */
	public IOrderByEntry orderByEntry(IExpressionNode expr) {
		
		IOrderByEntry entry = null;
		entry = new OrderByEntry(expr);
		return entry;
	}
}
