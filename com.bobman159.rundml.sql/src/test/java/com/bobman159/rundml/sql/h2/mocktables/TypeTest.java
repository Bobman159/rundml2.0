package com.bobman159.rundml.sql.h2.mocktables;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;

import com.bobman159.rundml.jdbc.mapping.IFieldMap;

public class TypeTest {
	
	private Integer 	DfltInteger;
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
	
	public Integer getDfltInteger() {
		return DfltInteger;
	}
	
	public Integer getNotNullMediumInt() {
		return NotNullMediumInt;
	}
	
	public Integer getDfltSigned() {
		return DfltSigned;
	}
	public Byte getDfltTinyInt() {
		return DfltTinyInt;
	}
	public Short getNotNullSmint() {
		return NotNullSmint;
	}
	public BigDecimal getNotNullDec72() {
		return NotNullDec72;
	}
	public BigDecimal getDfltNumber72() {
		return DfltNumber72;
	}
	public Time getNotNullTime() {
		return NotNullTime;
	}
	public Date getNotNullDate() {
		return NotNullDate;
	}
	public Timestamp getNotNullTimestamp() {
		return NotNullTimestamp;
	}
	public Timestamp getNotNullDateTime() {
		return NotNullDateTime;
	}
	public String getNotNullVarchar() {
		return NotNullVarchar;
	}
	public String getNotNullChar() {
		return NotNullChar;
	}
	public Blob getDfltBlob() {
		return DfltBlob;
	}
	public Clob getDfltClob() {
		return DfltClob;
	}
	public Boolean getNotNullBoolean() {
		return NotNullBoolean;
	}
	public Boolean getNotNullBool() {
		return NotNullBool;
	}
	public Boolean getNotNullBit() {
		return NotNullBit;
	}
	public Long getDfltBigInt() {
		return DfltBigInt;
	}
	public Long getDfltInt8() {
		return DfltInt8;
	}
	public Long getNotNullIdentity() {
		return NotNullIdentity;
	}

}
