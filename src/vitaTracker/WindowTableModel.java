package vitaTracker;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class WindowTableModel extends AbstractTableModel
{
	private int		          	anzahlZeilen;
	private int		           	anzahlSpalten;
	private ArrayList<String>	ColumnNames;
	private Object[][]		  	data;
	
	public WindowTableModel(Object in[][])
	{
		anzahlSpalten = in[0].length;
		anzahlZeilen  = in.length;
		setHeader(in);
		
		this.data = getData(in);
		
	}
	
	private void setHeader(Object[][] in)
	{
		ColumnNames = new ArrayList<String>();
		for (int i = 0; i < anzahlSpalten; i++)
			ColumnNames.add( in[0][i].toString() );
	}
	
	protected Object[][] getData(Object in[][])
	{
		Object[][] retValue = new Object[anzahlZeilen][anzahlSpalten];
			
		for (int zeile = 1; zeile < anzahlZeilen; zeile++)
		{
			for (int spalte = 0; spalte < anzahlSpalten; spalte++)
					retValue[zeile-1][spalte] = in[zeile][spalte];
				
				
		}
		
		return retValue;
	}
 
	
	// Keine abstrakte Methode.
	// Muss manuell Überschrieben werden.	
	@Override
	public String getColumnName(int colIndex)
	{
		return ColumnNames.get(colIndex);
	}
	
	@Override
	public int getColumnCount()
	{
		return  anzahlSpalten;
	}

	@Override
	public int getRowCount()
	{
		return anzahlZeilen;
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex)
	{
		return data[rowIndex][colIndex];
	}
	
}