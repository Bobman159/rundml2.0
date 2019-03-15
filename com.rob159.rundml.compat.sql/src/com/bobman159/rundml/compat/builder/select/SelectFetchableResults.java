package com.bobman159.rundml.compat.builder.select;

import java.util.ArrayList;
import java.util.List;

import com.bobman159.rundml.compat.model.SQLStatementModel;

//public abstract class SelectFetchableResults<S> implements IFetchableResults<S> {
public abstract class SelectFetchableResults<S> implements IFetchableResults {

	protected SQLStatementModel model = new SQLStatementModel();
	@Override
	public List<String> fetch() {
		System.out.println("SelectFetchableResults fetch method");
		return new ArrayList<String>();
		
	}
}
