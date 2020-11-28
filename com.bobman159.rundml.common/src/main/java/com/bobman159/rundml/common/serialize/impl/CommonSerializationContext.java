package com.bobman159.rundml.common.serialize.impl;

import com.bobman159.rundml.common.serialize.ICommonSerializationTask;
import com.bobman159.rundml.common.serialize.ISerializationStrategy;

/**
 * Communication with ISerializationStrategy implementations.
 *
 */
public class CommonSerializationContext {

	private ISerializationStrategy strategy;
	
	/**
	 * Create a serialization stratgey context
	 * @return the new context
	 */
	public static CommonSerializationContext createSerializationContext() {
		return new CommonSerializationContext();
	}
	
	/**
	 * Specify the strategy to be executed
	 * @param strategy the strategy to be used
	 */
	public void setStrategy(ISerializationStrategy strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * Execute the serialization strategy to generate the SQL clause text
	 * @param task the task information for the strategy.
	 * @return the SQL clause as text
	 */
	public String executeStrategy(ICommonSerializationTask task) {
		return strategy.serialize(task);
	}
	
}
