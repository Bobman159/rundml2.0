package com.bobman159.rundml.integration.h2.mocks;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Test class for an IllegalAccessException because a class field is defined as final.
 *
 */
@SuppressWarnings("unused")
public class H2IllegalAccessExceptionMock {

	//Force IllegalAccessException in ResultSetMapper.mapColumnToField method
	private static final int DfltInteger = -1;	//Force IllegalAccessException
	private Integer 	NotNullMediumInt;
	private Integer 	DfltSigned;
	private Byte    	DfltTinyInt;

	private Short 		NotNullSmint;
	private BigDecimal	NotNullDec72;	
	private BigDecimal 	DfltNumber72;
	private Time 		NotNullTime;
	private Date		NotNullDate;
	private Timestamp	NotNullTimestamp;
	private Timestamp 	NotNullDateTime;
	private String		NotNullVarchar;
	private String		NotNullChar;
	private Blob		DfltBlob;
	private Clob		DfltClob;
	private Boolean		NotNullBoolean;
	private Boolean 	NotNullBool;
	private Boolean		NotNullBit;
	private Long		DfltBigInt;
	private Long		DfltInt8;
	private Long		NotNullIdentity;
	
	public int getDfltInteger() {
		return DfltInteger;
	}
	
	public int getDfltSigned() {
		return DfltSigned;
		
	}
}	
