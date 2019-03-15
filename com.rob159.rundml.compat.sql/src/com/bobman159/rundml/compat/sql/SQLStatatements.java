package com.bobman159.rundml.compat.sql;

import com.bobman159.rundml.compat.builder.select.SelectStatement;

public class SQLStatatements {

	@SuppressWarnings("rawtypes")
	public static SelectStatement select() {
		return new SelectStatement();
	}
}
