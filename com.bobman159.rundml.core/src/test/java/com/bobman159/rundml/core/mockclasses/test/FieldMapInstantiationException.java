package com.bobman159.rundml.core.mockclasses.test;

import com.bobman159.rundml.core.model.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.model.mapping.IFieldMap;

public class FieldMapInstantiationException implements IFieldMap {

	@SuppressWarnings("unused")
	private int fieldOne;
	@SuppressWarnings("unused")
	private int fieldTwo;
	
	private FieldMapInstantiationException() {
		fieldOne = 10;
		fieldTwo = 20;
	}
	
	@Override
	public FieldMapDefinitionList getFieldMappings(FieldMapDefinitionList fieldList) {

		fieldList.addDefinition("col01", "fieldOne");
		fieldList.addDefinition("col02", "fieldTwo");
		return fieldList;
	}

	

}
