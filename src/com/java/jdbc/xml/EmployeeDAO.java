package com.java.jdbc.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeDAO {
	
	private PrintWriter pw;
	private Connection connection;
	private PreparedStatement statement;
	private Statement statement1;
	//private int result;
	//private ResultSet rs;
	private static final String USER_NAME="jdbc";
	private static final String PASSWORD="jdbc";
	private static final String DB_URL="jdbc:oracle:thin:@172.22.22.200:1521:orcl";
	private static final String DRIVER="oracle.jdbc.driver.OracleDriver";
	
	public void configure(){
		try{
		Class.forName(DRIVER);
		System.out.println("Connecting to Database...");
		connection=DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		connection.setAutoCommit(false);
		
		//File file2=new File("C:/Documents and Settings/akash.gupta/Desktop/employee_error.txt");
		File file2=new File("src/employee_error.txt");
		pw=new PrintWriter(file2);
		
		String sql="create table employee_xml(ID Number(10), Data Long)";
		statement=connection.prepareStatement(sql);
		statement1=connection.createStatement();
		statement1.executeUpdate("Drop table employee_xml");
		statement.execute();
		
		File file1=new File("src/employee_xml.xml");
		
		FileInputStream fin =new FileInputStream(file1);
		sql="insert into employee_xml values(?,?)";
		statement=connection.prepareStatement(sql);
		statement.setInt(1, 1234);
		statement.setAsciiStream(2, fin,(int)file1.length());
		statement.execute();
		connection.commit();
		}
		catch(ClassNotFoundException |SQLException |FileNotFoundException exception){
			exception.printStackTrace(pw);
			pw.close();
		}
		finally{
			
		  try{
				if(statement!=null)
					statement.close();
				if(connection!=null)
					connection.close();
			 }catch(SQLException se){
				se.printStackTrace(pw);
				}
		}
	}
}