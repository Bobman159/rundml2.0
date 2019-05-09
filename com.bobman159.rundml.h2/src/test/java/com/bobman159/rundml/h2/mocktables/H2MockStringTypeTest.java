package com.bobman159.rundml.h2.mocktables;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.bobman159.rundml.jdbc.select.ITableRow;

public class H2MockStringTypeTest implements ITableRow {
	
	private String	intDflt;
	private String	medIntNotNull;
	private String	signedDflt;
	private String	tinyIntDflt;
	private String	smintNotNull;
	private String	dec72NotNull;	
	private String	num72Dflt;
	private String 	timeNotNull;
	private String	dateNotNull;
	private String	tstampNotNull;
	private String 	dateTstampNotNull;
	private String	varcharNotNull;
	private String	charNotNull;
	private String	blobCol;
	private String	clobCol;
	private String	booleanNotNull;
	private String  boolNotNull;
	private String	bitNotNull;
	private String	bigIntDflt;
	private String	int8Dflt;
	private String	identityNotNull;
	
	public String getIntDflt() {
		return intDflt;
	}
	public String getMedIntNotNull() {
		return medIntNotNull;
	}
	public String getSignedDflt() {
		return signedDflt;
	}
	public String getTinyIntDflt() {
		return tinyIntDflt;
	}
	public String getSmintNotNull() {
		return smintNotNull;
	}
	public String getDec72NotNull() {
		return dec72NotNull;
	}
	public String getNum72Dflt() {
		return num72Dflt;
	}
	public String getTimeNotNull() {
		return timeNotNull;
	}
	public String getDateNotNull() {
		return dateNotNull;
	}
	public String getTstampNotNull() {
		return tstampNotNull;
	}
	public String getDateTstampNotNull() {
		return dateTstampNotNull;
	}
	public String getVarcharNotNull() {
		return varcharNotNull;
	}
	public String getCharNotNull() {
		return charNotNull;
	}
	public String getBlobCol() {
		return blobCol;
	}
	public String getClobCol() {
		return clobCol;
	}
	public String getBooleanNotNull() {
		return booleanNotNull;
	}
	public String getBoolNotNull() {
		return boolNotNull;
	}
	public String getBitNotNull() {
		return bitNotNull;
	}
	public String getBigIntDflt() {
		return bigIntDflt;
	}
	public String getInt8Dflt() {
		return int8Dflt;
	}
	public String getIdentityNotNull() {
		return identityNotNull;
	}

	
	
}
