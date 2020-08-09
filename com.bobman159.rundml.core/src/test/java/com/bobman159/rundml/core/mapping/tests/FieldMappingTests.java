package com.bobman159.rundml.core.mapping.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.mapping.exceptions.NoTableRowClassFieldException;
import com.bobman159.rundml.core.mockclasses.test.FieldMapInterfaceAllColumnsDefined;
import com.bobman159.rundml.core.mockclasses.test.FieldMapInterfaceClassNotUsed;
import com.bobman159.rundml.core.mockclasses.test.FieldMapInterfaceSomeColumnsDefined;
import com.bobman159.rundml.core.mockclasses.test.FieldMapNoInterface;
import com.bobman159.rundml.core.mockclasses.test.FieldMapNoTableRowFieldClassException;
import com.bobman159.rundml.core.model.impl.CoreModelFactory;
import com.bobman159.rundml.core.model.mapping.FieldMap;
import com.bobman159.rundml.core.model.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.model.mapping.IFieldMapDefinition;

class FieldMappingTests {

	/* [0] = column name, [1] = class field name */
	private String[][] noInterfaceDefinition = {{"field1","field1"},
												{"field2","field2"},
												{"field3","field3"}};
	
	/* [0] = column name, [1] = class field name */
	private String[][] someColsInterfaceDefinition = {
			{"tableCol1","tableCol1"},
			{"tableCol2","field2"},
			{"tableCol3","tableCol3"},			
			{"tableCol4","field4"}};
	
	/* [0] = column name, [1] = class field name */

	private String[][] allColsInterfaceDefinition = {
					{"tableCol1", "classField1"},
					{"tableCol2", "classField2"},
					{"tableCol3", "classField3"},
					{"tableCol4","classField4"},
					{"tableCol5", "classField5"}};
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Not needed at this time
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//Not needed at this time
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindFieldMap() {
		
		FieldMap fieldMap = FieldMap.findFieldMap(FieldMapInterfaceClassNotUsed.class);
		assertNull(fieldMap);
		
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapNoInterface.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap fieldMapTest = FieldMap.findFieldMap(FieldMapNoInterface.class);
		assertNotNull(fieldMapTest);
	
	}

