package com.bobman159.rundml.core.util.test;
import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.mockclasses.test.FieldMapInterfaceAllColumnsDefined;
import com.bobman159.rundml.core.mockclasses.test.FieldMapNoInterface;
import com.bobman159.rundml.core.model.mapping.FieldMappingUtils;

class UtilsClassesTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetClassDeclaredFields() {
		String[] expectedFields = new String[] {"classField1","classField2","classField3",
												"classField4","classField5"};
		
		Field[] fields = FieldMappingUtils.getClassDeclaredFields(FieldMapInterfaceAllColumnsDefined.class);		
		Assertions.assertEquals(expectedFields.length, fields.length);
		for(int ix = 0; ix < fields.length;ix++) {
			Assertions.assertEquals(expectedFields[ix], fields[ix].getName());
		}
		
	}

	@Test
	void testGetClassField() {
		Field actualField = FieldMappingUtils.getClassField(FieldMapInterfaceAllColumnsDefined.class, "classField4");
		Assertions.assertNotNull(actualField);
		Assertions.assertEquals("classField4", actualField.getName());
	}

	@Test
	void testIsAnIFieldMap() {
		
		boolean isFieldMap = FieldMappingUtils.isAnIFieldMap(FieldMapInterfaceAllColumnsDefined.class);
		Assertions.assertTrue(isFieldMap);
		
		boolean isFieldMap2 = FieldMappingUtils.isAnIFieldMap(FieldMapNoInterface.class);
		Assertions.assertFalse(isFieldMap2);
	}

}
