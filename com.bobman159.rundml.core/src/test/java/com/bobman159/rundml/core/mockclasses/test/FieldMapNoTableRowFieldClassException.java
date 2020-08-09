package com.bobman159.rundml.core.mockclasses.test;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.bobman159.rundml.core.model.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.model.mapping.IFieldMap;
@SuppressWarnings("unused")
public class FieldMapNoTableRowFieldClassException implements IFieldMap {

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
