package vitaTracker;

import java.util.Date;

public class Messung
{
	private Date zp;
	private double[] wert = {0,0};
	private String messArtStr;
	private String messUnit;
	public static enum messArtEnum {blutDruck, gewicht, blutZucker};
	private int mID = -1;
	
	private Messung() {}
		
	/**
	 * Konstruktor der zum Bauen einer Messung genutzt werden sollte!
	 * @param date Date; Zeitpunkt der Messung
	 * @param value1 double; Erster Wert, bei allen Messungen außer Blutdruck das einzige zu setzende Meßfeld
	 * @param value2 double; nur für den diastolischen Wert verwendet
	 * @param unit	String; Einheit der Messung
	 * @param art	Enum; Art der Messung
	 */
	public Messung (Date date, double value1, double value2, String unit, messArtEnum art)
	{
		this(); //eigentlich überflüssig... 
		if (art == messArtEnum.blutDruck)
		{
			this.zp = date;
			this.wert[0] = value1;
			this.wert[1] = value2;
			this.messArtStr = MainWindow.M_STR_BLUTDRUCK;
			this.messUnit = unit;
			this.mID = MainWindow.BLUTDRUCK;
		}
		
		if (art == messArtEnum.blutZucker)
		{
			this.zp = date;
			this.wert[0] = value1;
			this.wert[1] = 0;
			this.messArtStr = MainWindow.M_STR_BLUTZUCKER;
			this.messUnit = unit;
			this.mID = MainWindow.BLUTZUCKER;
		}
		
		
		if (art == messArtEnum.gewicht)
		{
			this.zp = date;
			this.wert[0] = value1;
			this.wert[1] = 0;
			this.messArtStr = MainWindow.M_STR_GEWICHT;
			this.messUnit = unit;
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

	public String getMessUnit()
	{
		return messUnit;
	}
	
}