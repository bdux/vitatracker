/**
 * 
 */
package vitaTracker;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * @deprecated
 * @author Alfa
 * 
 * work in Progress...
 *
 */
public class SQLiteDBController {
    
//    private static final SQLiteDBController dbcontroller = SQLiteDBController();
    private static Connection connection;
    private static final String DB_PATH = "vitatracker.db";
    private LinkedList<Messung> readLiList;
    
      
    
    
    public SQLiteDBController(LinkedList<Messung> readList)
    {
    	this.readLiList = readList;
    	try
		{
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e)
		{}
    	
    }
    
//    public static SQLiteDBController getInstance(){
//        return dbcontroller;
//    }
    
    public void initDBConnection() {
        try {
            if (connection != null)
                return;
            System.out.println("Creating Connection to Database...");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            if (!connection.isClosed())
               System.out.println("...Connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                            System.out.println("Connection to Database closed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addToDB() 
    {

    	try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("DROP TABLE IF EXISTS messungen_2;");
//            stmt.executeUpdate("CREATE TABLE messungen_2 (id, val1, val2, messArtStr, messUnitStr, date, numericdate, mID);");
//            stmt.execute("INSERT INTO messungen_2 (val1, val2, messArtStr, messUnitStr, date, numericdate, mID) VALUES ('100.0', 'Paul der Penner', '2001-05-06', '1234', '5.67', '555', '777')");
            
            for (Messung m : readLiList)
            {
	            PreparedStatement ps = connection
	                    .prepareStatement("INSERT INTO messungen (val1,val2,messArtStr,messUnitStr,date,numericdate,mID) VALUES (?, ?, ?, ?, ?, ?, ?);");
	
	            
	            ps.setDouble(1, m.getValue1());
	            ps.setDouble(2, m.getValue2());
	            ps.setString(3, m.getStrMessArt());
	            ps.setString(4, m.getMessUnit());
	            ps.setLong(5, m.getNumericDate());
	            ps.setString(6, m.getDate().toString());
	            ps.setInt(7, m.getmID());
	            
	            ps.addBatch();
	            
	            connection.setAutoCommit(false);
	            ps.executeBatch();
	            connection.setAutoCommit(true);
            }
            

//            ResultSet rs = stmt.executeQuery("SELECT * FROM messungen;");
//            while (rs.next()) 
//            {
//                System.out.println("Autor = " + rs.getString("author"));
//                System.out.println("Titel = " + rs.getString("title"));
//                System.out.println("Erscheinungsdatum = "
//                        + rs.getDate("publication"));
//                System.out.println("Seiten = " + rs.getInt("pages"));
//                System.out.println("Preis = " + rs.getDouble("price"));
//            }
//            rs.close();
            connection.close();
        	} catch (SQLException e) 
	        {
	            System.err.println("Couldn't handle DB-Query");
	            e.printStackTrace();
	        }

    }
    
    public boolean readFromDB()
    {
    	boolean retValue = false;
    	
    	try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM messungen;");
			
			rs.close();
			
		} catch (Exception e)
		{
			// TODO: handle exception
		}
    	
    
    	return retValue;
    }

//    public static void main(String[] args) {
//        SQLiteDBController dbc = SQLiteDBController.getInstance();
//        dbc.initDBConnection();
//        dbc.handleDB();
//    }
}