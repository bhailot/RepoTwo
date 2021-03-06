package com.wipro.servlet;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ReadPNG {

	 Connection con = null;
	 PreparedStatement stmt = null;
	 ResultSet rs = null;
	 byte[] b;
	 
	 private static final String SQL_PNG_IMAGE_SELECT =
		        " SELECT M.PNGFILE " + 
		        " FROM EBOOK_MASTER M " + 
		        " WHERE M.REGION_KEY = ? " +
		        " AND M.BOOK_KEY = ? " +
		        " AND M.PUB_SEQUENCE = ? " +
		        " AND M.SECTION_CODE = ? " +
		        " AND M.PAGENBR = ? " +
		        " AND M.PNGFILE IS NOT NULL ";
	 
	
	protected Connection getConnection()
	        throws SQLException
	    {
	       
	        try
	        {
	        	//@Sandeep - 8.01.2015
	           /* Hashtable<String, String> map = new Hashtable<String, String>() ;
	            map.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory") ;
	            map.put("java.naming.provider.url", "jnp://localhost:1099") ;
	            map.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces") ;
	        	*/
	        	Properties properties = new Properties();
	            
	            properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
	            properties.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
	            properties.put("java.naming.provider.url", "localhost:1099");
	        	
	        	InitialContext ctx = new InitialContext(properties);
	            DataSource ds =  (DataSource)ctx.lookup("java:comp/env/jdbc/ebook");

	            con = ds.getConnection();
	        }
	        catch (NamingException ne)
	        {
	           // logger.error("NamingException occurred in getConnection: " + ne.getMessage(), ne);
	        	ne.printStackTrace();
	            throw new SQLException("NamingException on getConnection");
	        }

	      
	        return con;
	    }
	
	void getBlob() throws ClassNotFoundException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//vip-ybcdrracdb05.corp.ybusa.net:1521/ybnet", "EBOOK_LOAD", "MB1YVZ2Q08WVTHJKE6ET-");
			
			stmt = conn.prepareStatement(SQL_PNG_IMAGE_SELECT);
			stmt.setInt(1, 8);
			stmt.setInt(2, 1620);
			stmt.setInt(3, 60);
			stmt.setInt(4, 501);
			stmt.setInt(5, 1);
			
			rs = stmt.executeQuery();
			
			if(rs.next()){
				
				//Getting Blob file form DB
				Blob blob = rs.getBlob("PNGFILE");
				int length = (int)blob.length();
				b = blob.getBytes(1, length);
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		ReadPNG readPng = new ReadPNG();
		//readPng.getConnection();
		readPng.getBlob();
		
	}

}
