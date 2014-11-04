package vitaTracker.Util;



import org.jfree.chart.axis.DateAxis;
import org.jfree.data.xy.*;

import vitaTracker.MainWindow;
import vitaTracker.Messung;

/**
 *
 * @author 
 */
public class DataSet {

	
	private double[] systolicvalues;
	private double[] diastolicValues;
	private double[] glucoValues;
	
	private double[] weightValues;
	
	private long[] bpDates;
	private long[] glucoDates;
	private long[] weightDates;
	private Messung m;
	
	private int bpCount = 0;
	private int glucoCount = 0;
	private int weightCount = 0;
	
	
	private void readDataIntoArrays(Object[][] in)
	{
		for (int i = 0; i<in.length;i++)
		{
			Object o = in[i];
			m = (Messung)o;
			if (m.getmID() == MainWindow.BLUTDRUCK)
			{
				bpCount++;
			}
			else if (m.getmID() == MainWindow.BLUTZUCKER)
			{
				glucoCount++;
			}
			else if (m.getmID() == MainWindow.GEWICHT)
			{
				weightCount++;
			}
			
		}

		systolicvalues = new double[bpCount];
		diastolicValues = new double[bpCount];
		glucoValues = new double[glucoCount];
		weightValues = new double[weightCount];
		bpDates = new long[in.length];
		glucoDates = new long[in.length];
		weightDates = new long[in.length];
		
		
		
		for (int counter = 0;counter<in.length; counter++)
		{
			Object o = in[counter];
			m = (Messung)o;
			
			switch (m.getmID())
			{
			case MainWindow.BLUTDRUCK:
				systolicvalues[bpCount-bpCount] = m.getValue1();
				diastolicValues[bpCount-bpCount] = m.getValue2();
				bpDates[bpCount-bpCount--] = m.getNumericDate();
				
				break;
			
			case MainWindow.BLUTZUCKER:
				glucoValues[counter] = m.getValue1();
				glucoDates[counter] = m.getNumericDate();
				break;
			
			case MainWindow.GEWICHT:
				glucoValues[counter] = m.getValue1();
				weightDates[counter] = m.getNumericDate();
				break;

			default:
				break;
			}
		}
	}
	
    public static DefaultXYDataset dataset(double[][] A)
    {
        DefaultXYDataset dataset = new DefaultXYDataset();
        
        dataset.addSeries("xy", A); // A wird unter dem Namen xy abgespeichert
        return dataset;
    }
    
    public static XYSeriesCollection dataset(Object[][] input)
    {
    	XYSeriesCollection dataset = new XYSeriesCollection();
    	DateAxis dA = new DateAxis();
    	XYSeries dataseries = new XYSeries("Daten");
    	
    	for (int i = 1; i<input.length;i++)
    	{
    		
    	}
    	
    	return dataset;
    }
    
    public static XYSeriesCollection dataset(double[] x,double[] y)
    {
        // Bezeichung Punkte1 erscheint in der Legende
        XYSeries series1 = new XYSeries("Punkte1");
        for(int i=0;i<x.length/2;i++)
        series1.add(x[i], y[i]);
        
        // Bezeichung Punkte2 erscheint in der Legende
        XYSeries series2 = new XYSeries("Punkte2");
        for(int i=x.length/2;i<x.length;i++)
        series2.add(x[i], y[i]);

        // Zusammenfassen der beiden Serien
        XYSeriesCollection dataset2 = new XYSeriesCollection();
        dataset2.addSeries(series1);
        dataset2.addSeries(series2);
        return dataset2;
    }
}
