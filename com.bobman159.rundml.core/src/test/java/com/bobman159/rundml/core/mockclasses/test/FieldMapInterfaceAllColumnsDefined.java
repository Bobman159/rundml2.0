package com.bobman159.rundml.core.mockclasses.test;

import com.bobman159.rundml.core.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.mapping.IFieldMap;

public class FieldMapInterfaceAllColumnsDefined implements IFieldMap {

	private int	classField1;
	private int classField2;
	private int classField3;
	private int classField4;
	private int classField5;
	
	@Override
	public FieldMapDefinitionList getFieldMappings(FieldMapDefinitionList fieldList) {

		fieldList.addDefinition("tableCol1", "classField1");
		fieldList.addDefinition("tableCol2", "classField2");
		fieldList.addDefinition("tableCol3", "classField3");
		fieldList.addDefinition("tableCol4","classField4");
		fieldList.addDefinition("tableCol5", "classField5");
		return fieldList;
	}

}
