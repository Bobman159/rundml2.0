package com.bobman159.rundml.core.sql.serialize.impl;

import com.bobman159.rundml.core.sql.impl.OrderByList;
import com.bobman159.rundml.core.sql.serialize.ICommonSerializationTask.Clause;
import com.bobman159.rundml.core.sql.types.impl.Table;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Base class to serialize SQL SELECT statements for most databases.
 *
 */
public class BaseSelectSerializer extends SQLStatementSerializer {
	
	private static BaseSelectSerializer self;
	
	/**
	 * 
	 * @return an instance of the base SQL serializer.
	 */
	public static BaseSelectSerializer getInstance() {
		if (self == null) {
			self = new BaseSelectSerializer();
		}
		return self;
	}
	
	private BaseSelectSerializer() {
		//Do nothing
	}
	
	/**
	 * Serialize a model FROM into an SQL FROM clause text
	 * @param table the table for the FROM clause
	 * @return the FROM as SQL FROM clause text
	 */
	public String serializeFrom(Table table) {
		CommonSerializationContext context = CommonSerializationContext.createSerializationContext();
		CommonSerializationTask task = CommonSerializationTask.createSerializationTask(Clause.FROM, table);
		FromSerializationStrategy fromStrategy = new FromSerializationStrategy();
		context.setStrategy(fromStrategy);
		String sql = context.executeStrategy(task);
		return CoreUtils.normalizeString(sql);
	}
	
	/**
	 * Serialize a model ORDER BY into an SQL ORDER BY text 
	 * @param orderBys list of ORDER BY entries
	 * @return the ORDER BY as an SQL ORDER BY text
	 */
	public String serializeOrderBy(OrderByList orderBys) {
		
		CommonSerializationContext context = CommonSerializationContext.createSerializationContext();
		CommonSerializationTask task = CommonSerializationTask.createSerializationTask(Clause.ORDERBY, orderBys);
		OrderBySerializationStrategy obStrategy = new OrderBySerializationStrategy();
		context.setStrategy(obStrategy);
		String sql = context.executeStrategy(task);
		return CoreUtils.normalizeString(sql);
		
	}
	
}
