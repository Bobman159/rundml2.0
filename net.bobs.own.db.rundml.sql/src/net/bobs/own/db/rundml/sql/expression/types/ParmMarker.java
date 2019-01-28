package net.bobs.own.db.rundml.sql.expression.types;

public class ParmMarker implements IExpression, IMathOperations, IStringOperations {

	private Object value;
	private int jdbcType;
	
	/**
	 * Creates a parameter marker expression expression type.
	 * A "?" will be generated for each parameter marker expression type.
	 * @param jdbcType - the JDBC Data Type to be used when binding the parameter
	 * marker.
	 * @param value - the data value for the parameter marker
	 * @see java.sql.Types
	 */
	public ParmMarker(int jdbcType,Object value) {
		this.jdbcType = jdbcType;
		this.value = value;
	}
	
	/**
	 * Return the JDBC data type for this parameter marker.
	 * @return - the JDBC data type
	 * @see java.sql.Types
	 */
	public int getParmType() {
		return jdbcType;
	}
	
	/**
	 * Returns the value this parameter marker.
	 * @return - the value as a string
	 */
	public String getValue() {
		return value.toString();
	}
	
	public void bind() {
		//TODO Implement JDBC bind logic here
	}
	
	/**
	 * @see net.bobs.own.db.rundml.sql.expression.types.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		String expr = "";
		expr = " ? ";
		return expr;
	}

}
