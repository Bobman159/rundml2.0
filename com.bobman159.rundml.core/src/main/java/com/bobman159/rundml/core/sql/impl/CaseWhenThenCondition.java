package com.bobman159.rundml.core.sql.impl;

import com.bobman159.rundml.core.sql.ICaseWhenConditions;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Defines a single "WHEN ISQLType THEN expression for a CASE or CASE WHEN  SQL expression.
 *
 */
public class CaseWhenThenCondition implements ICaseWhenConditions {
	ISQLType when;
	ISQLType then;
	
	/**
	 * Define the WHEN <code>expr</code> THEN <code>expr</code> condition using an SQL expression 
	 * @param when the WHEN SQL expression
	 * @param then the THEN SQL expression
	 */
	public CaseWhenThenCondition(ISQLType when, ISQLType then) {
		this.when = when;
		this.then = then;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseWhenConditions#getWhenCondition()
	 */
	@Override
	public ISQLType getWhenCondition() {
		return this.when;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseWhenConditions#getThenCondition()
	 */
	@Override
	public ISQLType getThenCondition() {
		return this.then;
	}

}