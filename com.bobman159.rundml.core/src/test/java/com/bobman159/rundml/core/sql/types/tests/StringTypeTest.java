package com.bobman159.rundml.core.sql.types.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.sql.BaseSQLSerializer;
import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.sql.types.impl.StringType;

class StringTypeTest {

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
	void stringTypeTest() {
		
		StringType strType = new StringType("AbcDef");
		Assertions.assertEquals(SQLType.STRING, strType.getType());
		Assertions.assertEquals("'AbcDef'", new BaseSQLSerializer().serialize(strType));
		
	}

}
