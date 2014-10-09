package vitaTracker.userInterface;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;

//import org.jfree.chart.JFreeChart;

import vitaTracker.Util.*;


public class MainWindow extends JFrame implements ActionListener, WindowListener
{

	JPanel 				chartPanel, eingabePanel, tfPanel;
	JButton				btnMessungSpeichern, btnFelderLöschen, btnDatenHolen,btnMessZeitSetzen;
	JComboBox 			msngArt;
	JMenuBar 			menuBar;
	JMenu 				datei, extras;
	JMenuItem 			miLoad, miSave, miExit;
	JLabel				bdSys, bdDia, glucoVal, gewichtVal, messZeit;
	JTextField			tfBdSys, tfBdDia, tfGlucoVal, tfGewichtVal, tfMessZeit;
	String[]			strArrmessArten;
	Date				dateMessung;
	Calendar			calDateMess;
	JComboBox<String> 	cBoxMessArten;
	URL 				iconURL;
	DateTimePicker 		dtp;
	
	
	public MainWindow()
	{
		
		initializeComponents();
		
	}
	
	
	private void Show()
	{
		
		
		this.setVisible(true);
		initFrame();
	
	}
	
	private void initFrame()
	{
		// Heutigen Tag anzeigen, Graph für die letzten 24 Stunden zeichnen
		// alle Felder leer, combobox auf defaultwert
		
	}
	
	
	private void initializeComponents()
	{
		
		iconURL = getClass().getResource("120px-Health-stub.gif");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		this.setTitle("VitaTracker");
		strArrmessArten = new String[] {"Blutdruck","Blutzucker","Gewicht"};
		dateMessung = new Date(System.currentTimeMillis());
		
		cBoxMessArten = new JComboBox<String>(strArrmessArten);
		cBoxMessArten.setSelectedItem(strArrmessArten[strArrmessArten.length-1]);
		cBoxMessArten.setBounds(5, 5, 125, 25);
		cBoxMessArten.addActionListener(this);
		this.add(cBoxMessArten, BorderLayout.NORTH);
		
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 640, 480);
		this.addWindowListener(this);
		this.setResizable(false);

		menuBar = new JMenuBar();
		datei = WinUtil.createMenu(menuBar, "Datei", "menuName", 'D');
		
		miLoad = WinUtil.createMenuItem(datei, "Laden", WinUtil.MenuItemType.ITEM_PLAIN, this, "Laden", null, 'L', null);
		miSave = WinUtil.createMenuItem(datei, "Speichern", WinUtil.MenuItemType.ITEM_PLAIN, this, "Speichern", null, 'A', null);
		miExit = WinUtil.createMenuItem(datei, "Beenden", WinUtil.MenuItemType.ITEM_PLAIN, this, "Beenden", null, 'N', null);
		this.setJMenuBar(menuBar);
		
		eingabePanel = new JPanel();
		eingabePanel.setLayout(null);
		eingabePanel.setPreferredSize(new Dimension(200,250));
		eingabePanel.setBackground(Color.LIGHT_GRAY);
		this.add(eingabePanel, BorderLayout.EAST);
		
		
		btnMessungSpeichern = new JButton("Messung Speichern");
		btnMessungSpeichern.setBounds(15, 400, 150, 25);
		eingabePanel.add(btnMessungSpeichern);
		
		bdSys = new JLabel("Systolischer Wert: ");
		bdSys.setBounds(15, 10, 150, 25);
		eingabePanel.add(bdSys);
		
		bdDia = new JLabel("Diastolischer Wert: ");
		bdDia.setBounds(15, 75, 150, 25);
		eingabePanel.add(bdDia);
		
		btnMessZeitSetzen = new JButton(">");
		btnMessZeitSetzen.setFont(btnMessZeitSetzen.getFont().deriveFont(Font.PLAIN));
		btnMessZeitSetzen.setMargin(null);
		btnMessZeitSetzen.setBounds(170, 160 ,25,25);
		btnMessZeitSetzen.addActionListener(this);
		eingabePanel.add(btnMessZeitSetzen);
		
		messZeit = new JLabel("Messzeitpunkt: ");
		messZeit.setBounds(15, 130, 150, 25);
		eingabePanel.add(messZeit);
		
		
		tfMessZeit = new JTextField(dateMessung.toString());
		tfMessZeit.setBounds(15, 160, 150, 25);
		eingabePanel.add(tfMessZeit);
		
		
		tfBdSys = new JTextField();
		tfBdSys.setBounds(15,35,75,25);
		eingabePanel.add(tfBdSys);
		
		tfBdDia = new JTextField();
		tfBdDia.setBounds(15,100,75,25);
		eingabePanel.add(tfBdDia);
		
	}
		
	private void checkSelection()
	{
		
			System.out.println("die Auswahl ist: " + cBoxMessArten.getSelectedItem().toString());
		
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		
		if (o == cBoxMessArten)
			checkSelection();
		
		else if (o == miExit)
			this.dispose();
		else if (o == btnMessZeitSetzen)
			dtp = new DateTimePicker(this);
		
	}


	public Date getDateMessung()
	{
		
			
		
		return dateMessung;
	}


	public void setDateMessung(Date dateMessung)
	{
		this.dateMessung = dateMessung;
		tfMessZeit.setText(dateMessung.toString());
	}


	@Override
	public void windowActivated(WindowEvent e){}


	@Override
	public void windowClosed(WindowEvent e)	{}


	@Override
	public void windowClosing(WindowEvent e)
	{
		this.dispose();
		
	}


	@Override
	public void windowDeactivated(WindowEvent e){}


	@Override
	public void windowDeiconified(WindowEvent e){}


	@Override
	public void windowIconified(WindowEvent e){}


	@Override
	public void windowOpened(WindowEvent e)	{}


	public static void main(String[] args)
	{
		MainWindow mw = new MainWindow();
		mw.Show();
	
	}

}
