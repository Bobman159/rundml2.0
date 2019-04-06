package com.bobman159.rundml.core.predicates.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.tabledef.TableDefinition;

class PredicateHavingTest {

	private static TableDefinition tbDef;
	private static final String DFLTINTEGER = "dfltInteger";
	private static final String NOTNULLVARCHAR = "notNullVarchar";
	private static final String RUNDML_SCHEMA = "rundml";
	private static final String RUNDML_TABLE = "typetest";

	@BeforeAll
	static void setUpBeforeClass() {
		tbDef = new TableDefinition(RUNDML_SCHEMA,RUNDML_TABLE);
		tbDef.addColumn(DFLTINTEGER, Types.INTEGER);
		tbDef.addColumn(NOTNULLVARCHAR, Types.VARCHAR);
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

		String stmtText = Predicate.having(tbDef.column(DFLTINTEGER)).isGreater(100000)
								 .build().serialize();
		Assertions.assertEquals("HAVING DFLTINTEGER > 100000",stmtText);

		String stmtText2 = Predicate.having(tbDef.column(NOTNULLVARCHAR))
				 .isGreater("0123456789")
				 .build().serialize();
		Assertions.assertEquals("HAVING NOTNULLVARCHAR > '0123456789'",stmtText2);

		String stmtText3 = Predicate.having(tbDef.column(NOTNULLVARCHAR))
				 .isGreaterOrEqual("0123456789")
				 .or(tbDef.column(NOTNULLVARCHAR)).isEqual("223456789")
				 .and(tbDef.column(NOTNULLVARCHAR)).isLess("1123456789")
				 .build().serialize();
		Assertions.assertEquals("HAVING NOTNULLVARCHAR >= '0123456789' " + 
				 			"OR NOTNULLVARCHAR = '223456789' " + 
				 			"AND NOTNULLVARCHAR < '1123456789'",stmtText3);

		String stmtText4 = Predicate.having("Abcdef").isEqual("Abcdef2")
				 .or("Hijklmnop").isGreater("Hijklmno")
				 .build().serialize();
		Assertions.assertEquals("HAVING 'Abcdef' = 'Abcdef2' " + 
				 			"OR 'Hijklmnop' > 'Hijklmno'",stmtText4);

		String stmtText5 = Predicate.having(20).isEqual(20)
				 .and(20).isGreater(10).and(10).isLess(30)
				 .build().serialize();
		Assertions.assertEquals("HAVING 20 = 20 " + 
				 			"AND 20 > 10 AND 10 < 30",stmtText5);

	}

}
