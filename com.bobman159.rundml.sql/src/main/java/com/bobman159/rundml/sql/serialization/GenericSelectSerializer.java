package com.bobman159.rundml.sql.serialization;

import com.bobman159.rundml.core.model.ISQLStatement;
import com.bobman159.rundml.core.model.ISelectStatement;
import com.bobman159.rundml.core.sql.AbstractSelectSerializer;
import com.bobman159.rundml.core.util.CoreUtils;
import com.bobman159.rundml.sql.generic.GenericSelectStatement;

/**
 * SQL SELECT serializer for Generic (no DBMS specific SELECT syntax) to 
 * generate SELECT statement text.
 *
 */
public class GenericSelectSerializer extends AbstractSelectSerializer {

	/**
	 * Create a Generic SQL SELECT serializer.
	 */
	public GenericSelectSerializer() {
		super(new GenericSelectStatement());
	}
	
	@Override
	public String serializeSelect() {
		select();
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
	public String serializeSQLStatement() {
		serializeSelect();
		serializeWhere();
		return CoreUtils.normalizeString(sql.toString());
	}

	@Override
	public void setStatement(ISQLStatement statement) {
		this.model = (ISelectStatement) statement;
	}

}
