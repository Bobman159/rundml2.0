package com.bobman159.rundml.common.serialize.impl;

import com.bobman159.rundml.common.model.ISQLStatement;
import com.bobman159.rundml.common.model.ISelectStatement;
import com.bobman159.rundml.common.serialize.AbstractSelectSerializer;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * SQL SELECT serializer for Generic (no DBMS specific SELECT syntax) to 
 * generate SELECT statement text.
 * Subclasses of this class should implement a no argument constructor 
 *
 */
public class GenericSelectSerializer extends AbstractSelectSerializer {

	public GenericSelectSerializer() {
		super();
	}
	
	/**
	 * Create a Generic SQL SELECT serializer.
	 */
//	public GenericSelectSerializer(ISelectStatement model) {
//		super(model);
//	}
	
	@Override
	public String serializeSelect() {
		select();
		distinctAll();
		selectExpressions();
		from();
		return CoreUtils.normalizeString(sql.toString());
	}

	@Override
	public String serializeWhere() {

		wherePredicates();
		groupByHaving();
		orderBy();
		return CoreUtils.normalizeString(sql.toString());
	}
	
	@Override
	public String serialize() {
		serializeSelect();
		serializeWhere();
		return CoreUtils.normalizeString(sql.toString());
	}

	@Override
	public void setStatement(ISQLStatement statement) {
		this.model = (ISelectStatement) statement;
	}

}
