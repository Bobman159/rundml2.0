package com.bobman159.rundml.sql.h2.mocktables;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.bobman159.rundml.core.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.mapping.IFieldMap;

public class H2NoClassFieldExceptionMock implements IFieldMap {

	private int		DfltInteger;
	private int		NotNullMediumInt;
	private int		DfltSigned;
	private byte	DfltTinyInt;
	private short	NotNullSmint;
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
	private boolean		NotNullBoolean;
	private boolean 	NotNullBool;
	private boolean		NotNullBit;
	private long		DfltBigInt;
	private long		DfltInt8;
	private long		NotNullIdentity;
	
	@Override
	public FieldMapDefinitionList getFieldMappings(FieldMapDefinitionList fieldList) {
		fieldList.addDefinition("DfltInteger", "fieldOne");
		fieldList.addDefinition("medIntNotNull", "fieldTwoNotFound");
		return fieldList;
	}
	
	
}
