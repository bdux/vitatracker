package vitaTracker;

import java.util.Date;

public class Messung
{
	private Date zp;
	private double[] wert = {0,0};
	private String messArtStr = null;
	public static enum messArtEnum {blutDruck, gewicht, blutZucker};
	private int mID = -1;
	
	public Messung()
	{
		
		this.zp = new Date(System.currentTimeMillis());
//			this.wert[0] = 0.0;
		
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
		this.mID = MainWindow.BLUTDRUCK;
		}
		
		if (art == messArtEnum.blutZucker)
		{
		this.zp = date;
		this.wert[0] = value1;
		this.wert[1] = 0;
		this.messArtStr = "Glucose";
		this.mID = MainWindow.BLUTZUCKER;
		}
		
		
		if (art == messArtEnum.gewicht)
		{
		this.zp = date;
		this.wert[0] = value1;
		this.wert[1] = 0;
		this.messArtStr = "Gewicht";
		this.mID = MainWindow.GEWICHT;
		}
		
				
	}
	
	public Date getDate()
	{
		return zp;
	}
	
	public double getValueAtIndex(int val)
	{
		return wert[val];
	}
	
	public String getStrMessArt()
	{
		
		return messArtStr;
		
	}

	public int getmID()
	{
		return mID;
	}


	
}