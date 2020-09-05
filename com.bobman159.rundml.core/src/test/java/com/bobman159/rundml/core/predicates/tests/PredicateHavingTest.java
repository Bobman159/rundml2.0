package com.bobman159.rundml.core.predicates.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;
import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;

class PredicateHavingTest {

	private static final String DFLTINTEGER = "dfltInteger";
	private static final String NOTNULLVARCHAR = "notNullVarchar";
	private final TestBaseSQLSerializer serializer = new TestBaseSQLSerializer();

	@BeforeAll
	static void setUpBeforeClass() {
		//No setup needed
	}

	@AfterAll
	static void tearDownAfterClass() {
		//Not needed at this time
	}

	@BeforeEach
	void setUp() {
		//Not needed at this time
	}

	@AfterEach
	void tearDown() {
		//Not needed at this time
	}

	@Test
	void testHavingPreidcates() {

		String stmtText = serializer.serializePredicatesList(
				PredicateBuilder.having(SQLTypeFactory.getInstance()
								.column(DFLTINTEGER)).isGreater(100000)
								.build().getPredicates());
		Assertions.assertEquals("HAVING dfltInteger > 100000",stmtText);

		String stmtText2 = serializer.serializePredicatesList(
				PredicateBuilder.having(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				 				.isGreater("0123456789")
				 				.build().getPredicates());
		Assertions.assertEquals("HAVING notNullVarchar > '0123456789'",stmtText2);

		String stmtText3 = serializer.serializePredicatesList(
				PredicateBuilder.having(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				 				.isGreaterOrEqual("0123456789")
				 				.or(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR)).isEqual("223456789")
				 				.and(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR)).isLess("1123456789")
				 				.build().getPredicates());
		Assertions.assertEquals("HAVING notNullVarchar >= '0123456789' " + 
				 			"OR notNullVarchar = '223456789' " + 
				 			"AND notNullVarchar < '1123456789'",stmtText3);

		String stmtText4 = serializer.serializePredicatesList(PredicateBuilder.having("Abcdef").isEqual("Abcdef2")
				 .or("Hijklmnop").isGreater("Hijklmno")
				 .build().getPredicates());
		Assertions.assertEquals("HAVING 'Abcdef' = 'Abcdef2' " + 
				 			"OR 'Hijklmnop' > 'Hijklmno'",stmtText4);

		String stmtText5 = serializer.serializePredicatesList(PredicateBuilder.having(20).isEqual(20)
				 .and(20).isGreater(10).and(10).isLess(30)
				 .build().getPredicates());
		Assertions.assertEquals("HAVING 20 = 20 " + 
				 			"AND 20 > 10 AND 10 < 30",stmtText5);
		
		String stmtText6 = serializer.serializePredicatesList(PredicateBuilder.having(20).isNot(SQLTypeFactory.getInstance().constant(20))
				 .and(20).isNotEqual(SQLTypeFactory.getInstance().constant(10))
				 .and(10).isLess(30)
				 .build().getPredicates());
		Assertions.assertEquals("HAVING 20 ! 20 " + 
				 			"AND 20 <> 10 AND 10 < 30",stmtText6);

	}

}
