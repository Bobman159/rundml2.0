package com.bobman159.rundml.core.predicates.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;
import com.bobman159.rundml.core.sql.BaseSQLSerializer;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
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
	
	private final BaseSQLSerializer serializer = new BaseSQLSerializer();
		
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

		
		String predResult = serializer.serialize(PredicateBuilder.where(10).isEqual(10).build());
		Assertions.assertEquals("WHERE 10 = 10",predResult);
		
		String predResult2 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.constant(10))
									  .isLessOrEqual(10)
									  .build());
		Assertions.assertEquals("WHERE 10 <= 10",predResult2);

		Column colTest = (Column) SQLTypeFactory.column(COLTEST);
		String predResult3 = serializer.serialize(PredicateBuilder.where(colTest).isLessOrEqual(10)
									  .build());
		Assertions.assertEquals(WHERECOLTESTLETEN,predResult3);
		
		String predResult4 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .build());
		Assertions.assertEquals("WHERE ? <= 10",predResult4);
		
		String predResult5 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.mathExpression(10).add(10))
									  .isLessOrEqual(10)
									  .build());
		Assertions.assertEquals(WHERETENPLUS10LETEN,predResult5);
		
		String predResult6 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.stringExpression(
													SQLTypeFactory.constant("abc")).concat("def"))
				  					  .isLessOrEqual(ABCDEF)
				  					  .build());
		Assertions.assertEquals("WHERE \'abc\' || \'def\' <= 'abcdef'",predResult6);
		
		String predResult7 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.stringExpression(
				SQLTypeFactory.constant("abc")).concat("def"))
						  .isLessOrEqual(SQLTypeFactory.constant(ABCDEF))
						  .build());
		Assertions.assertEquals("WHERE \'abc\' || \'def\' <= 'abcdef'",predResult7);
		
		String predResult8 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.stringExpression(
				SQLTypeFactory.constant("abc")).concat("def"))
						  .isLessOrEqual(SQLTypeFactory.constant(ABCDEF))
						  .build());
		Assertions.assertEquals("WHERE \'abc\' || \'def\' <= 'abcdef'",predResult8);
		
		String predResult9 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(10))
				.add(SQLTypeFactory.constant(20)))
			  .isEqual(SQLTypeFactory.constant(30))
			  .build());
		Assertions.assertEquals("WHERE 10 + 20 = 30",predResult9);
		
		String predResult10 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.mathExpression(SQLTypeFactory.parm(Types.INTEGER, 10))
				.add(SQLTypeFactory.parm(Types.SMALLINT, 20)))
			  .isGreater(SQLTypeFactory.parm(Types.BIGINT,30))
			  .build());
		Assertions.assertEquals("WHERE ? + ? > ?",predResult10);
		
		String predResult11 = serializer.serialize(PredicateBuilder.where("Xyz")
			  .isGreater("Abc")
			  .build());
		Assertions.assertEquals("WHERE 'Xyz' > 'Abc'",predResult11);
	
	}
	
	@Test
	void testAndPredicates() {
		
		String predResult = serializer.serialize(PredicateBuilder.where(10).isEqual(10)
									 .and(10).isEqual(10).build());
		Assertions.assertEquals("WHERE 10 = 10 AND 10 = 10",predResult);
		
		String predResult2 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.constant(10))
									  .isLessOrEqual(10)
									  .and(SQLTypeFactory.constant(10))
									  .isLessOrEqual(10)
									  .build());
		Assertions.assertEquals("WHERE 10 <= 10 AND 10 <= 10",predResult2);
		
		Column colTest = (Column) SQLTypeFactory.column(COLTEST);
		String predResult3 = serializer.serialize(PredicateBuilder.where(colTest).isLessOrEqual(10)
									  .and(colTest).isLessOrEqual(10)
									  .build());
		Assertions.assertEquals(WHERECOLTESTLETEN +
							" AND coltest <= 10",predResult3);
		
		String predResult4 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .and(SQLTypeFactory.parm(Types.BIGINT, 10))
									  .isLessOrEqual(10)
									  .build());
		Assertions.assertEquals("WHERE ? <= 10 AND ? <= 10",predResult4);
		
		String predResult5 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.mathExpression(10).add(10))
									  .isLessOrEqual(10)
									  .and(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(10)).add(10))
									  .isLessOrEqual(10)
									  .build());
		Assertions.assertEquals(WHERETENPLUS10LETEN +
							ANDTENPLUS10LETEN,predResult5);
		
		String predResult6 = serializer.serialize(PredicateBuilder.where(
				SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
				  					  .isLessOrEqual(ABCDEF)
				  					  .and(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
				  					  .isEqual(ABCDEF)
				  					  .build());
		Assertions.assertEquals(WHERECONCATABCDEF +
				  			" AND 'abc' || 'def' = 'abcdef'",predResult6);
		
		String predResult7 = serializer.serialize(PredicateBuilder.where(
				SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
				  					  .isLess(ABCDEF)
				  					  .and(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
				  					  .isLess(SQLTypeFactory.column("col01"))
				  					  .build());
		Assertions.assertEquals(WHERECONCATABCDEF_LT +
				  			" AND 'abc' || 'def' < col01",predResult7);
		
		String predResult8 = serializer.serialize(PredicateBuilder.where(
				SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
				  					  .isLess(ABCDEF)
				  					  .and(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
				  					  .isLessOrEqual(SQLTypeFactory.stringExpression(SQLTypeFactory.column("col01")).concat("def"))
				  					  .build());
		Assertions.assertEquals(WHERECONCATABCDEF_LT +
				  			" AND 'abc' || 'def' <= col01 || 'def'",predResult8);
	
	}
	
	@Test
	void testOrPredicates() {
		
		String predResult = serializer.serialize(PredicateBuilder.where(10).isEqual(10)
				 .or(10).isEqual(10).build());
		Assertions.assertEquals("WHERE 10 = 10 OR 10 = 10",predResult);

		String predResult2 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.constant(10))
				  					  .isLessOrEqual(10)
				  					  .or(SQLTypeFactory.constant(10))
				  					  .isLessOrEqual(10)
				  					  .build());
		Assertions.assertEquals("WHERE 10 <= 10 OR 10 <= 10",predResult2);

		Column colTest = (Column) SQLTypeFactory.column(COLTEST);
		String predResult3 = serializer.serialize(PredicateBuilder.where(colTest).isLessOrEqual(10)
						  			  .or(colTest).isLessOrEqual(10)
						  			  .build());
		Assertions.assertEquals(WHERECOLTESTLETEN +
				" OR coltest <= 10",predResult3);

		String predResult4 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.parm(Types.BIGINT, 10))
						  			  .isLessOrEqual(10)
						  			  .or(SQLTypeFactory.parm(Types.BIGINT, 10))
						  			  .isLessOrEqual(10)
						  			  .build());
		Assertions.assertEquals("WHERE ? <= 10 OR ? <= 10",predResult4);

		String predResult5 = serializer.serialize(PredicateBuilder.where(
				SQLTypeFactory.mathExpression(SQLTypeFactory.constant(10)).add(10))
						  .isLessOrEqual(10)
						  .or(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(10)).add(10))
						  .isLessOrEqual(10)
						  .build());
		Assertions.assertEquals(WHERETENPLUS10LETEN +
				ORTENPLUS10LETEN,predResult5);

		String predResult6 = serializer.serialize(PredicateBuilder
				.where(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
							  	 .isLessOrEqual(ABCDEF)
							  	 .or(SQLTypeFactory.stringExpression("abc").concat("def"))
							  	 .isLessOrEqual(ABCDEF)
							  	 .build());
		Assertions.assertEquals(WHERECONCATABCDEF +
					" OR 'abc' || 'def' <= 'abcdef'",predResult6);
		
		String predResult7 = serializer.serialize(PredicateBuilder
				.where(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
							  	 .isLessOrEqual(ABCDEF)
							  	 .or(SQLTypeFactory.stringExpression("abc").concat("def"))
							  	 .isLessOrEqual(ABCDEF)
							  	 .build());
		Assertions.assertEquals(WHERECONCATABCDEF +
					" OR 'abc' || 'def' <= 'abcdef'",predResult7);
		
		String predResult8 = serializer.serialize(PredicateBuilder
				.where(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
							  	 .isNot(ABCDEF)
							  	 .or(SQLTypeFactory.stringExpression("abc").concat("def"))
							  	 .isNotEqual(ABCDEF)
							  	 .build());
		Assertions.assertEquals(WHERECONCATABCDEF_NOT +
					" OR 'abc' || 'def' <> 'abcdef'",predResult8);
		
		String predResult9 = serializer.serialize(PredicateBuilder
				.where(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc")).concat("def"))
							  	 .isGreaterOrEqual(SQLTypeFactory.constant(ABCDEF))
//							  	 .or(SQLTypeFactory.stringExpression("abc").concat("def"))
//							  	 .isNotEqual(ABCDEF)
							  	 .build());
		Assertions.assertEquals(WHERECONCATABCDEF_GTE,predResult9);
//					" OR 'abc' || 'def' != 'abcdef"
//					 ',predResult9);
	
	}
	
	
	@Test 
	void testWhereAndOrPredicates() {
		
		String predResult = serializer.serialize(PredicateBuilder.where(10).isEqual(10)
				 .and(10).isEqual(10)
				 .or(10).isEqual(10).build());
		Assertions.assertEquals("WHERE 10 = 10 AND 10 = 10" +
							" OR 10 = 10",predResult);

		String predResult2 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.constant(10))
				  .isLessOrEqual(10)
				  .or(SQLTypeFactory.constant(10))
				  .isLessOrEqual(10)
				  .and(SQLTypeFactory.constant(10))
				  .isLessOrEqual(10)
				  .build());
		Assertions.assertEquals("WHERE 10 <= 10 OR 10 <= 10 AND 10 <= 10",predResult2);

		Column colTest = (Column) SQLTypeFactory.column(COLTEST);
		String predResult3 = serializer.serialize(PredicateBuilder.where(colTest).isLessOrEqual(10)
				  .and(colTest).isLessOrEqual(10)
				  .or(colTest).isLessOrEqual(10)
				  .and(colTest).isLess(10)
				  .build());
		Assertions.assertEquals(WHERECOLTESTLETEN +
				" AND coltest <= 10" +
				" OR coltest <= 10"  +
				" AND coltest < 10",predResult3);

		String predResult4 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.parm(Types.BIGINT, 10))
						  .isLessOrEqual(10)
						  .or(SQLTypeFactory.parm(Types.INTEGER,10)).isGreater(10)
						  .or(SQLTypeFactory.parm(Types.SMALLINT, 10))
						  .isGreaterOrEqual(10)
						  .and(SQLTypeFactory.parm(Types.BIGINT, 10))
						  .isLessOrEqual(10)
						  .and(SQLTypeFactory.parm(Types.DOUBLE, 10.01))
						  .isNotEqual(10.01)
						  .build());
		Assertions.assertEquals("WHERE ? <= 10"
				+ " OR ? > 10"
				+ " OR ? >= 10"
				+ " AND ? <= 10"
				+ " AND ? <> 10.01",predResult4);

		String predResult5 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .and(SQLTypeFactory.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .or(SQLTypeFactory.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .and(SQLTypeFactory.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .or(SQLTypeFactory.mathExpression(10).add(10))
						  .isLessOrEqual(10)
						  .or(SQLTypeFactory.mathExpression(10).add(10))
						  .isNot(10)
						  .build());
		Assertions.assertEquals(WHERETENPLUS10LETEN +
				ANDTENPLUS10LETEN +
				ORTENPLUS10LETEN 		 +
				ANDTENPLUS10LETEN 	 +
				ORTENPLUS10LETEN 		 +
				" OR 10 + 10 ! 10",predResult5);

		String predResult6 = serializer.serialize(PredicateBuilder.where(SQLTypeFactory.stringExpression("abc").concat("def"))
							  .isLessOrEqual(ABCDEF)
							  .and(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc2")).concat("def2"))
							  .isLessOrEqual("abc2def2")
							  .and(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc3")).concat("def3"))
							  .isLessOrEqual("abc3def3")
							  .or(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc4")).concat("def4"))
							  .isLessOrEqual("abc4def4")
							  .or(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc5")).concat("def5"))
							  .isLessOrEqual("abc5def5")
							  .and(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("abc6")).concat("def6"))
							  .isLessOrEqual("abc6def6")
							  .build());
		Assertions.assertEquals(WHERECONCATABCDEF +
					" AND 'abc2' || 'def2' <= 'abc2def2'" +
					" AND 'abc3' || 'def3' <= 'abc3def3'" +
					" OR 'abc4' || 'def4' <= 'abc4def4'" +
					" OR 'abc5' || 'def5' <= 'abc5def5'" +
					" AND 'abc6' || 'def6' <= 'abc6def6'",predResult6);

	}

}
