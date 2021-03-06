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
	 * @param value1 double; Erster Wert, bei allen Messungen au�er Blutdruck das einzige zu setzende Me�feld
	 * @param value2 double; nur f�r den diastolischen Wert verwendet
	 * @param unit	String; Einheit der Messung
	 * @param art	Enum; Art der Messung
	 */
	public Messung (Date date, double value1, double value2, String unit, messArtEnum art)
	{
		this(); //eigentlich �berfl�ssig... 
		if (art == messArtEnum.blutDruck)
		{
			this.zp = date;
			this.numericDate = date.getTime();
			this.value1 = value1;
			this.value2 = value2;
			this.messArtStr = MainWindow.M_STR_BLUTDRUCK;
			this.messUnit = unit;
			this.mID = MainWindow.BLUTDRUCK;
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
		}
	}
	
	
	/**
	 * 
	 * @return gibt den Zeitpunkt der Messung zur�ck
	 */
	public Date getDate()
	{
		return zp;
	}
	
	/**
	 * gibt den Wert der Messung zur�ck. Zu beachten ist hier, dass man zwar einen int �bergibt, die Messwerte
	 * aber als double vorliegen
	 * 
	 * @return double value1 Messwert. 
	 */
	public double getValue1()
	{
		return this.value1;
	}
	/**
	 * Wird nur f�r Blutdruck gebraucht - diastolischer Wert.
	 * @return value2 double Wert.
	 */
	public double getValue2()
	{
		return this.value2;
	}
	
	/**
	 * 
	 * @return gibt den Klarnamen der Messart zur�ck.
	 */
	public String getStrMessArt()
	{
		return messArtStr;
	}

	/**
	 * 
	 * @return gibt den Messungsschl�ssel der Messung zur�ck. Siehe MainWindow.
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
	 * @return gibt die Einheit der Messung zur�ck.
	 */
	public String getMessUnit()
	{
		return messUnit;
	}
	
	/**
	 * Hilfsklasse um alle Werte aus dem Messungsobjekt auf der Konsole auszugeben
	 * @return einen String, der alle Werte aus diesem Objekt beinhaltet.
	 */
	public String getAllData()
	{
		String s = "Messzeitpunkt:" + this.zp + ", LongDatum: " + this.numericDate  + ", Wert1: " + this.value1 +
					", Wert2: " + this.value2 + ", Str messart: " + this.messArtStr + ", Messeinheit: " + this.messUnit + 
					", MessArtID: " + this.mID;
		System.out.println(s);
		
		return s;
	}

}