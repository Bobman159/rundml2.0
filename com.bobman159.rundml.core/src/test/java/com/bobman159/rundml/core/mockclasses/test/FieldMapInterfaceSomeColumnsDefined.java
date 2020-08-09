package com.bobman159.rundml.core.mockclasses.test;

import com.bobman159.rundml.core.model.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.model.mapping.IFieldMap;

public class FieldMapInterfaceSomeColumnsDefined implements IFieldMap {
	
	@SuppressWarnings("unused")
	private int tableCol1;
	@SuppressWarnings("unused")
	private int field2;
	@SuppressWarnings("unused")
	private int tableCol3;
	@SuppressWarnings("unused")
	private int field4;

	@Override
	public FieldMapDefinitionList getFieldMappings(FieldMapDefinitionList fieldList) {

		fieldList.addDefinition("tableCol2", "field2");
		fieldList.addDefinition("tableCol4","field4");
		return fieldList;
	}

}
