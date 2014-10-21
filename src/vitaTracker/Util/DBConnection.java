package vitaTracker.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBConnection
{
	
	private static Connection dbConn;
	private static String connectionString;

	public static boolean connectToDatabase
		(String classForName, String connectionString, 
		String userID, String passWord)
	{
		boolean retValue = false;
		
		try
		{
			Class.forName(classForName).newInstance();
			
			DBConnection.dbConn = 
				DriverManager.getConnection(
					connectionString, userID, passWord);
			
			DBConnection.connectionString = connectionString;
			
			retValue = true;
			
		} 
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE				
			);
			
			DBConnection.connectionString = null;
			DBConnection.dbConn = null;
		} 
	
		
		
		
		
		return retValue;
	}
	
	public static boolean closeConnection()
	{
		boolean retValue = false;
		
		if( DBConnection.dbConn == null ) return true; 
		
		try
		{
			DBConnection.dbConn.close();
			retValue = true;
		}
		catch(Exception e){}
		
		return retValue;
	}
	
	public static int executeNonQuery(String sql)
	{
		int retValue = 0;
		Statement stmt;
		
		if(DBConnection.dbConn == null) return retValue;
		
		try
		{
			stmt = DBConnection.dbConn.createStatement();
			retValue = stmt.executeUpdate(sql);
			stmt.close();
		} 
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE				
			);
		}
	
		return retValue;
	}
	
	public static Object executeScalar(String sql)
	{
		Object retValue = null;
		Statement stmt;
		
		if(DBConnection.dbConn == null) return retValue;
		
		try
		{
			stmt = DBConnection.dbConn.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			rset.next();
			retValue = rset.getObject(1);
			rset.close();
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE				
			);
		}
		
		return retValue;
		
	}
	
	public static void beginTransaction()
	{
		if (DBConnection.dbConn == null)
			return;
		try
		{
			DBConnection.dbConn.setAutoCommit(false);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}	
	
	public static PreparedStatement prepareStatement(String SQL)
	{
		PreparedStatement prepStmt = null;
		
		if (DBConnection.dbConn == null) return prepStmt;
		
		try
		{
			prepStmt = DBConnection.dbConn.prepareStatement(SQL);

		}
		catch (Exception ex) {}
		
		return prepStmt;
	}
	
	
	
	public static void commitTransaction()
	{
		if (DBConnection.dbConn == null)
			return;
		
		try
		{
			// Keine Transaktionsschleife ge�ffnet.
			//
			if(DBConnection.dbConn.getAutoCommit()) return;
			
			DBConnection.dbConn.commit();
			DBConnection.dbConn.setAutoCommit(true);
		} 
		catch (Exception ex)
		{}
	}
	
	public static void rollbackTransaction()
	{
		if(DBConnection.dbConn == null) return;
		
		try
		{
			// Keine Transaktionsschleife ge�ffnet.
			//
			if(DBConnection.dbConn.getAutoCommit()) return;

			DBConnection.dbConn.rollback();
			DBConnection.dbConn.setAutoCommit(true);
		} 
		catch (Exception ex)
		{}
	}

	public static ResultSet executeQuery(String sql)
	{
		
		ResultSet rSet = null;
		Statement stmt = null;
		
		if (DBConnection.dbConn == null)
			return rSet;
		
		
		
		try
		{
			stmt = DBConnection.dbConn.createStatement();
			rSet = stmt.executeQuery(sql);
			
			
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE				
			);
		}
		
		return rSet;
	}
	
	
}
