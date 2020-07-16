package com.bobman159.rundml.core.mockclasses.test;

import com.bobman159.rundml.core.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.mapping.exceptions.IFieldMap;

public class FieldMapInstantiationException implements IFieldMap {

	private int fieldOne;
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
