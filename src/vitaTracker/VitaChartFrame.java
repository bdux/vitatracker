package vitaTracker;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class VitaChartFrame extends JFrame implements WindowListener, MouseMotionListener
{
	
	private MainWindow owner;
	private int winX, winY, winHeight = 500, winWidth = 500;
	
	protected  ChartPanel chrtPanel;
	protected static JScrollPane imgScrllPane;
	
	private LinkedList<Messung> data;
//	private DataSet dataset;
//	private XYDataset bpSysDataset, bpDiaDataset, glucoDataSet, weightDataset;
	protected JFreeChart jChart;
	private XYPlot plot;
	
	protected double[] systolicvalues;
	protected double[] diastolicValues;
	protected double[] glucoValues;
	
	protected double[] weightValues;
	
	protected long[] bpDates;
	protected long[] glucoDates;
	protected long[] weightDates;
	private Messung m;
	
	private int bpCount = 0;
	private int glucoCount = 0;
	private int weightCount = 0;
	
	
	
	public VitaChartFrame(MainWindow owner, LinkedList<Messung> data)
	{
		this.owner = owner;
		this.data = data;
		
		this.winX = 0;
		this.winY = 0;
		
		this.winX = owner.getX() + owner.getWidth();
		this.winY = owner.getY();
		
		this.winHeight = owner.getHeight()-50;
		this.winWidth = owner.getWidth()-50;

		initializeComponents();
//		Show();
		
	}
		
	private void initializeComponents()
	{
		this.setTitle(MainWindow.DIAGRAM_WINDOW_TITLE);
		this.setBounds(winX, winY, winWidth, winHeight);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		this.setIconImage(owner.icon.getImage());
		this.setLayout(new BorderLayout());

		
		JPanel kopfZeile = new JPanel();
		kopfZeile.setLayout(new BorderLayout());
		
		this.add(kopfZeile, BorderLayout.PAGE_START);
		chrtPanel = new ChartPanel(jChart);
		this.add(chrtPanel, BorderLayout.CENTER);

		initChartData();
		drawChart();
	}
	

	/**
	 * 
	 */
	private void initChartData()
	{
		readDataIntoArrays(data);
	}
	
	/**
	 * 
	 */
	
		
	
	private TimeSeries createVitaSeries(double[] values, long[] dates,final String name) 
	{
        final TimeSeries timeSeries = new TimeSeries(name);
        
        for (int i = 0; i<values.length;i++) 
        {
        	timeSeries.addOrUpdate(new Second(new Date(dates[i])), values[i]);
        }
        return timeSeries;
	}
	
	private XYDataset createDataset() 
	{
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        tsc.addSeries(createVitaSeries(systolicvalues, bpDates, "Systol"));
        tsc.addSeries(createVitaSeries(diastolicValues,bpDates, "Dias"));
        tsc.addSeries(createVitaSeries(glucoValues, glucoDates, "Gluc"));
        tsc.addSeries(createVitaSeries(weightValues, weightDates, "Gewicht"));
        
        return tsc;
	}
	
	private void drawChart()
	{
		XYDataset VitaChartData = createDataset();
		jChart = ChartFactory.createTimeSeriesChart(
				null, "Zeitpunkt", "Messwert", VitaChartData, true, true, false);
		 XYPlot plot = jChart.getXYPlot();
	        XYLineAndShapeRenderer renderer =
	            (XYLineAndShapeRenderer) plot.getRenderer();
	        renderer.setBaseShapesVisible(true);
		ChartPanel chartPanel = new ChartPanel(jChart);
		this.add(chartPanel);
	}
	
	public void readDataIntoArrays(LinkedList<Messung> in)
	{
		
		for (Messung m:in)
		{
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
		bpDates = new long[bpCount];
		
		glucoValues = new double[glucoCount];
		glucoDates = new long[glucoCount];
		
		weightValues = new double[weightCount];
		weightDates = new long[weightCount];
		
		int bpC=0, glC=0, wC = 0;
		
		for (Messung m : in)
		{
			
			switch (m.getmID())
			{
			case MainWindow.BLUTDRUCK:
				
				systolicvalues[bpC] = m.getValue1();
				diastolicValues[bpC] = m.getValue2();
				bpDates[bpC] = m.getNumericDate();
				bpC++;
				
				break;
			
			case MainWindow.BLUTZUCKER:

				glucoValues[glC] = m.getValue1();
				glucoDates[glC] = m.getNumericDate();
				glC++;
			
				break;
			
			case MainWindow.GEWICHT:
				 
				weightValues[wC] = m.getValue1();
				weightDates[wC] = m.getNumericDate();
				wC++;
			
				break;

			default:
				break;
			}
		}
	}

	public void Show()
	{
		this.setVisible(true);
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e)
	{
		 owner.setbtnEnabledState(owner.btnOpenChart, true);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		this.dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		chrtPanel.repaint();
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{}

	@Override
	public void windowIconified(WindowEvent e)
	{}

	@Override
	public void windowOpened(WindowEvent e)
	{}

}
