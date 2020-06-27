package com.bobman159.rundml.core.predicates.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;
import com.bobman159.rundml.core.sql.types.impl.Column;

class PredicateWhereTest {

	private static final String ANDTENPLUS10LETEN = " AND 10 + 10 <= 10";
	private static final String ORTENPLUS10LETEN = " OR 10 + 10 <= 10";
	private static final String WHERECONCATABCDEF = "WHERE 'abc' || 'def' <= 'abcdef'";
	private static final String WHERECONCATABCDEF_NOT = "WHERE 'abc' || 'def' ! 'abcdef'";
	private static final String WHERECONCATABCDEF_LT = "WHERE 'abc' || 'def' < 'abcdef'";
	private static final String WHERECONCATABCDEF_GTE = "WHERE 'abc' || 'def' >= 'abcdef'";
	private static final String WHERETENPLUS10LETEN = "WHERE 10 + 10 <= 10";
	private static final String WHERECOLTESTLETEN = "WHERE coltest <= 10";
	private static final String ABCDEF = "abcdef";
	private static final String COLTEST = "coltest";
		
	@BeforeAll
	static void setUpBeforeClass() {
		//no set up needed at this time
	}

	@AfterAll
	static void tearDownAfterClass() {
		//no tear down needed at this time
	}

	@BeforeEach
	void setUp() {
		//no set up needed at this time
	}

	@AfterEach
	void tearDown() {
		//no tear down needed at this time
	}
	
