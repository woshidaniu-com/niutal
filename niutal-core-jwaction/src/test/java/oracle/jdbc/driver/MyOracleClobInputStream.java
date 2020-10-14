package oracle.jdbc.driver;

import java.sql.SQLException;

import oracle.sql.CLOB;

public class MyOracleClobInputStream extends OracleClobInputStream {

	public MyOracleClobInputStream(CLOB clob) throws SQLException {
		super(clob);
	}
	
	public MyOracleClobInputStream(CLOB clob, int offset) throws SQLException {
		super(clob, offset);
	}
	
	public MyOracleClobInputStream(CLOB clob, int offset, long arg2) throws SQLException {
		super(clob, offset, arg2);
	}

}
