package net.bobs.own.db.rundml.sql.builders.test;

import static org.junit.Assert.fail;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.bobs.own.db.rundml.sql.builders.exprs.Expression;
import net.bobs.own.db.rundml.sql.builders.stmts.SelectStatement;

class SelectBuildTest {

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
	void selectNumberTest() {

		Connection conn = null;
		SelectStatement.select(conn)
//						.selectExpr(SQLEx.number(10))
//						.selectExpr(SQLEx.numberExpr(10).add(Literal.numberExpr(20)))
						.from("ezmenu","ingredients")
						.fetch();
		
		fail("Not yet implemented");
	}

}
