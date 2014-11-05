package vitaTracker;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class VitaChartFrame extends JFrame implements WindowListener, MouseMotionListener
{
	
	private MainWindow owner;
	private int winX, winY, winHeight = 500, winWidth = 500;
	
	protected static ChartPanel chrtPanel;
	protected static JScrollPane imgScrllPane;
	
	private LinkedList<Messung> data;
//	private DataSet dataset;
//	private XYDataset bpSysDataset, bpDiaDataset, glucoDataSet, weightDataset;
	private JFreeChart jChart;
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
//		chrtPanel.setSize(300, 300);
//		chrtPanel.addMouseMotionListener(this);
//		imgScrllPane = new JScrollPane(chrtPanel);
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
//       	bpSysDataset = DataSet.dataset(systolicvalues, bpDates, "Syst");
//       	bpDiaDataset= DataSet.dataset(diastolicValues, bpDates, "Dias");
//       	bpSysDataset = DataSet.dataset(glucoValues, glucoDates, "Gluc");
//       	bpSysDataset = DataSet.dataset(weightValues, weightDates, "Gew");
    
	}

	/**
	 * 
	 */
	
		
	
	private TimeSeries createVitaSeries(double[] values, long[] dates,final String name) 
	{
        final TimeSeries timeSeries = new TimeSeries(name);
//        RegularTimePeriod t = new Day();
 
        
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
//			m = in.get(i);
						
			if (m.getmID() == MainWindow.BLUTDRUCK)
			{
				bpCount++;
				System.out.println("bpCount: " + bpCount);
			}
			else if (m.getmID() == MainWindow.BLUTZUCKER)
			{
				glucoCount++;
				System.out.println("glucoCount: " + glucoCount);
			}
			else if (m.getmID() == MainWindow.GEWICHT)
			{
				weightCount++;
				System.out.println("weightCount: " + weightCount);
			}
		}

		systolicvalues = new double[bpCount];
		diastolicValues = new double[bpCount];
		bpDates = new long[bpCount];
		System.out.println("sys[]length: " + systolicvalues.length);
		
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
				m.getAllData(); //hilfsmethode... 
				systolicvalues[bpC] = m.getValue1();
				diastolicValues[bpC] = m.getValue2();
				bpDates[bpC] = m.getNumericDate();
				bpC++;
				
				break;
			
			case MainWindow.BLUTZUCKER:
				m.getAllData(); //hilfsmethode... 
				glucoValues[glC] = m.getValue1();
				glucoDates[glC] = m.getNumericDate();
				glC++;
			
				break;
			
			case MainWindow.GEWICHT:
				m.getAllData(); //hilfsmethode... 
				weightValues[wC] = m.getValue1();
				weightDates[wC] = m.getNumericDate();
				wC++;
			
				break;

			default:
				break;
			}
			
			
		}
		
		for (int i = 0; i<bpCount;i++)
			System.out.println(i+ " , " +systolicvalues[i]+ " , "+ diastolicValues[i]+" , " + new SimpleDateFormat("HH:mm, dd.MM.yyyy").format(new Date(bpDates[i])));
		
		
		
	}

	public void Show()
	{
		this.setVisible(true);
	}

	public void setImageOnImagePanel(File bildPfad, BufferedImage bild)
	{
//		chrtPanel.setImage(bildPfad,bild);
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{}

	@Override
	public void mouseMoved(MouseEvent e)
	{}

	@Override
	public void windowActivated(WindowEvent e)
	{}

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
	{}

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
