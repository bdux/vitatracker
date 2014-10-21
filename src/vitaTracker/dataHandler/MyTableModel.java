package vitaTracker.dataHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import vitaTracker.Util.DBConnection;



public class MyTableModel extends AbstractTableModel
{
	
	private int anzahlSpalten, anzahlZeilen;
	private ArrayList<String> ColumnNames;
	private Object[][] data;
	
	public MyTableModel(String sql)
	{
		ResultSet rSet = DBConnection.executeQuery(sql);
		
		// Lesen der Metadaten: Anzahl, Datentypen und Eigenschaften
		// der Spalten aus dem ResultSet.
		//
		ResultSetMetaData rsMetaData = getMetaData(rSet);
		
		// Anzahl der Spalten aus dem ResultSet ermitteln
		//
		anzahlSpalten = getColumnCount(rsMetaData);
		
		// Anzahl der Zeilen aus dem ResultSet ermitteln
		//
		anzahlZeilen = getRowCount(rSet);
		
		// �berschriften der Spalten aus den Metadaten erstellen
		//
		setHeader(rsMetaData);
		
		
		// Liest alle Datens�tze aus dem ResultSet in das 
		// zweidimensionale Objekt-Array 'data'
		//
		getData(rSet);
		
	}
	
	
	private void getData(ResultSet rSet)
	{
		data = new Object[anzahlZeilen][anzahlSpalten];
		
		try
		{
		
			for(int zeile = 1; zeile <= anzahlZeilen; zeile++)
			{
				rSet.next();
				
				for( int spalte = 1; spalte <= anzahlSpalten; spalte++ )
				{
					data[zeile -1][spalte - 1] = rSet.getObject(spalte);
				}
			}
		
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"getData: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE
			);
		}
		
	}

	private String getColumnLabel(ResultSetMetaData rsMD, int colIndex)
	{
		String colName = "";
		
		try
		{
			colName = rsMD.getColumnLabel( colIndex );
		} 
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"getColumnLabel: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE
			);
		}
		return colName;
	}
	
	private String getColumnName(ResultSetMetaData rsMD, int colIndex)
	{
		String colName = "";
		
		try
		{
			colName = rsMD.getColumnName( colIndex );
		} 
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"getColumnName: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE
			);
		}
		return colName;
	}
	
	private void setHeader(ResultSetMetaData rsMetaData)
	{
		ColumnNames = new ArrayList<String>();
		for( int i = 1; i <= anzahlSpalten; i++)
			ColumnNames.add(getColumnLabel(rsMetaData, i));
		
	}

	private ResultSetMetaData getMetaData( ResultSet rSet )
	{
		ResultSetMetaData rsMD = null;

		try
		{
			rsMD = rSet.getMetaData();
		} 
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"getMetaData: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE
			);
		}
		
		return rsMD;
	}

	
	private int getColumnCount( ResultSetMetaData rsMD )
	{
		int retValue = 0;
		
		try
		{
			retValue = rsMD.getColumnCount();
		} 
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"getColumnCount: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE
			);
		}
		
		return retValue;
	}

	private int getRowCount( ResultSet rs )
	{
		int retValue = 0;
		
		try
		{
			rs.last();
			retValue = rs.getRow();
			rs.beforeFirst();
		} 
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(
					null,
					"getRowCount: " + ex.getMessage(),
					"Fehler",
					JOptionPane.ERROR_MESSAGE
			);
		}
		
		return retValue;
	}
	
	@Override
	public String getColumnName(int colIndex)
	{
		return ColumnNames.get(colIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex)
	{
		return data[rowIndex][colIndex];
	}

	@Override
	public int getColumnCount()
	{
		return anzahlSpalten;
	}
	
	@Override
	public int getRowCount()
	{
		return anzahlZeilen;
	}
	
}
