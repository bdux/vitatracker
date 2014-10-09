package vitaTracker.dataHandler.messungen;

import java.sql.Date;

public class Messung
{
	private Date zp;
	private double wert;
	
	public Messung()
	{
		
		this.zp = new Date(System.currentTimeMillis());
		this.wert = 0.0;
		
	}
	
	public Messung(double value)
	{
		this();
		wert = value;
	}
	
	public Messung (Date date, double value)
	{
		this();
		//this.zp = TODO
		wert = value;
		
	}
	
}
