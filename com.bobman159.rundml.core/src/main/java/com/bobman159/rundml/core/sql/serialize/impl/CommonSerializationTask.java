package com.bobman159.rundml.core.sql.serialize.impl;

import com.bobman159.rundml.core.sql.serialize.ICommonSerializationTask;

/**
 * Information for a common SQL serialization
 *
 */
public class CommonSerializationTask implements ICommonSerializationTask {

	private Clause clause;
	private Object model;
	
	/**
	 * Factory method for creating a serialization task information
	 * @param clause the clause to be serialized
	 * @param model the model information for serialization
	 * @return a serialization task
	 */
	public static CommonSerializationTask createSerializationTask(Clause clause,Object model) {
		return new CommonSerializationTask(clause,model);
	}
	
	private CommonSerializationTask(Clause clause,Object model) {
		this.clause = clause;
		this.model = model;
	}
	
	@Override
	public Clause getClause() {
		return clause;
	}

	@Override
	public Object getModel() {
		return model;
	}

}
