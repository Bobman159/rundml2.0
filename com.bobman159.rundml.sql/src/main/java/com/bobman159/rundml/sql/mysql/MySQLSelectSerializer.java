package com.bobman159.rundml.sql.mysql;

import com.bobman159.rundml.common.serialize.impl.GenericSelectSerializer;
import com.bobman159.rundml.core.util.CoreUtils;


public class MySQLSelectSerializer extends GenericSelectSerializer {

	public MySQLSelectSerializer() {
		super();
	}
	
	@Override
	public String serializeSelect() {
		select();
		distinctAll();

		if (((IMySQLSelectStatement) model).hasSmallResult()) {
			sql.append(" sql_small_result ").append(" ");
		} else if (((IMySQLSelectStatement) model).hasBigResult()) {
			sql.append(" sql_big_result ").append(" ");
		}
		if (((IMySQLSelectStatement) model).hasBufferResult()) {
			sql.append(" sql_buffer_result ").append(" ");
		}
		selectExpressions();
		from();
		return CoreUtils.normalizeString(sql.toString());
	}

	@Override
	public String serializeWhere() {

		wherePredicates();
		groupByHaving();
		orderBy();
		IMySQLSelectStatement mySQLSelectModel = (IMySQLSelectStatement) model;
		if (mySQLSelectModel.getLimit() != null) {
			sql.append(" limit ");
			sql.append(serialize(mySQLSelectModel.getLimit()));
			sql.append(" ");
		}
		if (mySQLSelectModel.getOffset() != null) {
			sql.append(" offset ");
			sql.append(serialize(mySQLSelectModel.getOffset()));
			sql.append(" ");
		}
		return CoreUtils.normalizeString(sql.toString());
	}

}
