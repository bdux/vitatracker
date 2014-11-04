package vitaTracker;



import org.jfree.chart.axis.DateAxis;
import org.jfree.data.xy.*;

import java.lang.Number;

/**
 *
 * @author 
 */
public class DataSet {
	
   
    public static XYSeriesCollection dataset(double[] x, long[] y, String graphname)
    {
    	XYSeriesCollection dataset = new XYSeriesCollection();
    	XYSeries dataseries = new XYSeries(graphname);
    	
    	
    	for (int i = 0; i<y.length;i++)
    	{
    		dataseries.add(x[i], y[i]);
    	}
    	
    	dataset.addSeries(dataseries);
    	return dataset;
    }
    
   
}
