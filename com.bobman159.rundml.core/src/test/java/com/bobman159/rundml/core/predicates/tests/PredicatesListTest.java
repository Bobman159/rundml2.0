package com.bobman159.rundml.core.predicates.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.predicates.impl.PredOperand;
import com.bobman159.rundml.core.predicates.impl.PredicateClause;
import com.bobman159.rundml.core.predicates.impl.PredicatesList;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.sql.conditions.SQLCondition;
import com.bobman159.rundml.core.sql.types.SQLType;

class PredicatesListTest {

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
	void testAddPredicate() {
		
		PredicatesList predList = new PredicatesList();
		
		IPredicate whereClause = PredicateClause.createPredicate(PredOperand.WHERE, 
					SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().constant(10)),
													Op.EQ,SQLTypeFactory.getInstance().constant(10)));
		predList.addPredicate(whereClause);
		IPredicate orClause = PredicateClause.createPredicate(PredOperand.OR, 
				SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().constant("Abc")), Op.LTE, 
												SQLTypeFactory.getInstance().constant("Abc")));
		predList.addPredicate(orClause);
		
		IPredicate andClause = PredicateClause.createPredicate(PredOperand.AND,
				SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().column("COL01")), 
												Op.NOT, SQLTypeFactory.getInstance().column("COL02")));
		predList.addPredicate(andClause);
		
		assertEquals(3, predList.size());
		
		
	}
	
	@Test
	void testGetFirstPredicate() {
		
		PredicatesList predList = new PredicatesList();
		
		IPredicate whereClause = PredicateClause.createPredicate(PredOperand.WHERE, 
					SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().constant(10)),
													Op.EQ,SQLTypeFactory.getInstance().constant(10)));
		predList.addPredicate(whereClause);
		
		IPredicate orClause = PredicateClause.createPredicate(PredOperand.OR, 
				SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().constant("Abc")), Op.LTE, 
												SQLTypeFactory.getInstance().constant("Abc")));
		predList.addPredicate(orClause);
		
		IPredicate firstPred = predList.getFirstPredicate();
		assertEquals(SQLType.PREDICATE,firstPred.getType());
		assertEquals(PredOperand.WHERE, firstPred.getPredicateOperation());
		assertNotNull(firstPred.getCondition());
		assertEquals(SQLType.NUMERIC,firstPred.getCondition().getLeftCondition().getType());
		assertEquals(Op.EQ,firstPred.getCondition().getOperator());
		
	}
	
	@Test
	void testGetLastPredicate() {
		
		PredicatesList predList = new PredicatesList();
		
		IPredicate whereClause = PredicateClause.createPredicate(PredOperand.WHERE, 
					SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().constant(10)),
													Op.EQ,SQLTypeFactory.getInstance().constant(10)));

		IPredicate andClause = PredicateClause.createPredicate(PredOperand.AND,
				SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().column("COL01")), 
												Op.NOT, SQLTypeFactory.getInstance().column("COL02")));
		predList.addPredicate(andClause);
		
		predList.addPredicate(whereClause);
		IPredicate orClause = PredicateClause.createPredicate(PredOperand.OR, 
				SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().constant("Abc")), Op.LTE, 
												SQLTypeFactory.getInstance().constant("Abc")));
		predList.addPredicate(orClause);
		
		IPredicate lastPred = predList.getLastPredicate();
		assertEquals(SQLType.PREDICATE,lastPred.getType());
		assertEquals(PredOperand.OR, lastPred.getPredicateOperation());
		assertNotNull(lastPred.getCondition());
		assertEquals(SQLType.STRING,lastPred.getCondition().getLeftCondition().getType());
		assertEquals(Op.LTE,lastPred.getCondition().getOperator());

	}
	
	@Test
	void testToString() {
		
		PredicatesList predList = new PredicatesList();
		
		IPredicate whereClause = PredicateClause.createPredicate(PredOperand.WHERE, 
					SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().constant(10)),
													Op.EQ,SQLTypeFactory.getInstance().constant(10)));
		predList.addPredicate(whereClause);
		
		IPredicate andClause = PredicateClause.createPredicate(PredOperand.AND,
				SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().column("COL01")), 
												Op.NOT, SQLTypeFactory.getInstance().column("COL02")));
		predList.addPredicate(andClause);
		
		IPredicate orClause = PredicateClause.createPredicate(PredOperand.OR, 
				SQLCondition.createSQLCondition(SQLCondition.createSQLCondition(SQLTypeFactory.getInstance().constant("Abc")), Op.LTE, 
												SQLTypeFactory.getInstance().constant("Abc")));
		predList.addPredicate(orClause);
		
		String sql = new TestBaseSQLSerializer().serialize(predList);
		assertEquals("WHERE 10 = 10 AND COL01 ! COL02 OR 'Abc' <= 'Abc'", sql);
	}

}
