package com.bobman159.rundml.factory;

import java.util.List;

import com.bobman159.rundml.exception.RunDMLException;
import com.bobman159.rundml.mapper.AbstractTableMapper;
import com.bobman159.rundml.mapper.ITable;

public class RunDMLRequestFactory {
	
	public static List<ITable> makeSelectRequest(AbstractTableMapper mapper) throws RunDMLException {
		return mapper.select();
	}
	
	public static void makeInsertRequest(AbstractTableMapper mapper,ITable table) throws RunDMLException {
		mapper.insert(table);
	}
	
	public static void makeUpdateRequest(AbstractTableMapper mapper, ITable table) throws RunDMLException {
		mapper.update(table);
	}
	
	public static void makeDeleteRequest(AbstractTableMapper mapper, ITable table) throws RunDMLException {
		mapper.delete(table);
	}
	
}
