package vitaTracker.dataHandler.messungen;

import java.util.Date;

public class MessWert 
{
	private Date date;
	private double[] value;
	
	public MessWert(Date date, double value1, double value2)
	{
		
		this.date=date;
		this.value[0]=value1;
		this.value[1]=value2;
		
	}
	
}
