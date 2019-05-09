package com.bobman159.rundml.h2.select.steps;

import java.util.List;

import com.bobman159.rundml.jdbc.select.ITableRow;

	/**
	 * Fourth and final builder step in the Select build steps.
	 *
	 */
public interface H2SelectFetchStep {
	/**
	 * Executes a SELCT statement and returns the results
	 * @return results of the select or empty is no rows returned
	 */
	List<ITableRow> fetch();    

}
