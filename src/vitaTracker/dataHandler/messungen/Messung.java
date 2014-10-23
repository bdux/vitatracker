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
	public static enum messArtEnum {blutDruck, gewicht, blutZucker};
	private String messArtStr = null;
	
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
	
	public Messung (Date date, double value1, double value2, messArtEnum art)
	{
		this();
		if (art == messArtEnum.blutDruck)
		{
		this.zp = date;
		this.wert[0] = value1;
		this.wert[1] = value2;
		this.messArtStr = "Blutdruck";
		}
		
		if (art == messArtEnum.blutZucker)
		{
		this.zp = date;
		this.wert[0] = value1;
		this.wert[1] = 0;
		this.messArtStr = "Glucose";
		}
		
		
		if (art == messArtEnum.gewicht)
		{
		this.zp = date;
		this.wert[0] = value1;
		this.wert[1] = 0;
		this.messArtStr = "Gewicht";
		}
		
		
		
		
		
	}
	
	public Date getDate()
	{
		return zp;
	}
	
	public double getValues(int val1)
	{
		return wert[val1];
	}
	
	public String getStrMessArt()
	{
		
		return messArtStr;
		
	}
	
	
}
