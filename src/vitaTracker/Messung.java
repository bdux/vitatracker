package vitaTracker;

import java.util.Date;


/**
 * 
 * Klasse Messung, dient zum erstellen von Objekten des Typs Messung, zur weiteren Verarbeitung 
 * in der MainWindow Klasse.
 * 
 * @author Benjamin Dux
 * 
 *
 */
public class Messung
{
	private int mID = -1;
	private double value1 = 0;
	private double value2 = 0;
	private Date zp;
	private String messArtStr, messUnit;
	private long numericDate = 0;
	public static enum messArtEnum {blutDruck, gewicht, blutZucker};
	
	private Messung() {}
		
	/**
	 * Konstruktor der zum Bauen eines Messungsobjekts.
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
			this.numericDate = date.getTime();
			this.value1 = value1;
			this.value2 = value2;
			this.messArtStr = MainWindow.M_STR_BLUTDRUCK;
			this.messUnit = unit;
			this.mID = MainWindow.BLUTDRUCK;
			System.out.println("Messung erstellt: " + zp + "," + numericDate + "," + value1 + "," + value2 + "," + messArtStr + "," + messUnit + "," + mID);
		}
		
		if (art == messArtEnum.blutZucker)
		{
			this.zp = date;
			this.numericDate = date.getTime();
			this.value1 = value1;
			this.value2 = value2;
			this.messArtStr = MainWindow.M_STR_BLUTZUCKER;
			this.messUnit = unit;
			this.mID = MainWindow.BLUTZUCKER;
			System.out.println("Messung erstellt: " + zp + "," + numericDate + "," + value1 + "," + value2 + "," + messArtStr + "," + messUnit + "," + mID);
		}
		
		
		if (art == messArtEnum.gewicht)
		{
			this.zp = date;
			this.numericDate = date.getTime();
			this.value1 = value1;
			this.value2 = value2;
			this.messArtStr = MainWindow.M_STR_GEWICHT;
			this.messUnit = unit;
			this.mID = MainWindow.GEWICHT;
			System.out.println("Messung erstellt: " + zp + "," + numericDate + "," + value1 + "," + value2 + "," + messArtStr + "," + messUnit + "," + mID);
		}
	}
	
	
	/**
	 * 
	 * @return gibt den Zeitpunkt der Messung zurück
	 */
	public Date getDate()
	{
		return zp;
	}
	
	/**
	 * gibt den Wert der Messung zurück. Zu beachten ist hier, dass man zwar einen int übergibt, die Messwerte
	 * aber als double vorliegen - und zwar in einem double[]. val ist hier der index des zu holenden Messwerts.
	 * wert[1] wird nur im Falle einer Blutdruckmessung gebraucht.
	 * @param val double Messwert am index val.
	 * @return
	 */
	public double getValue1()
	{
		return this.value1;
	}
	
	public double getValue2()
	{
		return this.value2;
	}
	
	/**
	 * 
	 * @return gibt den Klarnamen der Messart zurück.
	 */
	public String getStrMessArt()
	{
		return messArtStr;
	}

	/**
	 * 
	 * @return gibt den Messungsschlüssel der Messung zurück. Siehe MainWindow.
	 */
	public int getmID()
	{
		return mID;
	}
	
	public long getNumericDate()
	{
		return numericDate;
	}

	/**
	 * 
	 * @return gibt die Einheit der Messung zurück.
	 */
	public String getMessUnit()
	{
		return messUnit;
	}
	
	public String getAllData()
	{
		String s = "Messzeitpunkt:" + this.zp + ", LongDatum: " + this.numericDate  + ", Wert1: " + this.value1 +
					", Wert2: " + this.value2 + ", Str messart: " + this.messArtStr + ", Messeinheit: " + this.messUnit + 
					", MessArtID: " + this.mID;
		System.out.println(s);
		
		return s;
	}

}