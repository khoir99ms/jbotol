package bot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class openDB {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet result = null;
	private String hostname = "localhost";
	private String dbname = "jbotol";
	private String dbuser = "root";
	private String dbpass = "rahasia123";
	private String qString;

	public void openDB() throws Exception{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.connect = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbname+"?user="+dbuser+"&password="+dbpass);
			// statements allow to issue SQL queries to the database
			this.statement = this.connect.createStatement();
		} catch (Exception e) {
			throw e;
		} finally {
			this.close();
		}
	}
	
	public void save(String tableName, String qString, Object... args) throws SQLException{
		this.qString = "INSERT INTO "+ tableName +" "+ String.format(qString, args);
		this.result = this.statement.executeQuery(this.qString);
	}
	
	private void writeMetaData(ResultSet result) throws SQLException {
		// now get some metadata from the database
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + result.getMetaData().getTableName(1));
		for  (int i = 1; i<= result.getMetaData().getColumnCount(); i++){
			System.out.println("Column " +i  + " "+ result.getMetaData().getColumnName(i));
		}
		System.out.print("\n");
	}
	
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
	
	// you need to close all three to make sure
	private void close()  {
		try {
			if (this.result != null) this.result.close();
			if (this.statement != null) this.statement.close();
			if (this.connect != null) this.connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}