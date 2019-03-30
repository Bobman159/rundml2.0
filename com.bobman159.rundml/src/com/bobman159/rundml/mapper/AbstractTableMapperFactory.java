package com.bobman159.rundml.mapper;

import java.util.HashMap;

//import net.bobs.own.db.h2.db.H2AbstractDatabaseService;
//import net.bobs.own.db.h2.pool.H2Database;

	/**
	 * Simple factory class to create and manage <code>AbstractTableMapper</code> base classes.
	 * The factory can be used to control the number of mapper classes for an application.
	 * 
	 * @author Robert Anderson
	 *
	 */
public abstract class AbstractTableMapperFactory {

	protected static AbstractTableMapperFactory factory = null;
//	protected H2AbstractDatabaseService service = null;
	protected HashMap<String,AbstractTableMapper> tableMappers = null;
			
	public AbstractTableMapperFactory() {
		
		tableMappers = new HashMap<String,AbstractTableMapper>();
		
	}

	/**
	 * Locate an <code>AbstractTableMapper</code> object by name.
	 * @param mapName - name of the mapper to locate
	 * @return - the mapper object, null otherwise
	 */
	public AbstractTableMapper findMapper(String mapName) {
		
		AbstractTableMapper mapper = null;
		if (tableMappers.containsKey(mapName)) {
			mapper = tableMappers.get(mapName);
		} 
		
		return mapper;
		
	}
	
//	public abstract AbstractTableMapperFactory getInstance();
	public abstract AbstractTableMapper createMapper(String mapperName);

	//HOW DO I 
//	-	Create Mapper if it doesn't already exist
//	-	If mapper does exist, then use it
//		-	How do I know how to specify connection, userid etc,
//			when it is needed & NOT specify that info when it's not 
//			need?
//	public AbstractTableMapper makeMapper(IH2ConnectionPool pool,String mapperName) {
//		
//		return new AbstractTableMapper(service,pool);
//		
//	}
//	
//	public EzMenuProfileMapper makeProfileMapper(String poolName,String mapperName) {
//		
//		IH2ConnectionPool pool = H2ConnectionPoolFactory.findPool(poolName);
//		if (pool == null) {
//			pool = H2ConnectionPoolFactory.getInstance().makePool(H2ConnectionPoolFactory.PoolTypes.MYOWN, 
//					poolName, EZMENU_MYOWN_CONFIG);
//			
//		}
//		return new EzMenuProfileMapper(service,pool);
//	}

}