	@Test
	void testCreateFieldMap() {
		
		final String NOFIELD_ERROR = "No Field named fieldOne found in class com.bobman159.rundml.core.mockclasses.test.FieldMapNoTableRowFieldClassException";
		FieldMap fieldMap = null;
		try {
			fieldMap = CoreModelFactory.getInstance().createFieldMap(FieldMapNoInterface.class);
			assertNotNull(fieldMap);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		
		/* NoTableRowClassFieldException - Test */
		try {
			fieldMap = CoreModelFactory.getInstance().createFieldMap(FieldMapNoTableRowFieldClassException.class);
		} catch (NoTableRowClassFieldException e) {
			Assertions.assertNotNull(e);
			Assertions.assertEquals(NOFIELD_ERROR,e.getMessage());
			e.printStackTrace();
		}
		assertNotNull(fieldMap);
		
	}
	
	@Test 
	void testFieldMapNoInterface() {

		
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapNoInterface.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap fieldMap = FieldMap.findFieldMap(FieldMapNoInterface.class);
		assertNotNull(fieldMap);
		
		FieldMapDefinitionList defList = fieldMap.getFieldDefinitions();
		assertNotNull(defList);
		Stream<IFieldMapDefinition> countStream = defList.getFieldDefinitions();		
//		countStream.forEach(fieldDef -> {
//			System.out.println("fieldDef.column: " + fieldDef.getColumnName());
//			System.out.println("fieldDef.classField: " + fieldDef.getClassFieldName());
//		});
		
		
		Assertions.assertEquals(countStream.count(),noInterfaceDefinition.length);
	
		Stream<IFieldMapDefinition> defStream = defList.getFieldDefinitions();
		validateFieldDefinitions(defStream,noInterfaceDefinition);

	}
	
	@Test 
	void testFieldMapSomeColsDefinedInterface() {

		
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapInterfaceSomeColumnsDefined.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap fieldMap = FieldMap.findFieldMap(FieldMapInterfaceSomeColumnsDefined.class);
		assertNotNull(fieldMap);
		
		FieldMapDefinitionList defList = fieldMap.getFieldDefinitions();
		assertNotNull(defList);
		Stream<IFieldMapDefinition> countStream = defList.getFieldDefinitions();		
		Assertions.assertEquals(countStream.count(),someColsInterfaceDefinition.length);
	
		Stream<IFieldMapDefinition> defStream = defList.getFieldDefinitions();
		validateFieldDefinitions(defStream,someColsInterfaceDefinition);

	}
	
	@Test 
	void testFieldMapAllColsDefinedInterface() {

		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapInterfaceAllColumnsDefined.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap fieldMap = FieldMap.findFieldMap(FieldMapInterfaceAllColumnsDefined.class);
		assertNotNull(fieldMap);
		
		FieldMapDefinitionList defList = fieldMap.getFieldDefinitions();
		assertNotNull(defList);
		Stream<IFieldMapDefinition> countStream = defList.getFieldDefinitions();		
		Assertions.assertEquals(countStream.count(),allColsInterfaceDefinition.length);
	
		Stream<IFieldMapDefinition> defStream = defList.getFieldDefinitions();
		validateFieldDefinitions(defStream,allColsInterfaceDefinition);

	}
	
	@Test 
	void testFindMapDefinitionByColumnName() {

		/* No FieldMap Interface Implemented */
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapNoInterface.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap noneFieldMap = FieldMap.findFieldMap(FieldMapNoInterface.class);		
		FieldMapDefinitionList noneDefList = noneFieldMap.getFieldDefinitions();
		assertNotNull(noneDefList);
		
		IFieldMapDefinition fieldDefNone = noneDefList.findMapDefinitionByColumn("field1");
		Assertions.assertEquals("field1", fieldDefNone.getColumnName());
		Assertions.assertEquals("field1", fieldDefNone.getClassFieldName());

		IFieldMapDefinition fieldDefNone2 = noneDefList.findMapDefinitionByColumn("field2");
		Assertions.assertEquals("field2", fieldDefNone2.getColumnName());
		Assertions.assertEquals("field2", fieldDefNone2.getClassFieldName());
		
		IFieldMapDefinition fieldDefNone3 = noneDefList.findMapDefinitionByColumn("field4");
		Assertions.assertNull(fieldDefNone3);

		/* Partial FieldMap Interface (Some Columns Defined) */
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapInterfaceSomeColumnsDefined.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap fieldMapSome = FieldMap.findFieldMap(FieldMapInterfaceSomeColumnsDefined.class);		
		FieldMapDefinitionList defListSome = fieldMapSome.getFieldDefinitions();
		assertNotNull(defListSome);
		
		IFieldMapDefinition fieldDefSome = defListSome.findMapDefinitionByColumn("tableCol1");
		Assertions.assertEquals("tableCol1", fieldDefSome.getColumnName());
		Assertions.assertEquals("tableCol1", fieldDefSome.getClassFieldName());

		IFieldMapDefinition fieldDefSome2 = defListSome.findMapDefinitionByColumn("field5");
		Assertions.assertNull(fieldDefSome2);
		
		IFieldMapDefinition fieldDefSome3 = defListSome.findMapDefinitionByColumn("tableCol4");
		Assertions.assertEquals("tableCol4", fieldDefSome3.getColumnName());
		Assertions.assertEquals("field4", fieldDefSome3.getClassFieldName());
		
		/* Partial FieldMap Interface (All Columns Defined) */
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapInterfaceSomeColumnsDefined.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap fieldMapAll = FieldMap.findFieldMap(FieldMapInterfaceSomeColumnsDefined.class);		
		FieldMapDefinitionList defListAll = fieldMapAll.getFieldDefinitions();
		assertNotNull(defListAll);
		
		IFieldMapDefinition fieldDefAll = defListAll.findMapDefinitionByColumn("tableCol1");
		Assertions.assertEquals("tableCol1", fieldDefAll.getColumnName());
		Assertions.assertEquals("tableCol1", fieldDefAll.getClassFieldName());

		IFieldMapDefinition fieldDefAll2 = defListAll.findMapDefinitionByColumn("field5");
		Assertions.assertNull(fieldDefAll2);
		
		IFieldMapDefinition fieldDefAll3 = defListAll.findMapDefinitionByColumn("tableCol4");
		Assertions.assertEquals("tableCol4", fieldDefAll3.getColumnName());
		Assertions.assertEquals("field4", fieldDefAll3.getClassFieldName());
		
	}
	
	@Test 
	void testFindMapDefinitionByFieldName() {

		/* No FieldMap Interface Implemented */
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapNoInterface.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap noneFieldMap = FieldMap.findFieldMap(FieldMapNoInterface.class);		
		FieldMapDefinitionList noneDefList = noneFieldMap.getFieldDefinitions();
		assertNotNull(noneDefList);
		
		IFieldMapDefinition fieldDefNone = noneDefList.findMapDefinitionByField("field1");
		Assertions.assertEquals("field1", fieldDefNone.getColumnName());
		Assertions.assertEquals("field1", fieldDefNone.getClassFieldName());

		IFieldMapDefinition fieldDefNone2 = noneDefList.findMapDefinitionByField("field2");
		Assertions.assertEquals("field2", fieldDefNone2.getColumnName());
		Assertions.assertEquals("field2", fieldDefNone2.getClassFieldName());
		
		IFieldMapDefinition fieldDefNone3 = noneDefList.findMapDefinitionByField("field4");
		Assertions.assertNull(fieldDefNone3);

		/* Partial FieldMap Interface (Some Columns Defined) */
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapInterfaceSomeColumnsDefined.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap fieldMapSome = FieldMap.findFieldMap(FieldMapInterfaceSomeColumnsDefined.class);		
		FieldMapDefinitionList defListSome = fieldMapSome.getFieldDefinitions();
		assertNotNull(defListSome);
		
		IFieldMapDefinition fieldDefSome = defListSome.findMapDefinitionByField("tableCol1");
		Assertions.assertEquals("tableCol1", fieldDefSome.getColumnName());
		Assertions.assertEquals("tableCol1", fieldDefSome.getClassFieldName());

		IFieldMapDefinition fieldDefSome2 = defListSome.findMapDefinitionByField("field5");
		Assertions.assertNull(fieldDefSome2);
		
		IFieldMapDefinition fieldDefSome3 = defListSome.findMapDefinitionByField("field4");
		Assertions.assertEquals("tableCol4", fieldDefSome3.getColumnName());
		Assertions.assertEquals("field4", fieldDefSome3.getClassFieldName());
		
		/* Partial FieldMap Interface (All Columns Defined) */
		try {
			CoreModelFactory.getInstance().createFieldMap(FieldMapInterfaceSomeColumnsDefined.class);
		} catch (NoTableRowClassFieldException e) {
			e.printStackTrace();
		}
		FieldMap fieldMapAll = FieldMap.findFieldMap(FieldMapInterfaceSomeColumnsDefined.class);		
		FieldMapDefinitionList defListAll = fieldMapAll.getFieldDefinitions();
		assertNotNull(defListAll);
		
		IFieldMapDefinition fieldDefAll = defListAll.findMapDefinitionByField("tableCol1");
		Assertions.assertEquals("tableCol1", fieldDefAll.getColumnName());
		Assertions.assertEquals("tableCol1", fieldDefAll.getClassFieldName());

		IFieldMapDefinition fieldDefAll2 = defListAll.findMapDefinitionByField("field5");
		Assertions.assertNull(fieldDefAll2);
		
		IFieldMapDefinition fieldDefAll3 = defListAll.findMapDefinitionByField("field4");
		Assertions.assertEquals("tableCol4", fieldDefAll3.getColumnName());
		Assertions.assertEquals("field4", fieldDefAll3.getClassFieldName());
		
	}
	
	/*
	 * Verify that each entry in actualStream has a matching column name and 
	 * field name against the expected column name and field name array.
	 */
	private void validateFieldDefinitions(Stream<IFieldMapDefinition> actualStream,String[][] expected) {
		int row = 0;

		Iterator<IFieldMapDefinition> streamIterator = actualStream.iterator();
		while(streamIterator.hasNext()) {
			IFieldMapDefinition fieldDef = streamIterator.next();
			Assertions.assertEquals(expected[row][0],fieldDef.getColumnName());
			Assertions.assertEquals(expected[row][1],fieldDef.getClassFieldName());
			row++;
		}

	}

}
