package com.bobman159.rundml.common.serialize.impl;

import com.bobman159.rundml.common.serialize.ICommonSerializationTask;
import com.bobman159.rundml.common.serialize.ICommonSerializationTask.Clause;
import com.bobman159.rundml.common.serialize.ISerializationStrategy;
import com.bobman159.rundml.core.sql.types.impl.Table;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Serialization strategy to convert a SQL model FROM clause to SQL text
 *
 */
public class FromSerializationStrategy implements ISerializationStrategy {

	/**
	 * Serialize an SQL model FROM clause to SQL text
	 */
	@Override
	public String serialize(ICommonSerializationTask task) {
		
		String sql = null;
		if (Clause.FROM.equals(task.getClause()) &&
			(task.getModel() instanceof Table)) {
			Table fromTb = (Table) task.getModel();
			sql = "from ";
			sql = sql + fromTb.tableName() + " ";
		}
		return CoreUtils.normalizeString(sql);
	}

}
