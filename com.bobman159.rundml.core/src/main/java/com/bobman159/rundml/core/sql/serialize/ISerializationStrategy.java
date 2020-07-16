package com.bobman159.rundml.core.sql.serialize;

/**
 * Execution strategy for the serialization of common SQL clauses
 *
 */
public interface ISerializationStrategy {

	/**
	 * Convert an object model into SQL clause text
	 * @param task information for the serialization
	 * @return an SQL clause as text
	 */
	public String serialize(ICommonSerializationTask task);
}
