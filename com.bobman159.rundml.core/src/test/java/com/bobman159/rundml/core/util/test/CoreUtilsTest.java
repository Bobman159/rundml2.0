package com.bobman159.rundml.core.util.test;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.mockclasses.test.FieldMapInterfaceAllColumnsDefined;
import com.bobman159.rundml.core.mockclasses.test.FieldMapNoInterface;
import com.bobman159.rundml.core.mockclasses.test.FieldMapNoTableRowFieldClassException;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.util.CoreUtils;

class CoreUtilsTest {

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
		
		Field[] fields = CoreUtils.getClassDeclaredFields(FieldMapInterfaceAllColumnsDefined.class);		
		Assertions.assertEquals(expectedFields.length, fields.length);
		for(int ix = 0; ix < fields.length;ix++) {
			Assertions.assertEquals(expectedFields[ix], fields[ix].getName());
		}
		
	}

	@Test
	void testGetClassField() {
		Field actualField = CoreUtils.getClassField(FieldMapInterfaceAllColumnsDefined.class, "classField4");
		Assertions.assertNotNull(actualField);
		Assertions.assertEquals("classField4", actualField.getName());
	}

	@Test
	void testIsAnIFieldMap() {
		
		boolean isFieldMap = CoreUtils.isAnIFieldMap(FieldMapInterfaceAllColumnsDefined.class);
		Assertions.assertTrue(isFieldMap);
		
		boolean isFieldMap2 = CoreUtils.isAnIFieldMap(FieldMapNoInterface.class);
		Assertions.assertFalse(isFieldMap2);
	}

	@Test
	void testCreateColumnsFromClass() {
			
		ISQLType[] expectedColumns = new ISQLType[] {new Column("tableCol1"),
															new Column("tableCol2"),
															new Column("tableCol3"),
															new Column("tableCol4"),
															new Column("tableCol5")
														  };
		ISQLType[] expectedColumns2 = new ISQLType[] {new Column("field1"),
				new Column("field2"),
				new Column("field3")
			  };
		
		ISQLType[] actualColumns = null;
		try {
			actualColumns = CoreUtils.createColumnsFromClass(FieldMapInterfaceAllColumnsDefined.class);
		} catch (RunDMLException e) {
			e.printStackTrace();
		}
		Assertions.assertNotNull(actualColumns);
		Assertions.assertEquals(5, actualColumns.length);
		int ix = 0;
		for (ISQLType actualExpr: actualColumns) {
			if (actualExpr instanceof Column == false) {
				fail("actualExpr index: " + ix + "is NOT a Column expression");
			} else {
				Column expectedColumn = (Column) expectedColumns[ix];
				Column actualColumn = (Column) actualColumns[ix];
				Assertions.assertEquals(expectedColumn.getColumnName(), actualColumn.getColumnName());
			}
			ix++;
		}
		
		ISQLType[] actualColumns2 = null;
		try {
			actualColumns2 = CoreUtils.createColumnsFromClass(FieldMapNoInterface.class);
		} catch (RunDMLException e) {
			e.printStackTrace();
		}
		Assertions.assertNotNull(actualColumns2);
		Assertions.assertEquals(3, actualColumns2.length);
		int ix2 = 0;
		for (ISQLType actualExpr: actualColumns2) {
			if (actualExpr instanceof Column == false) {
				fail("actualExpr index: " + ix2 + "is NOT a Column expression");
			} else {
				Column expectedColumn = (Column) expectedColumns2[ix2];
				Column actualColumn = (Column) actualColumns2[ix2];
				Assertions.assertEquals(expectedColumn.getColumnName(), actualColumn.getColumnName());
			}
			ix2++;
		}
		
		final String NOFIELD_ERROR = "com.bobman159.rundml.core.model.mapping.exceptions.NoTableRowClassFieldException: No Field named fieldOne found in class com.bobman159.rundml.core.mockclasses.test.FieldMapNoTableRowFieldClassException";
		try {
			ISQLType[] actualColumns3 = CoreUtils.createColumnsFromClass(FieldMapNoTableRowFieldClassException.class);
			Assertions.assertEquals(0,actualColumns3.length);
		} catch (RunDMLException e) {
			Assertions.assertNotNull(e);
			Assertions.assertEquals(RunDMLException.SQL_MODEL_BUILD, e.getExecutionPhase());
			Assertions.assertEquals(NOFIELD_ERROR, e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	void testCreateColumnsFromStrings() {
		String[] expectedColumns = new String[] {"col01","col02","col03","col04","col05","col06"};
		ISQLType[] actualColumns = CoreUtils.createColumnsFromStrings(expectedColumns);
		int ix = 0;
		for(ISQLType actualExpr : actualColumns) {
			if (actualExpr instanceof Column == false) {
				fail("actualExpr index: " + ix + "is NOT a Column expression");
			} else {
				Column actualColumn = (Column) actualColumns[ix];
				Assertions.assertEquals(expectedColumns[ix], actualColumn.getColumnName());
			}
			ix++;
		}	
	}

}