	@Test
	void testWherePredicates() {

		PredicateBuilder.where(10).isEqual(20).and("Abc").isEqual(10);
		String predResult = PredicateBuilder.where(10).isEqual(10).build().toString();
		Assertions.assertEquals("WHERE 10 = 10",predResult);
		
		String predResult2 = PredicateBuilder.where(Expression.constant(10))
									  .isLessOrEqual(10)
									  .build().toString();
		Assertions.assertEquals("WHERE 10 <= 10",predResult2);

		Column colTest = (Column) Expression.column(COLTEST);
		String predResult3 = PredicateBuilder.where(colTest).isLessOrEqual(10)
									  .build().toString();
		Assertions.assertEquals(WHERECOLTESTLETEN,predResult3);
		
		String predResult4 = PredicateBuilder.where(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .build().toString();
		Assertions.assertEquals("WHERE ? <= 10",predResult4);
		
		String predResult5 = PredicateBuilder.where(Expression.mathExpression(10).add(10))
									  .isLessOrEqual(10)
									  .build().toString();
		Assertions.assertEquals(WHERETENPLUS10LETEN,predResult5);
		
		String predResult6 = PredicateBuilder.where(Expression.stringExpression(
													Expression.constant("abc")).concat("def"))
				  					  .isLessOrEqual(ABCDEF)
				  					  .build().toString();
		Assertions.assertEquals("WHERE \'abc\' || \'def\' <= 'abcdef'",predResult6);
		
		String predResult7 = PredicateBuilder.where(Expression.stringExpression(
				Expression.constant("abc")).concat("def"))
						  .isLessOrEqual(Expression.constant(ABCDEF))
						  .build().toString();
		Assertions.assertEquals("WHERE \'abc\' || \'def\' <= 'abcdef'",predResult7);
		
		String predResult8 = PredicateBuilder.where(Expression.stringExpression(
				Expression.constant("abc")).concat("def"))
						  .isLessOrEqual(Expression.constant(ABCDEF))
						  .build().toString();
		Assertions.assertEquals("WHERE \'abc\' || \'def\' <= 'abcdef'",predResult8);
		
		String predResult9 = PredicateBuilder.where(Expression.mathExpression(Expression.constant(10))
				.add(Expression.constant(20)))
			  .isEqual(Expression.constant(30))
			  .build().toString();
		Assertions.assertEquals("WHERE 10 + 20 = 30",predResult9);
		
		String predResult10 = PredicateBuilder.where(Expression.mathExpression(Expression.parm(Types.INTEGER, 10))
				.add(Expression.parm(Types.SMALLINT, 20)))
			  .isGreater(Expression.parm(Types.BIGINT,30))
			  .build().toString();
		Assertions.assertEquals("WHERE ? + ? > ?",predResult10);
		
		String predResult11 = PredicateBuilder.where("Xyz")
			  .isGreater("Abc")
			  .build().toString();
		Assertions.assertEquals("WHERE 'Xyz' > 'Abc'",predResult11);
	
	}
	
	@Test
	void testAndPredicates() {
		
		String predResult = PredicateBuilder.where(10).isEqual(10)
									 .and(10).isEqual(10).build().toString();
		Assertions.assertEquals("WHERE 10 = 10 AND 10 = 10",predResult);
		
		String predResult2 = PredicateBuilder.where(Expression.constant(10))
									  .isLessOrEqual(10)
									  .and(Expression.constant(10))
									  .isLessOrEqual(10)
									  .build().toString();
		Assertions.assertEquals("WHERE 10 <= 10 AND 10 <= 10",predResult2);
		
		Column colTest = (Column) Expression.column(COLTEST);
		String predResult3 = PredicateBuilder.where(colTest).isLessOrEqual(10)
									  .and(colTest).isLessOrEqual(10)
									  .build().toString();
		Assertions.assertEquals(WHERECOLTESTLETEN +
							" AND coltest <= 10",predResult3);
		
		String predResult4 = PredicateBuilder.where(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .and(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .build().toString();
		Assertions.assertEquals("WHERE ? <= 10 AND ? <= 10",predResult4);
		
		String predResult5 = PredicateBuilder.where(Expression.mathExpression(10).add(10))
									  .isLessOrEqual(10)
									  .and(Expression.mathExpression(Expression.constant(10)).add(10))
									  .isLessOrEqual(10)
									  .build().toString();
		Assertions.assertEquals(WHERETENPLUS10LETEN +
							ANDTENPLUS10LETEN,predResult5);
		
		String predResult6 = PredicateBuilder.where(
				Expression.stringExpression(Expression.constant("abc")).concat("def"))
				  					  .isLessOrEqual(ABCDEF)
				  					  .and(Expression.stringExpression(Expression.constant("abc")).concat("def"))
				  					  .isEqual(ABCDEF)
				  					  .build().toString();
		Assertions.assertEquals(WHERECONCATABCDEF +
				  			" AND 'abc' || 'def' = 'abcdef'",predResult6);
		
		String predResult7 = PredicateBuilder.where(
				Expression.stringExpression(Expression.constant("abc")).concat("def"))
				  					  .isLess(ABCDEF)
				  					  .and(Expression.stringExpression(Expression.constant("abc")).concat("def"))
				  					  .isLess(Expression.column("col01"))
				  					  .build().toString();
		Assertions.assertEquals(WHERECONCATABCDEF_LT +
				  			" AND 'abc' || 'def' < col01",predResult7);
		
		String predResult8 = PredicateBuilder.where(
				Expression.stringExpression(Expression.constant("abc")).concat("def"))
				  					  .isLess(ABCDEF)
				  					  .and(Expression.stringExpression(Expression.constant("abc")).concat("def"))
				  					  .isLessOrEqual(Expression.stringExpression(Expression.column("col01")).concat("def"))
				  					  .build().toString();
		Assertions.assertEquals(WHERECONCATABCDEF_LT +
				  			" AND 'abc' || 'def' <= col01 || 'def'",predResult8);
	
	}
	
	@Test
	void testOrPredicates() {
		
		String predResult = PredicateBuilder.where(10).isEqual(10)
				 .or(10).isEqual(10).build().toString();
		Assertions.assertEquals("WHERE 10 = 10 OR 10 = 10",predResult);

		String predResult2 = PredicateBuilder.where(Expression.constant(10))
				  					  .isLessOrEqual(10)
				  					  .or(Expression.constant(10))
				  					  .isLessOrEqual(10)
				  					  .build().toString();
		Assertions.assertEquals("WHERE 10 <= 10 OR 10 <= 10",predResult2);

		Column colTest = (Column) Expression.column(COLTEST);
		String predResult3 = PredicateBuilder.where(colTest).isLessOrEqual(10)
						  			  .or(colTest).isLessOrEqual(10)
						  			  .build().toString();
		Assertions.assertEquals(WHERECOLTESTLETEN +
				" OR coltest <= 10",predResult3);

		String predResult4 = PredicateBuilder.where(Expression.parm(Types.BIGINT, 10))
						  			  .isLessOrEqual(10)
						  			  .or(Expression.parm(Types.BIGINT, 10))
						  			  .isLessOrEqual(10)
						  			  .build().toString();
		Assertions.assertEquals("WHERE ? <= 10 OR ? <= 10",predResult4);

		String predResult5 = PredicateBuilder.where(
				Expression.mathExpression(Expression.constant(10)).add(10))
						  .isLessOrEqual(10)
						  .or(Expression.mathExpression(Expression.constant(10)).add(10))
						  .isLessOrEqual(10)
						  .build().toString();
		Assertions.assertEquals(WHERETENPLUS10LETEN +
				ORTENPLUS10LETEN,predResult5);

		String predResult6 = PredicateBuilder
				.where(Expression.stringExpression(Expression.constant("abc")).concat("def"))
							  	 .isLessOrEqual(ABCDEF)
							  	 .or(Expression.stringExpression("abc").concat("def"))
							  	 .isLessOrEqual(ABCDEF)
							  	 .build().toString();
		Assertions.assertEquals(WHERECONCATABCDEF +
					" OR 'abc' || 'def' <= 'abcdef'",predResult6);
		
		String predResult7 = PredicateBuilder
				.where(Expression.stringExpression(Expression.constant("abc")).concat("def"))
							  	 .isLessOrEqual(ABCDEF)
							  	 .or(Expression.stringExpression("abc").concat("def"))
							  	 .isLessOrEqual(ABCDEF)
							  	 .build().toString();
		Assertions.assertEquals(WHERECONCATABCDEF +
					" OR 'abc' || 'def' <= 'abcdef'",predResult7);
		
		String predResult8 = PredicateBuilder
				.where(Expression.stringExpression(Expression.constant("abc")).concat("def"))
							  	 .isNot(ABCDEF)
							  	 .or(Expression.stringExpression("abc").concat("def"))
							  	 .isNotEqual(ABCDEF)
							  	 .build().toString();
		Assertions.assertEquals(WHERECONCATABCDEF_NOT +
					" OR 'abc' || 'def' <> 'abcdef'",predResult8);
		
		String predResult9 = PredicateBuilder
				.where(Expression.stringExpression(Expression.constant("abc")).concat("def"))
							  	 .isGreaterOrEqual(Expression.constant(ABCDEF))
//							  	 .or(Expression.stringExpression("abc").concat("def"))
//							  	 .isNotEqual(ABCDEF)
							  	 .build().toString();
		Assertions.assertEquals(WHERECONCATABCDEF_GTE,predResult9);
//					" OR 'abc' || 'def' != 'abcdef"
//					 ',predResult9);
	
	}
	
	
	@Test 
	void testWhereAndOrPredicates() {
		
		String predResult = PredicateBuilder.where(10).isEqual(10)
				 .and(10).isEqual(10)
				 .or(10).isEqual(10).build().toString();
		Assertions.assertEquals("WHERE 10 = 10 AND 10 = 10" +
							" OR 10 = 10",predResult);

		String predResult2 = PredicateBuilder.where(Expression.constant(10))
				  .isLessOrEqual(10)
				  .or(Expression.constant(10))
				  .isLessOrEqual(10)
				  .and(Expression.constant(10))
				  .isLessOrEqual(10)
				  .build().toString();
		Assertions.assertEquals("WHERE 10 <= 10 OR 10 <= 10 AND 10 <= 10",predResult2);

		Column colTest = (Column) Expression.column(COLTEST);
		String predResult3 = PredicateBuilder.where(colTest).isLessOrEqual(10)
				  .and(colTest).isLessOrEqual(10)
				  .or(colTest).isLessOrEqual(10)
				  .and(colTest).isLess(10)
				  .build().toString();
		Assertions.assertEquals(WHERECOLTESTLETEN +
				" AND coltest <= 10" +
				" OR coltest <= 10"  +
				" AND coltest < 10",predResult3);

		String predResult4 = PredicateBuilder.where(Expression.parm(Types.BIGINT, 10))
						  .isLessOrEqual(10)
						  .or(Expression.parm(Types.INTEGER,10)).isGreater(10)
						  .or(Expression.parm(Types.SMALLINT, 10))
						  .isGreaterOrEqual(10)
						  .and(Expression.parm(Types.BIGINT, 10))
						  .isLessOrEqual(10)
						  .and(Expression.parm(Types.DOUBLE, 10.01))
						  .isNotEqual(10.01)
						  .build().toString();
		Assertions.assertEquals("WHERE ? <= 10"
				+ " OR ? > 10"
				+ " OR ? >= 10"
				+ " AND ? <= 10"
				+ " AND ? <> 10.01",predResult4);

		String predResult5 = PredicateBuilder.where(Expression.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .and(Expression.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .or(Expression.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .and(Expression.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .or(Expression.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .or(Expression.mathExpression(10).add(10))
						  .isNot(10)
						  .build().toString();
		Assertions.assertEquals(WHERETENPLUS10LETEN +
				ANDTENPLUS10LETEN +
				ORTENPLUS10LETEN 		 +
				ANDTENPLUS10LETEN 	 +
				ORTENPLUS10LETEN 		 +
				" OR 10 + 10 ! 10",predResult5);

		String predResult6 = PredicateBuilder.where(Expression.stringExpression("abc").concat("def"))
							  .isLessOrEqual(ABCDEF)
							  .and(Expression.stringExpression(Expression.constant("abc2")).concat("def2"))
							  .isLessOrEqual("abc2def2")
							  .and(Expression.stringExpression(Expression.constant("abc3")).concat("def3"))
							  .isLessOrEqual("abc3def3")
							  .or(Expression.stringExpression(Expression.constant("abc4")).concat("def4"))
							  .isLessOrEqual("abc4def4")
							  .or(Expression.stringExpression(Expression.constant("abc5")).concat("def5"))
							  .isLessOrEqual("abc5def5")
							  .and(Expression.stringExpression(Expression.constant("abc6")).concat("def6"))
							  .isLessOrEqual("abc6def6")
							  .build().toString();
		Assertions.assertEquals(WHERECONCATABCDEF +
					" AND 'abc2' || 'def2' <= 'abc2def2'" +
					" AND 'abc3' || 'def3' <= 'abc3def3'" +
					" OR 'abc4' || 'def4' <= 'abc4def4'" +
					" OR 'abc5' || 'def5' <= 'abc5def5'" +
					" AND 'abc6' || 'def6' <= 'abc6def6'",predResult6);

	}
	
//	@Test 
//	void testExpressionsComparison() {
//		fail("Not Implmented yet");
//	}

}
