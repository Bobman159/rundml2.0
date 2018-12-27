package net.bobs.own.db.rundml.mapper;

import java.util.ArrayList;
import java.util.List;

//import net.bobs.own.db.h2.pool.H2Database;
//import net.bobs.own.db.h2.pool.IH2ConnectionPool;
import net.bobs.own.db.rundml.exception.RunDMLException;
import net.bobs.own.ezmenu.rundml.listener.ITableUpdatedListener;

public abstract  class AbstractTableMapper {

	@SuppressWarnings("unused")
//	private IH2ConnectionPool pool = null;
//	@SuppressWarnings("unused")
//	protected H2Database db = null;
	
	private ArrayList<ITableUpdatedListener> updatedListener = null;
	
//	public AbstractTableMapper(H2Database database) {
		
//		this.db = database;
//		updatedListener = new ArrayList<ITableUpdatedListener>();
		
//	}
	
	public void addTableUpdatedListener(ITableUpdatedListener listener) {
		updatedListener.add(listener);
	}
	
	public void removeTableUpdatedListener(ITableUpdatedListener listener) {
		updatedListener.remove(listener);
	}
	
	public void fireTableUpdated() {
		for (ITableUpdatedListener listener : updatedListener) {
			listener.tableUpdated();
		}
	}
	
	abstract public List<ITable> select() throws RunDMLException;
	abstract public List<ITable> selectById(int id) throws RunDMLException;	
	abstract public void insert(ITable table) throws RunDMLException;
	abstract public void update(ITable table) throws RunDMLException;
	abstract public void delete(ITable table) throws RunDMLException;
	
}
