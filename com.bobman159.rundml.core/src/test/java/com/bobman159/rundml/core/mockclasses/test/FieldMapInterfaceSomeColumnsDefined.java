package com.bobman159.rundml.core.mockclasses.test;

import com.bobman159.rundml.core.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.mapping.exceptions.IFieldMap;

public class FieldMapInterfaceSomeColumnsDefined implements IFieldMap {
	
	private int tableCol1;
	private int field2;
	private int tableCol3;
	private int field4;

	@Override
	public FieldMapDefinitionList getFieldMappings(FieldMapDefinitionList fieldList) {

		fieldList.addDefinition("tableCol2", "field2");
		fieldList.addDefinition("tableCol4","field4");
		return fieldList;
	}

}
