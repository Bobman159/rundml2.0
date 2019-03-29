package com.bobman159.rundml.core.predicates.tests;

import java.sql.Types;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.exprtypes.Column;
import com.bobman159.rundml.core.predicates.Predicate;

class PredicateWhereTest {

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
	void testWherePredicates() {
		
		String predResult = Predicate.where(10).isEqual(10).build().serialize();
		Assert.assertEquals("WHERE 10 = 10",predResult);
		
		String predResult2 = Predicate.where(Expression.number(10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assert.assertEquals("WHERE 10 <= 10",predResult2);
		
		Column colTest = new Column("coltest",Types.INTEGER);
		String predResult3 = Predicate.where(colTest).isLessOrEqual(10)
									  .build().serialize();
		Assert.assertEquals("WHERE COLTEST <= 10",predResult3);
		
		String predResult4 = Predicate.where(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assert.assertEquals("WHERE ? <= 10",predResult4);
		
		String predResult5 = Predicate.where(Expression.number(10).add(10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assert.assertEquals("WHERE 10 + 10 <= 10",predResult5);
		
		String predResult6 = Predicate.where(Expression.string("abc").concat("def"))
				  					  .isLessOrEqual("abcdef")
				  					  .build().serialize();
		Assert.assertEquals("WHERE \'abc\' || \'def\' <= 'abcdef'",predResult6);
	
	}
	
	@Test
	void testAndPredicates() {
		
		String predResult = Predicate.where(10).isEqual(10)
									 .and(10).isEqual(10).build().serialize();
		Assert.assertEquals("WHERE 10 = 10 AND 10 = 10",predResult);
		
		String predResult2 = Predicate.where(Expression.number(10))
									  .isLessOrEqual(10)
									  .and(Expression.number(10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assert.assertEquals("WHERE 10 <= 10 AND 10 <= 10",predResult2);
		
		Column colTest = new Column("coltest",Types.INTEGER);
		String predResult3 = Predicate.where(colTest).isLessOrEqual(10)
									  .and(colTest).isLessOrEqual(10)
									  .build().serialize();
		Assert.assertEquals("WHERE COLTEST <= 10" +
							" AND COLTEST <= 10",predResult3);
		
		String predResult4 = Predicate.where(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .and(Expression.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assert.assertEquals("WHERE ? <= 10 AND ? <= 10",predResult4);
		
		String predResult5 = Predicate.where(Expression.number(10).add(10))
									  .isLessOrEqual(10)
									  .and(Expression.number(10).add(10))
									  .isLessOrEqual(10)
									  .build().serialize();
		Assert.assertEquals("WHERE 10 + 10 <= 10" +
							" AND 10 + 10 <= 10",predResult5);
		
		String predResult6 = Predicate.where(Expression.string("abc").concat("def"))
				  					  .isLessOrEqual("abcdef")
				  					  .and(Expression.string("abc").concat("def"))
				  					  .isLessOrEqual("abcdef")
				  					  .build().serialize();
		Assert.assertEquals("WHERE 'abc' || 'def' <= 'abcdef'" +
				  			" AND 'abc' || 'def' <= 'abcdef'",predResult6);
	
	}
	
	@Test
	void testOrPredicates() {
		
		String predResult = Predicate.where(10).isEqual(10)
				 .or(10).isEqual(10).build().serialize();
		Assert.assertEquals("WHERE 10 = 10 OR 10 = 10",predResult);

		String predResult2 = Predicate.where(Expression.number(10))
				  					  .isLessOrEqual(10)
				  					  .or(Expression.number(10))
				  					  .isLessOrEqual(10)
				  					  .build().serialize();
		Assert.assertEquals("WHERE 10 <= 10 OR 10 <= 10",predResult2);

		Column colTest = new Column("coltest",Types.INTEGER);
		String predResult3 = Predicate.where(colTest).isLessOrEqual(10)
						  			  .or(colTest).isLessOrEqual(10)
						  			  .build().serialize();
		Assert.assertEquals("WHERE COLTEST <= 10" +
				" OR COLTEST <= 10",predResult3);

		String predResult4 = Predicate.where(Expression.parm(Types.BIGINT, 10))
						  			  .isLessOrEqual(10)
						  			  .or(Expression.parm(Types.BIGINT, 10))
						  			  .isLessOrEqual(10)
						  			  .build().serialize();
		Assert.assertEquals("WHERE ? <= 10 OR ? <= 10",predResult4);

		String predResult5 = Predicate.where(Expression.number(10).add(10))
						  			  .isLessOrEqual(10)
						  			  .or(Expression.number(10).add(10))
						  			  .isLessOrEqual(10)
						  			  .build().serialize();
		Assert.assertEquals("WHERE 10 + 10 <= 10" +
				" OR 10 + 10 <= 10",predResult5);

		String predResult6 = Predicate.where(Expression.string("abc").concat("def"))
							  		  .isLessOrEqual("abcdef")
							  		  .or(Expression.string("abc").concat("def"))
							  		  .isLessOrEqual("abcdef")
							  		  .build().serialize();
		Assert.assertEquals("WHERE 'abc' || 'def' <= 'abcdef'" +
					" OR 'abc' || 'def' <= 'abcdef'",predResult6);
	
	}
	
	
	@Test 
	void testWhereAndOrPredicates() {
		
		String predResult = Predicate.where(10).isEqual(10)
				 .and(10).isEqual(10)
				 .or(10).isEqual(10).build().serialize();
		Assert.assertEquals("WHERE 10 = 10 AND 10 = 10" +
							" OR 10 = 10",predResult);

		String predResult2 = Predicate.where(Expression.number(10))
				  .isLessOrEqual(10)
				  .or(Expression.number(10))
				  .isLessOrEqual(10)
				  .and(Expression.number(10))
				  .isLessOrEqual(10)
				  .build().serialize();
		Assert.assertEquals("WHERE 10 <= 10 OR 10 <= 10 AND 10 <= 10",predResult2);

		Column colTest = new Column("coltest",Types.INTEGER);
		String predResult3 = Predicate.where(colTest).isLessOrEqual(10)
				  .and(colTest).isLessOrEqual(10)
				  .or(colTest).isLessOrEqual(10)
				  .and(colTest).isLess(10)
				  .build().serialize();
		Assert.assertEquals("WHERE COLTEST <= 10" +
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
		Assert.assertEquals("WHERE ? <= 10"
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
		Assert.assertEquals("WHERE 10 + 10 <= 10" +
				" AND 10 + 10 <= 10" +
				" OR 10 + 10 <= 10" 		 +
				" AND 10 + 10 <= 10" 	 +
				" OR 10 + 10 <= 10" 		 +
				" OR 10 + 10 ! 10",predResult5);

		String predResult6 = Predicate.where(Expression.string("abc").concat("def"))
							  .isLessOrEqual("abcdef")
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
		Assert.assertEquals("WHERE 'abc' || 'def' <= 'abcdef'" +
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
