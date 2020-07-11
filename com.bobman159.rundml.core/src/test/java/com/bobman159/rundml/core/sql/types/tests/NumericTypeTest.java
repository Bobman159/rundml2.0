package com.bobman159.rundml.core.sql.types.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.sql.BaseSQLSerializer;
import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.sql.types.impl.NumericType;

class NumericTypeTest {

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
	void numericTypeTest() {
		
		//byte 1 byte -128 to 127.
		NumericType negByteType = new NumericType(-128);
		Assertions.assertEquals(SQLType.NUMERIC, negByteType.getType());
		Assertions.assertEquals("-128", new BaseSQLSerializer().serialize(negByteType));
		
		NumericType posByteType = new NumericType(127);
		Assertions.assertEquals(SQLType.NUMERIC, posByteType.getType());
		Assertions.assertEquals("127", new BaseSQLSerializer().serialize(posByteType));
		
		//short 2 bytes -32,768 to 32,767.
		
		NumericType negShortType = new NumericType(-32768);
		Assertions.assertEquals(SQLType.NUMERIC, negShortType.getType());
		Assertions.assertEquals("-32768", new BaseSQLSerializer().serialize(negShortType));
		
		NumericType posShortType = new NumericType(32767);
		Assertions.assertEquals(SQLType.NUMERIC, posShortType.getType());
		Assertions.assertEquals("32767", new BaseSQLSerializer().serialize(posShortType));
		
		//int 4 bytes -2,147,483,648 to 2,147,483,647.
		NumericType negIntType = new NumericType(-2147483648);
		Assertions.assertEquals(SQLType.NUMERIC, negIntType.getType());
		Assertions.assertEquals("-2147483648", new BaseSQLSerializer().serialize(negIntType));
		
		NumericType posIntType = new NumericType(2147483647);
		Assertions.assertEquals(SQLType.NUMERIC, posIntType.getType());
		Assertions.assertEquals("2147483647", new BaseSQLSerializer().serialize(posIntType));
		
		//long 8 bytes -9,223,372,036,854,775,808 to 9,223,372,036,854,775,80.
		NumericType negLongType = new NumericType(-9223372036854775808l);
		Assertions.assertEquals(SQLType.NUMERIC, negLongType.getType());
		Assertions.assertEquals("-9223372036854775808", new BaseSQLSerializer().serialize(negLongType));
		
		NumericType posLongType = new NumericType(9223372036854775807l);
		Assertions.assertEquals(SQLType.NUMERIC, posLongType.getType());
		Assertions.assertEquals("9223372036854775807", new BaseSQLSerializer().serialize(posLongType));
		
		//float 4 bytes 7 decimal digits.
		NumericType negFloatType = new NumericType(1.40129846432481707e-45f);
		Assertions.assertEquals(SQLType.NUMERIC, negFloatType.getType());
		Assertions.assertEquals("1.4E-45", new BaseSQLSerializer().serialize(negFloatType));
		
		NumericType posFloatType = new NumericType(3.40282346638528860e+38f);
		Assertions.assertEquals(SQLType.NUMERIC, posFloatType.getType());
		Assertions.assertEquals("3.4028235E38", new BaseSQLSerializer().serialize(posFloatType));
		
		//double 8 bytes 16 decimal digits.
		NumericType negDoubleType = new NumericType(4.94065645841246544e-324d);
		Assertions.assertEquals(SQLType.NUMERIC, negDoubleType.getType());
		Assertions.assertEquals("4.9E-324", new BaseSQLSerializer().serialize(negDoubleType));
		
		NumericType posDoubleType = new NumericType(1.79769313486231570e+308d);
		Assertions.assertEquals(SQLType.NUMERIC, posDoubleType.getType());
		Assertions.assertEquals("1.7976931348623157E308", new BaseSQLSerializer().serialize(posDoubleType));

	}

}
