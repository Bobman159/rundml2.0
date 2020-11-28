package com.bobman159.rundml.sql.h2;

import com.bobman159.rundml.common.serialize.AbstractStatementSerializer;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.sql.builders.impl.GenericSelectBuilder;
import com.bobman159.rundml.sql.factory.SQLStatementBuilderFactory;

/**
 * Define a SELECT statement that can be executed against a table in an 
 * H2 database.
 *
 */
public class H2SelectBuilder extends GenericSelectBuilder<H2SelectBuilder> {
	
	/**
	 * Create a SELECT statement that may be executed against an H2 database
	 * 
	 */
	public H2SelectBuilder() {
		super((IH2SelectStatement) SQLStatementBuilderFactory.getInstance().
				createSelectModel(AbstractStatementSerializer.H2_SELECT));
	}		
	
	/**
	 * Specify a LIMIT clause for the SELECT statement
	 * @param limitTerm an expression to limit the number of results returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.types.IExpression
	 * 
	 */

	public H2SelectBuilder limit(ISQLType limitTerm) {
		castToH2Select().addLimit(limitTerm);
		return this;
	}
	
	/**
	 * Specify an OFFSET clause for the LIMIT clause in a SELECT
	 * statement.
	 * @param offset specifies how many rows to skip
	 * @return the SELECT statement builder
	 */
	public H2SelectBuilder offset(ISQLType offset) {
		castToH2Select().addOffset(offset);
		return this;
	}

	/**
	 * Specify a TOP clause for the SELECT statement 
	 * @param topNumber the maximum number of rows to return
	 * @return the SELECT statement builder
	 */
	public H2SelectBuilder top(int topNumber) {
		castToH2Select().addTop(topNumber);
		return this;
	}
	
	/**
	 * Specify a TOP clause for the SELECT statement
	 * @param topExpr an expression that limits the number of rows returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.types.IExpression
	 * 
	 */
	public H2SelectBuilder top(ISQLType topExpr) {
		castToH2Select().addTop(topExpr);
		return this;
	}
	
	private IH2SelectStatement castToH2Select() {
		if (model instanceof IH2SelectStatement) {
			return (IH2SelectStatement) model;
		} else {
			throw new IllegalArgumentException("model is not an istance of IH2SelectStatement");
		}
	}
	
}
