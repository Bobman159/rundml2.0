package com.bobman159.rundml.jdbc.utils.tests;

import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.impl.SQLClauses.SQLClause;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * A singleton factory class that creates <code>SQLModelFactory</code> instances for testing
 *
 */
public class SQLModelTestFactory {
	
	private static SQLModelTestFactory self = null;
	private static final String DFLTINTEGER = "DfltInteger";
	private static final String NOTNULLMEDINT = "NotNullMediumInt";
	private static final String DFLTSIGNED = "DfltSigned";
	private static final String DFLTINTUNSIGNED = "DfltIntUnsigned";	//MySQL TypeTest
	private static final String DFLTTINYINT = "DfltTinyInt";
	private static final String NOTNULLSMINT = "NotNullSmint";
	private static final String NOTNULLDEC72 = "NotNullDec72";	
	private static final String DFLTNUM72 = "DfltNumber72";

	private static final String NOTNULLTIME = "NotNullTime";
	private static final String NOTNULLDATE = "NotNullDate";
	private static final String NOTNULLTSTAMP = "NotNullTimestamp";
	private static final String NOTNULLDATETIME = "NotNullDateTime";
	private static final String NOTNULLVARCHAR = "NotNullVarchar";
	private static final String NOTNULLCHAR = "NotNullChar";
	private static final String DFLTBLOB = "DfltBlob";
	private static final String NOTNULLBLOB = "NotNullBlob";		//MySQL rundml.typetest
	private static final String NOTNULLTEXT = "NotNullText";		//MySQL rundml.typetest

	private static final String DFLTCLOB = "DfltClob";
	private static final String NOTNULLBOOLEAN = "NotNullBoolean";
	private static final String NOTNULLBOOL = "NotNullBool";
	private static final String NOTNULLBIT = "NotNullBit";
	private static final String NOTNULLBIGINT = "NotNullBigInt";	//MySQL rundml.typetest
	private static final String DFLTBIGINT = "DfltBigInt";
	private static final String DFLTINT8 = "DfltInt8";
	private static final String NOTNULLIDENTITY = "NotNullIdentity";
	private static final String NOTNULLBINARY = "NotNullBinary";	//MySQL rundml.typetest
	private static final String NOTNULLVARBINARY = "NotNullVarBinary";	//MySQL rundml.typetest
	private static final String DFLTVARBINARY = "DfltVarBinary";
	
	private static final String RUNDML_SCHEMA = "rundml";
	private static final String TYPETEST_TABLE = "typetest";
	
	/**
	 * Obtain the instance of this factory class
	 * @return
	 */
	public static SQLModelTestFactory getInstance() {
		if (self == null) {
			self = new SQLModelTestFactory();
		}
		return self;
	}
	
	/**
	 * Creates a basic SELECT statement model for the rundml.TypeTest in the H2 database.
	 * Only column names are used for the SELECT expression list.
	 * @return a SELECT statement SQL model
	 */
	public SQLStatementModel createH2SelectTypeTestModel() {

		SQLStatementModel model = new SQLStatementModel();
		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTINTEGER));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLMEDINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTSIGNED));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTTINYINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLSMINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLDEC72));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTNUM72));

		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLTIME));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLDATE));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLTSTAMP));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLDATETIME));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLVARCHAR));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLCHAR));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTBLOB));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTCLOB));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBOOLEAN));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBOOL));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBIT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTBIGINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTINT8));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBINARY));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTVARBINARY));
		model.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName(RUNDML_SCHEMA, TYPETEST_TABLE));

		return model;
	}
	
	/**
	 * Creates a basic SELECT statement model for the rundml.TypeTest in the MySQL database.
	 * Only column names are used for the SELECT expression list.
	 * @return a SELECT statement SQL model
	 */
	public SQLStatementModel createMySQLSelectTypeTestModel() {
				
		SQLStatementModel model = new SQLStatementModel();
		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTINTEGER));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLMEDINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTINTUNSIGNED));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(DFLTTINYINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLSMINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLDEC72));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLTIME));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLDATE));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLTSTAMP));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLDATETIME));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLVARCHAR));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLCHAR));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBLOB));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLTEXT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBOOLEAN));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBIT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBIGINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLBINARY));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column(NOTNULLVARBINARY));
		model.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName(RUNDML_SCHEMA, TYPETEST_TABLE));

		return model;
	}
	
	/**
	 * Creates a basic SELECT statement model for the rundml.TypeTest table., 
	 * An undefined column name is used for the SELECT expression list.
	 * @return a SELECT statement SQL model
	 */
	public SQLStatementModel createUndefinedColumnSelectModel() {
		
		SQLStatementModel model = new SQLStatementModel();
		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.getInstance().column("Col01"));
		model.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName(RUNDML_SCHEMA, TYPETEST_TABLE));

		return model;
	}
 	
}
