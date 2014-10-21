package vitaTracker.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Globals
{

	 // Privater Standardkonstruktor.
	 // Alle Methoden dieser Klasse sind statisch. Durch die Deklaration eines eigenen
	 // Standardkonstruktors wird verhindert, dass Java einen Standardkonstruktor erstellt. 
	 // Die �nderung des Zugriffsmodifizierers von 'public' in 'private' verhindert, dass
	 // eine Instanz dieser Klasse erstellt werden kann.
	
	 private Globals() {}	 
	 
	 
	 public static int getNextKey()
	 {
		 int retValue = 0;
		 
		 String SQL = "SELECT MAX(PRIMARYKEY) FROM POSTLEITZAHLEN";
		 Object obj = DBConnection.executeScalar(SQL);
		 if (obj != null) retValue = Integer.parseInt(obj.toString());
		 	 
		 
		 return ++retValue;
	 }
	 
	 public static String quote(String value)
	 {
		 return "'" + value + "'";
	 }
	 
	 public static boolean istPLZOrtVorhanden(String PLZ, String Ort)
	 {
		 
		 String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN ";
		 SQL += "WHERE PLZ = " + Globals.quote(PLZ);
		 SQL += " AND ORT = " + Globals.quote(Ort);
		 
		 Object obj = DBConnection.executeScalar(SQL);
		 
		 return Long.parseLong(obj.toString()) > 0;
		 
	 }
	 
	 public static boolean istPLZOrtVorhandenPrepared(PreparedStatement prepStatement, String PLZ, String Ort)
		{
			
			boolean retValue = false;	
			
			try
	      {
		      prepStatement.setString(1, PLZ);
		      prepStatement.setString(2, Ort);
		      
		      ResultSet rSet = prepStatement.executeQuery();
			  rSet.next();
			  Object obj = rSet.getObject(1);
			  rSet.close();
			  retValue = Long.parseLong(obj.toString()) > 0;
	      }
	      catch (Exception ex)
	      {
	      	JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der Datei " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
	      }
				
		  return retValue;
			
		}
	 

		
		public static PreparedStatement preparePLZOrtExists()
		{
			String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN";
			SQL += " WHERE PLZ = ?";
			SQL += " AND ORT =  ?"; 
			
			return DBConnection.prepareStatement(SQL);
			
		}
		
		// Die SQL-Anweisungen, die mittels execute(), executeQuery() oder executeUpdate() an die Datenbank gesendet werden, 
	    // haben bis zur Ausf�hrung im Datenbanksystem einige Umwandlungen vor sich. 
		// Zuerst m�ssen sie auf syntaktische Korrektheit getestet werden. Dann werden sie in einen internen Ausf�hrungsplan
		// der Datenbank �bersetzt und mit anderen Transaktionen optimal verzahnt. 
		// Der Aufwand f�r jede Anweisung ist messbar. Deutlich besser w�re es jedoch, eine Art Vor�bersetzung f�r SQL-Anweisungen zu nutzen.
		// Diese Vor�bersetzung ist eine Eigenschaft, die JDBC unterst�tzt und die sich Prepared Statements nennt. 
		// Vorbereitet (engl. prepared) deshalb, weil die Anweisungen in einem ersten Schritt zur Datenbank geschickt und dort in ein internes 
		// Format umgesetzt werden. Sp�ter verweist ein Programm auf diese vor�bersetzten Anweisungen, und die Datenbank kann sie schnell ausf�hren, 
		// da sie in einem optimalen Format vorliegen. Ein Geschwindigkeitsvorteil macht sich immer dann besonders bemerkbar, wenn Schleifen �nderungen
		// an Tabellenspalten vornehmen. Dies kann durch die vorbereiteten Anweisungen schneller geschehen.

		public static PreparedStatement prepareInsertPLZ()
		{
			
			String SQL = "INSERT INTO POSTLEITZAHLEN ";
			SQL += "(PRIMARYKEY, PLZ, ORT) ";
			SQL += "VALUES (?, ?, ?)";
			
			return DBConnection.prepareStatement(SQL);
			
		}
		
		public static boolean insertPLZPrepared(PreparedStatement prepStatement, int PrimaryKey, String PLZ, String Ort)
		{
			boolean retValue = false;
			
			try
			{
				prepStatement.setInt(1, PrimaryKey);
				prepStatement.setString(2, PLZ);
				prepStatement.setString(3, Ort);
				
				prepStatement.executeUpdate();
				retValue = true;
				
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der Datei " + ex.getMessage(), 
						                      "E/A Fehler", JOptionPane.ERROR_MESSAGE);
			}

			return retValue;
		}
		
		public static PreparedStatement prepareUpdatePLZ()
		{
			
			String SQL = "UPDATE POSTLEITZAHLEN ";
			SQL += "SET PLZ = ?, ORT = ? ";
			SQL += "WHERE PRIMARYKEY = ?";
			
			return DBConnection.prepareStatement(SQL);
			
		}
		
		public static boolean updatePLZPrepared(PreparedStatement prepStatement, int PrimaryKey, String PLZ, String Ort)
		{
			boolean retValue = false;
			
			try
			{
				prepStatement.setString(1, PLZ);
				prepStatement.setString(2, Ort);
				prepStatement.setInt(3, PrimaryKey);
				
				prepStatement.executeUpdate();
				retValue = true;
				
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der Datei " + ex.getMessage(), 
						                      "E/A Fehler", JOptionPane.ERROR_MESSAGE);
			}

			return retValue;
		}
		
}
