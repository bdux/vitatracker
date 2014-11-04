package vitaTracker;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;

import vitaTracker.Util.DataSet;

public class VitaChartFrame extends JFrame implements WindowListener, MouseMotionListener
{
	
	private MainWindow owner;
	private int winX, winY, winHeight = 500, winWidth = 500;
	
	protected static ChartPanel chrtPanel;
	protected static JScrollPane imgScrllPane;
	
	private Object[][] data;
	private DataSet dataset;
	private JFreeChart jChart;
	
	
	
	
	public VitaChartFrame(MainWindow owner, Object[][] data)
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
		chrtPanel.setSize(300, 300);
		chrtPanel.addMouseMotionListener(this);
		imgScrllPane = new JScrollPane(chrtPanel);
		this.add(imgScrllPane, BorderLayout.CENTER);

		initChartData();
		
	}
	
	/**
	 * 
	 */
	private void initChartData()
	{
		
		
//		jChart = new JFreeChart(ChartFactory.createTimeSeriesChart(null, "Zeitraum", "Werte", dataset));
		
		
		
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
