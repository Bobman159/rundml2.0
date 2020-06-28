package com.bobman159.rundml.core.predicates.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.factory.RunDMLTestFactory;
import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;

class PredicateHavingTest {

	private static final String DFLTINTEGER = "dfltInteger";
	private static final String NOTNULLVARCHAR = "notNullVarchar";
	private final RunDMLTestFactory testFactory = RunDMLTestFactory.getInstance();

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

		String stmtText = PredicateBuilder.having(testFactory.column(DFLTINTEGER)).isGreater(100000)
								 .build().toString();
		Assertions.assertEquals("HAVING dfltInteger > 100000",stmtText);

		String stmtText2 = PredicateBuilder.having(testFactory.column(NOTNULLVARCHAR))
				 .isGreater("0123456789")
				 .build().toString();
		Assertions.assertEquals("HAVING notNullVarchar > '0123456789'",stmtText2);

		String stmtText3 = PredicateBuilder.having(testFactory.column(NOTNULLVARCHAR))
				 .isGreaterOrEqual("0123456789")
				 .or(testFactory.column(NOTNULLVARCHAR)).isEqual("223456789")
				 .and(testFactory.column(NOTNULLVARCHAR)).isLess("1123456789")
				 .build().toString();
		Assertions.assertEquals("HAVING notNullVarchar >= '0123456789' " + 
				 			"OR notNullVarchar = '223456789' " + 
				 			"AND notNullVarchar < '1123456789'",stmtText3);

		String stmtText4 = PredicateBuilder.having("Abcdef").isEqual("Abcdef2")
				 .or("Hijklmnop").isGreater("Hijklmno")
				 .build().toString();
		Assertions.assertEquals("HAVING 'Abcdef' = 'Abcdef2' " + 
				 			"OR 'Hijklmnop' > 'Hijklmno'",stmtText4);

		String stmtText5 = PredicateBuilder.having(20).isEqual(20)
				 .and(20).isGreater(10).and(10).isLess(30)
				 .build().toString();
		Assertions.assertEquals("HAVING 20 = 20 " + 
				 			"AND 20 > 10 AND 10 < 30",stmtText5);
		
		String stmtText6 = PredicateBuilder.having(20).isNot(testFactory.constant(20))
				 .and(20).isNotEqual(testFactory.constant(10))
				 .and(10).isLess(30)
				 .build().toString();
		Assertions.assertEquals("HAVING 20 ! 20 " + 
				 			"AND 20 <> 10 AND 10 < 30",stmtText6);

	}

}
