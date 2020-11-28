package com.bobman159.rundml.sql.h2;

import com.bobman159.rundml.common.serialize.impl.GenericSelectSerializer;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Serialize H2 SELECT SQL statement clauses as text 
 * from an H2 SELECT statement
 *
 */
public class H2SelectSerializer extends GenericSelectSerializer {

	/**
	 * Create an H2 SELECT statement serializer
	*/
	public H2SelectSerializer() {
		super();
	}
	
	
	@Override
	public String serializeSelect() {
		select();
		if (((IH2SelectStatement) model).getTop() != null) {			
			sql.append(" top ").append(" ");
			sql.append(serialize(((IH2SelectStatement) model).getTop()));
			sql.append(" ");
		}
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
		IH2SelectStatement h2SelectModel = (IH2SelectStatement) model;
		if (h2SelectModel.getLimit() != null) {
			sql.append(" limit ");
			sql.append(serialize(h2SelectModel.getLimit()));
			sql.append(" ");
		}
		if (h2SelectModel.getOffset() != null) {
			sql.append(" offset ");
			sql.append(serialize(h2SelectModel.getOffset()));
			sql.append(" ");
		}
		return CoreUtils.normalizeString(sql.toString());
	}

}
