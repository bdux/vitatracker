package vitaTracker.chartEngine;

import java.util.Arrays;

import org.jfree.data.xy.*;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;

public class JFreeChartsExperiment
{

		
	
	
	public static void main(String[] args)
	{
		double [][] A = {{1,2,5},{3,4,0}};
		double [][] B = {{4,8,5},{7,1,5}};

		DefaultXYDataset dataset1 = new DefaultXYDataset();
		dataset1.addSeries("xy", A);
//		DefaultXYDataset dataset2 = new DefaultXYDataset();
//		dataset2.addSeries("xy", B);

		XYSplineRenderer spline = new XYSplineRenderer();
		NumberAxis xax = new NumberAxis("x");
		NumberAxis yax = new NumberAxis("y");
		
		XYPlot plot1 = new XYPlot(dataset1, xax, yax, spline);
//		XYPlot plot2 = new XYPlot(dataset2, xax, yax, spline);
		
		JFreeChart chart1 = new JFreeChart(plot1);
//		JFreeChart chart2 = new JFreeChart("Neuer Plot", plot2);
		
		ApplicationFrame splineframe = new ApplicationFrame("Spline");
		
		ChartPanel chartPanel1 = new ChartPanel(chart1);
//		ChartPanel chartPanel2 = new ChartPanel(chart2);
		splineframe.setContentPane(chartPanel1);
//		splineframe.setContentPane(chartPanel2);
		splineframe.pack();
		splineframe.setVisible(true);
		
	}

}
