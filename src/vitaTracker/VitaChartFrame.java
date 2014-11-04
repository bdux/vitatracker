package vitaTracker;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.Dataset;
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
	private XYDataset dataset;
	private JFreeChart jChart;
	
	
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
		XYSeries sysSeries = new XYSeries("Systolische Werte");
//        for(int i=0;i<systolicvalues.length;i++)
//        sysSeries.add(systolicvalues[i], bpDates[i]);
         	dataset = DataSet.dataset(systolicvalues, bpDates, "Systol");
//        dataset.addSeries(sysSeries);
	}

	/**
	 * 
	 */
	private void drawChart()
	{
		
		XYLineAndShapeRenderer line = new XYLineAndShapeRenderer();
		NumberAxis yax = new NumberAxis("Systolisch");
		NumberAxis xax = new NumberAxis("Zeitpunkt");
		XYPlot plot = new XYPlot(dataset, xax, yax, line);
		ValueAxis domainAxis = new DateAxis();
		
		plot.setDomainAxis(domainAxis);
		
		
		jChart = new JFreeChart(plot);
		ChartPanel chartPanel = new ChartPanel(jChart);
		this.add(chartPanel);
		
	}
	
	
	public void readDataIntoArrays(LinkedList<Messung> in)
	{
		for (int i = 0; i<in.size();i++)
		{
			m = in.get(i);
						
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

		systolicvalues = new double[in.size()];
		diastolicValues = new double[in.size()];
		bpDates = new long[in.size()];
		
		glucoValues = new double[in.size()];
		glucoDates = new long[in.size()];
		
		weightValues = new double[in.size()];
		weightDates = new long[in.size()];
		
		
		for (int counter = 0;counter<in.size(); counter++)
		{
			
			switch (in.get(counter).getmID())
			{
			case MainWindow.BLUTDRUCK:
				systolicvalues[counter] = m.getValue1();
				diastolicValues[counter] = m.getValue2();
				bpDates[counter] = m.getNumericDate();
				
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
