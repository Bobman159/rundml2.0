package com.bobman159.rundml.common.serialize.impl;

import java.util.List;

import com.bobman159.rundml.common.serialize.ICommonSerializationTask;
import com.bobman159.rundml.common.serialize.ICommonSerializationTask.Clause;
import com.bobman159.rundml.common.serialize.ISerializationStrategy;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.IOrderByEntry.NullsOrder;
import com.bobman159.rundml.core.sql.IOrderByEntry.SortOrder;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Serialization Strategy to convert an SQL model for a GROUP BY into SQL clause text
 *
 */
public class OrderBySerializationStrategy implements ISerializationStrategy {

	
	/**
	 * @see com.bobman159.rundml.common.serialize.ISerializationStrategy#serialize(ICommonSerializationTask)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String serialize(ICommonSerializationTask task) {
		
		String sql = null;
		
		if ((Clause.ORDERBY.equals(task.getClause())) &&
			task.getModel() != null) {
				sql = serializeOrderBy((List<IOrderByEntry>) task.getModel());
		} else {
			throw new IllegalStateException("OrderBySerialization called for non ORDER BY model type");
		}
		return sql;
	}

	private String serializeOrderBy(List<IOrderByEntry> orderBys) {
	
		StringBuilder sql = new StringBuilder();
		SQLTypeSerializer serializer = new SQLTypeSerializer();
		
		sql.append("order by").append(" ");
		int ix = 1;
		for (IOrderByEntry entry : orderBys) {
			sql.append(serializer.serialize(entry.getOrderByValue()));
			
			if (entry.getSortOrder() != null) {
				if (SortOrder.ASC.equals(entry.getSortOrder())) {
					sql.append(" asc");
				} else if (SortOrder.DESC.equals(entry.getSortOrder())) {
					sql.append(" desc");
				}
			}
			
			if (entry.getNullsOrder() != null) {
				sql.append(" ");
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
