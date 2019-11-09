package com.bobman159.rundml.core.predicates.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.exprtypes.Column;
import com.bobman159.rundml.core.predicates.Predicate;

class PredicateWhereTest {

	private static final String ANDTENPLUS10LETEN = " AND 10 + 10 <= 10";
	private static final String ORTENPLUS10LETEN = " OR 10 + 10 <= 10";
	private static final String WHERECONCATABCDEF = "WHERE 'abc' || 'def' <= 'abcdef'";
	private static final String WHERETENPLUS10LETEN = "WHERE 10 + 10 <= 10";
	private static final String WHERECOLTESTLETEN = "WHERE COLTEST <= 10";
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
		
		String predResult = Predicate.where(10).isEqual(10).build().serialize();
		Assertions.assertEquals("WHERE 10 = 10",predResult);
		
		String predResult2 = Predicate.where(Expression.number(10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assertions.assertEquals("WHERE 10 <= 10",predResult2);
		
		Column colTest = Expression.column(COLTEST);
		String predResult3 = Predicate.where(colTest).isLessOrEqual(10)
									  .build().serialize();
		Assertions.assertEquals(WHERECOLTESTLETEN,predResult3);
		
		String predResult4 = Predicate.where(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assertions.assertEquals("WHERE ? <= 10",predResult4);
		
		String predResult5 = Predicate.where(Expression.number(10).add(10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assertions.assertEquals(WHERETENPLUS10LETEN,predResult5);
		
		String predResult6 = Predicate.where(Expression.string("abc").concat("def"))
				  					  .isLessOrEqual(ABCDEF)
				  					  .build().serialize();
		Assertions.assertEquals("WHERE \'abc\' || \'def\' <= 'abcdef'",predResult6);
	
	}
	
	@Test
	void testAndPredicates() {
		
		String predResult = Predicate.where(10).isEqual(10)
									 .and(10).isEqual(10).build().serialize();
		Assertions.assertEquals("WHERE 10 = 10 AND 10 = 10",predResult);
		
		String predResult2 = Predicate.where(Expression.number(10))
									  .isLessOrEqual(10)
									  .and(Expression.number(10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assertions.assertEquals("WHERE 10 <= 10 AND 10 <= 10",predResult2);
		
		Column colTest = Expression.column(COLTEST);
		String predResult3 = Predicate.where(colTest).isLessOrEqual(10)
									  .and(colTest).isLessOrEqual(10)
									  .build().serialize();
		Assertions.assertEquals(WHERECOLTESTLETEN +
							" AND COLTEST <= 10",predResult3);
		
		String predResult4 = Predicate.where(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .and(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assertions.assertEquals("WHERE ? <= 10 AND ? <= 10",predResult4);
		
		String predResult5 = Predicate.where(Expression.number(10).add(10))
									  .isLessOrEqual(10)
									  .and(Expression.number(10).add(10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assertions.assertEquals(WHERETENPLUS10LETEN +
							ANDTENPLUS10LETEN,predResult5);
		
		String predResult6 = Predicate.where(Expression.string("abc").concat("def"))
				  					  .isLessOrEqual(ABCDEF)
				  					  .and(Expression.string("abc").concat("def"))
				  					  .isLessOrEqual(ABCDEF)
				  					  .build().serialize();
		Assertions.assertEquals(WHERECONCATABCDEF +
				  			" AND 'abc' || 'def' <= 'abcdef'",predResult6);
	
	}
	
	@Test
	void testOrPredicates() {
		
		String predResult = Predicate.where(10).isEqual(10)
				 .or(10).isEqual(10).build().serialize();
		Assertions.assertEquals("WHERE 10 = 10 OR 10 = 10",predResult);

		String predResult2 = Predicate.where(Expression.number(10))
				  					  .isLessOrEqual(10)
				  					  .or(Expression.number(10))
				  					  .isLessOrEqual(10)
				  					  .build().serialize();
		Assertions.assertEquals("WHERE 10 <= 10 OR 10 <= 10",predResult2);

		Column colTest = Expression.column(COLTEST);
		String predResult3 = Predicate.where(colTest).isLessOrEqual(10)
						  			  .or(colTest).isLessOrEqual(10)
						  			  .build().serialize();
		Assertions.assertEquals(WHERECOLTESTLETEN +
				" OR COLTEST <= 10",predResult3);

		String predResult4 = Predicate.where(Expression.parm(Types.BIGINT, 10))
						  			  .isLessOrEqual(10)
						  			  .or(Expression.parm(Types.BIGINT, 10))
						  			  .isLessOrEqual(10)
						  			  .build().serialize();
		Assertions.assertEquals("WHERE ? <= 10 OR ? <= 10",predResult4);

		String predResult5 = Predicate.where(Expression.number(10).add(10))
						  			  .isLessOrEqual(10)
						  			  .or(Expression.number(10).add(10))
						  			  .isLessOrEqual(10)
						  			  .build().serialize();
		Assertions.assertEquals(WHERETENPLUS10LETEN +
				ORTENPLUS10LETEN,predResult5);

		String predResult6 = Predicate.where(Expression.string("abc").concat("def"))
							  		  .isLessOrEqual(ABCDEF)
							  		  .or(Expression.string("abc").concat("def"))
							  		  .isLessOrEqual(ABCDEF)
							  		  .build().serialize();
		Assertions.assertEquals(WHERECONCATABCDEF +
					" OR 'abc' || 'def' <= 'abcdef'",predResult6);
	
	}
	
	
	@Test 
	void testWhereAndOrPredicates() {
		
		String predResult = Predicate.where(10).isEqual(10)
				 .and(10).isEqual(10)
				 .or(10).isEqual(10).build().serialize();
		Assertions.assertEquals("WHERE 10 = 10 AND 10 = 10" +
							" OR 10 = 10",predResult);

		String predResult2 = Predicate.where(Expression.number(10))
				  .isLessOrEqual(10)
				  .or(Expression.number(10))
				  .isLessOrEqual(10)
				  .and(Expression.number(10))
				  .isLessOrEqual(10)
				  .build().serialize();
		Assertions.assertEquals("WHERE 10 <= 10 OR 10 <= 10 AND 10 <= 10",predResult2);

		Column colTest = Expression.column(COLTEST);
		String predResult3 = Predicate.where(colTest).isLessOrEqual(10)
				  .and(colTest).isLessOrEqual(10)
				  .or(colTest).isLessOrEqual(10)
				  .and(colTest).isLess(10)
				  .build().serialize();
		Assertions.assertEquals(WHERECOLTESTLETEN +
				" AND COLTEST <= 10" +
				" OR COLTEST <= 10"  +
				" AND COLTEST < 10",predResult3);

		String predResult4 = Predicate.where(Expression.parm(Types.BIGINT, 10))
						  .isLessOrEqual(10)
						  .or(Expression.parm(Types.INTEGER,10)).isGreater(10)
						  .or(Expression.parm(Types.SMALLINT, 10))
						  .isGreaterOrEqual(10)
						  .and(Expression.parm(Types.BIGINT, 10))
						  .isLessOrEqual(10)
						  .and(Expression.parm(Types.DOUBLE, 10.01))
						  .isNotEqual(10.01)
						  .build().serialize();
		Assertions.assertEquals("WHERE ? <= 10"
				+ " OR ? > 10"
				+ " OR ? >= 10"
				+ " AND ? <= 10"
				+ " AND ? <> 10.01",predResult4);

		String predResult5 = Predicate.where(Expression.number(10).add(10))
						  .isLessOrEqual(10)
						  .and(Expression.number(10).add(10))
						  .isLessOrEqual(10)
						  .or(Expression.number(10).add(10))
						  .isLessOrEqual(10)
						  .and(Expression.number(10).add(10))
						  .isLessOrEqual(10)
						  .or(Expression.number(10).add(10))
						  .isLessOrEqual(10)
						  .or(Expression.number(10).add(10))
						  .isNot(10)
						  .build().serialize();
		Assertions.assertEquals(WHERETENPLUS10LETEN +
				ANDTENPLUS10LETEN +
				ORTENPLUS10LETEN 		 +
				ANDTENPLUS10LETEN 	 +
				ORTENPLUS10LETEN 		 +
				" OR 10 + 10 ! 10",predResult5);

		String predResult6 = Predicate.where(Expression.string("abc").concat("def"))
							  .isLessOrEqual(ABCDEF)
							  .and(Expression.string("abc2").concat("def2"))
							  .isLessOrEqual("abc2def2")
							  .and(Expression.string("abc3").concat("def3"))
							  .isLessOrEqual("abc3def3")
							  .or(Expression.string("abc4").concat("def4"))
							  .isLessOrEqual("abc4def4")
							  .or(Expression.string("abc5").concat("def5"))
							  .isLessOrEqual("abc5def5")
							  .and(Expression.string("abc6").concat("def6"))
							  .isLessOrEqual("abc6def6")
							  .build().serialize();
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
