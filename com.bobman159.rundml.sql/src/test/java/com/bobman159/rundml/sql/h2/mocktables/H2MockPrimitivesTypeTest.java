package com.bobman159.rundml.sql.h2.mocktables;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.bobman159.rundml.jdbc.mapping.CaseInsensitiveFieldsMap;
import com.bobman159.rundml.jdbc.mapping.IFieldMap;

/**
 * Mock object for testing RunDML mapping SELECT results to 
 * a class with a different fame than the table being queried.
 *
 */
public class H2MockPrimitivesTypeTest implements IFieldMap {

	private int DfltInteger;
	private int medIntNotNull;
	private int signedDflt;
	private byte tinyIntDflt;
	private short 	NotNullSmint;
	private BigDecimal	NotNullDec72;	
	private BigDecimal 	num72Dflt;
	private Time 	timeNotNull;
	private Date	NotNullDate;
	private Timestamp	NotNullTimestamp;
	private Timestamp 	NotNullDateTime;
	private String	NotNullVarchar;
	private String	charNotNull;
	private Blob	DfltBlob;
	private Clob	lobCharCol;
	private Boolean	booleanNotNull;
	private Boolean NotNullBool;
	private Boolean	bitNotNull;
	private long	DfltBigInt;
	private long	dflt8Col;
	private long	NotNullIdentity;
	
	
	public int getDfltInteger() {
		return DfltInteger;
	}
	public int getMedIntNotNull() {
		return medIntNotNull;
	}
	public int getSignedDflt() {
		return signedDflt;
	}
	public byte getTinyIntDflt() {
		return tinyIntDflt;
	}
	public short getNotNullSmint() {
		return NotNullSmint;
	}
	public BigDecimal getNotNullDec72() {
		return NotNullDec72;
	}
	public BigDecimal getNum72Dflt() {
		return num72Dflt;
	}
	public Time getTimeNotNull() {
		return timeNotNull;
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
	public String getCharNotNull() {
		return charNotNull;
	}
	public Blob getDfltBlob() {
		return DfltBlob;
	}
	public Clob getLobCharCol() {
		return lobCharCol;
	}
	public Boolean getBooleanNotNull() {
		return booleanNotNull;
	}
	public Boolean getNotNullBool() {
		return NotNullBool;
	}
	public Boolean getBitNotNull() {
		return bitNotNull;
	}
	public long getDfltBigInt() {
		return DfltBigInt;
	}
	public long getDflt8Col() {
		return dflt8Col;
	}
	public long getNotNullIdentity() {
		return NotNullIdentity;
	}
	
	@Override
	public CaseInsensitiveFieldsMap<String, String> getFieldMappings() {
		
		CaseInsensitiveFieldsMap<String,String> map = new CaseInsensitiveFieldsMap<String,String>();
		map.put("NotNullMediumInt","medIntNotNull");
		map.put("DfltSigned","signedDflt");
		map.put("DfltTinyInt","tinyIntDflt");
		map.put("DfltNumber72","num72Dflt");
		map.put("NotNullTime","timeNotNull");
		map.put("NotNullChar","charNotNull");
		map.put("DfltClob","lobCharCol");
		map.put("NotNullBoolean","booleanNotNull");
		map.put("NotNullBit","bitNotNull");
		map.put("DfltInt8","dflt8Col");
		return map;
	}
	

	
}
