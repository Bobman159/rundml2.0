package com.bobman159.rundml.sql.mysql.mocktables;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.bobman159.rundml.jdbc.mapping.CaseInsensitiveFieldsMap;
import com.bobman159.rundml.jdbc.mapping.IFieldMap;

public class MockMySQLPrimitivesTypeTest implements IFieldMap {


	private int DfltInteger;
	private int medIntNotNull;
	private int unsignedDflt;
	private byte tinyIntDflt;
	private short 	NotNullSmint;
	private BigDecimal	NotNullDec72;	
	private Time 	timeNotNull;
	private Date	NotNullDate;
	private Timestamp	NotNullTimestamp;
	private Timestamp 	NotNullDateTime;
	private String	NotNullVarchar;
	private String	charNotNull;
	private Blob	NotNullBlob;
	private Clob	lobCharCol;
	private Boolean	booleanNotNull;
	private Boolean	bitNotNull;
	private long	NotNullBigInt;
	private byte[]	binaryNotNull;
	private byte[]  NotNullVarBinary;
	
	public int getDfltInteger() {
		return DfltInteger;
	}
	public int getMedIntNotNull() {
		return medIntNotNull;
	}
	public int getUnsignedDflt() {
		return unsignedDflt;
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
	public Blob getNotNullBlob() {
		return NotNullBlob;
	}
	public Clob getLobCharCol() {
		return lobCharCol;
	}
	public Boolean getBooleanNotNull() {
		return booleanNotNull;
	}
	public Boolean getBitNotNull() {
		return bitNotNull;
	}
	public Long getDfltBigInt() {
		return NotNullBigInt;
	}
	public byte[] getNotNullBinary() {
		return binaryNotNull;
	}
	public byte[] getNotNullVarBinary() {
		return NotNullVarBinary;
	}
	@Override
	public CaseInsensitiveFieldsMap<String, String> getFieldMappings() {

		CaseInsensitiveFieldsMap<String,String> map = new CaseInsensitiveFieldsMap<String,String>();
		map.put("NotNullMediumInt","medIntNotNull");
		map.put("DfltIntUnsigned","unsignedDflt");
		map.put("DfltTinyInt","tinyIntDflt");
		map.put("NotNullTime","timeNotNull");
		map.put("NotNullChar","charNotNull");
		map.put("NotNullText","lobCharCol");
		map.put("NotNullBoolean","booleanNotNull");
		map.put("NotNullBit","bitNotNull");
		map.put("NotNullBinary","binaryNotNull");
		
		return map;
	}
	
	
}
