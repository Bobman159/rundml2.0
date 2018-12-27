package net.bobs.own.db.rundml.factory;

import java.util.List;

import net.bobs.own.db.rundml.exception.RunDMLException;
import net.bobs.own.db.rundml.mapper.AbstractTableMapper;
import net.bobs.own.db.rundml.mapper.ITable;

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
