package net.bobs.own.db.rundml.sql.builders.test;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.bobs.own.db.rundml.sql.builders.exprs.Expression;
import net.bobs.own.db.rundml.sql.builders.predicates.Predicate;
import net.bobs.own.db.rundml.sql.builders.stmts.SelectStatement;

class SelectStatementTest {

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
		
		Predicate pred = Predicate.where("abc").isEqual("abc")
								  .and(10).isEqual(10)
								  .build();
												 
		SelectStatement.select(conn)
						.selectExpr(Expression.number(10))
						.selectExpr(Expression.number(10).add(Expression.number(20)))
						.from("ezmenu","ingredients")	
						.where(pred)
						.fetch();

	}

}
