package vitaTracker.dataHandler.messungen;

import java.util.Date;

/**
 * 
 * @author ben
 *
 *
 */

public class Messung
{
	private Date zp;
	private double[] wert = {0,0};
	private enum messArt {blutDruck, gewicht, blutZucker};
	
	public Messung()
	{
		
		this.zp = new Date(System.currentTimeMillis());
//		this.wert[0] = 0.0;
		
	}
	
	public Messung (Date date, double value1, double value2)
	{
		this();
		this.zp = date;
		this.wert[0] = value1;
		this.wert[1] = value2;
		
	}
	
	public Messung (Date date, double value1)
	{
		this();
		this.zp = date;
		this.wert[0] = value1;
		this.wert[1] = 0;
		
	}
	
	public Date getDate()
	{
		return zp;
	}
	
	public double[] getValues()
	{
		return wert;
	}
	
	
}
