package com.bobman159.rundml.core.sql.serialize.impl;

import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.IOrderByEntry.NullsOrder;
import com.bobman159.rundml.core.sql.IOrderByEntry.SortOrder;
import com.bobman159.rundml.core.sql.impl.OrderByList;
import com.bobman159.rundml.core.sql.serialize.ICommonSerializationTask;
import com.bobman159.rundml.core.sql.serialize.ISerializationStrategy;
import com.bobman159.rundml.core.sql.serialize.ICommonSerializationTask.Clause;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Serialization Strategy to convert an SQL model for a GROUP BY into SQL clause text
 *
 */
public class OrderBySerializationStrategy implements ISerializationStrategy {

	
	/**
	 * @see com.bobman159.rundml.core.sql.serialize.ISerializationStrategy#serialize(ICommonSerializationTask)
	 */
	@Override
	public String serialize(ICommonSerializationTask task) {
		
		String sql = null;
		
		if ((Clause.ORDERBY.equals(task.getClause())) &&
			task.getModel() != null) {
			if (task.getModel() instanceof OrderByList) {
				OrderByList orderBys = (OrderByList) task.getModel();
				sql = serializeOrderBy(orderBys);
			}
		} else {
			throw new IllegalStateException("OrderBySerialization called for non ORDER BY model type");
		}
		return sql;
	}

	private String serializeOrderBy(OrderByList orderBys) {
	
		StringBuilder sql = new StringBuilder();
		BaseSQLSerializer serializer = new BaseSQLSerializer();
		
		sql.append("order by").append(" ");
		Iterable<IOrderByEntry> orderByList = orderBys.iteratable();
		int ix = 1;
		for (IOrderByEntry entry : orderByList) {
			sql.append(serializer.serialize(entry.getOrderByValue()));
			
			if (entry.getSortOrder() != null) {
				sql.append(" ");
				if (SortOrder.ASC.equals(entry.getSortOrder())) {
					sql.append("asc").append(" ");
				} else if (SortOrder.DESC.equals(entry.getSortOrder())) {
					sql.append("desc").append(" ");
				}
			}
			
			if (entry.getNullsOrder() != null) {
				if (NullsOrder.NULLS_FIRST.equals(entry.getNullsOrder())) {
					sql.append("nulls first");
				} else if (NullsOrder.NULLS_LAST.equals(entry.getNullsOrder())) {
					sql.append("nulls last");
				}
			}
			if (ix < orderBys.size()) {
				sql.append(",");
			}
			ix++;
		}
		return CoreUtils.normalizeString(sql.toString());
	}

}
