package com.bobman159.rundml.sql.select.steps;

import java.util.List;

	/**
	 * Third builder step in the Select.
	 * Next Step available: FetchStep.
	 *
	 */
public interface H2FetchStep {
	
    	List<String> fetch();    

}
