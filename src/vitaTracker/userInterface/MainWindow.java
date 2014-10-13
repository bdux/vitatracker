package vitaTracker.userInterface;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import vitaTracker.Util.*;
//import vitaTracker.dataHandler.messungen.MessWert;
import vitaTracker.dataHandler.messungen.Messung;
/**
 * 
 * @author Benjamin Dux
 *
 */
public class MainWindow extends JFrame implements ActionListener, WindowListener, ItemListener
{

	private JPanel 				chartPanel, eingabePanel, tfPanel;
	private JButton				btnMessungSpeichern, btnFelderLoeschen, btnDatenHolen,btnMessZeitSetzen;
	private JComboBox<String>	msngArt;
	private JMenuBar 			menuBar;
	private JMenu 				datei, extras;
	private JMenuItem 			miLoad, miSave, miExit;
	private JLabel				bdSys, bdDia, glucoVal, gewichtVal, messZeit;
	private JTextField			tfVal1, tfVal2 /*tfGlucoVal, tfGewichtVal*/, tfMessZeit;
	private JRadioButton		weightUnit;
	private String[]			strArrmessArten;
	private Date				dateMessung;
	private Calendar			calDateMess;
	private SimpleDateFormat	sDForm;
	private JComboBox<String> 	cBoxMessArten;
	private URL 				iconURL;
	private DateTimePicker 		dtp;
	private Messung 			m;
	private LinkedList<Object>	messungen = new LinkedList<Object>();
	private File				file = new File("user.home");
	
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
		// Heutigen Tag anzeigen, Graph fuer die letzten 24 Stunden zeichnen
		// alle Felder leer, combobox auf defaultwert
		
	}
	
	
	
	/*
	 * initialisiert die Fensterkomponenten
	 */
	private void initializeComponents()
	{
		
		iconURL = getClass().getResource("120px-Health-stub.gif");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		this.setTitle("VitaTracker");
		strArrmessArten = new String[] {"Blutdruck","Blutzucker","Gewicht"};
		dateMessung = new Date(System.currentTimeMillis());
		sDForm = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
		
		
		
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
		btnMessungSpeichern.addActionListener(this);
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
		
		
		tfMessZeit = new JTextField(sDForm.format(dateMessung).toString());
		tfMessZeit.setBounds(15, 160, 150, 25);
		tfMessZeit.setEnabled(false);
		eingabePanel.add(tfMessZeit);
		
		
		tfVal1 = new JTextField();
		tfVal1.setBounds(15,35,75,25);
		eingabePanel.add(tfVal1);
		
		tfVal2 = new JTextField();
		tfVal2.setBounds(15,100,75,25);
		eingabePanel.add(tfVal2);
		
	}
		
	private void checkSelection()
	{
		
			System.out.println("die Auswahl ist: " + cBoxMessArten.getSelectedItem().toString());
			System.out.println(cBoxMessArten.getSelectedIndex() + " index");
	}
	
	private void dateiLesen()
	{
		
		JFileChooser fc = new JFileChooser();
		
		fc.setFileFilter(new FileNameExtensionFilter("Textdateien (*.txt)", "txt"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.setDialogTitle("Textdatei Auswählen");
		
		
		fc.setCurrentDirectory(file);
		
		
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			{
				return;
			}
		
		fc.getSelectedFile();
		
//		dateiLesen(fc.getSelectedFile().toString());
//		dateiLesenStringBuilder(fc.getSelectedFile().toString());
		dateiLesenBufferedReader(fc.getSelectedFile().toString());
	}
	

	private void dateiLesenBufferedReader(String string) {
		// TODO Auto-generated method stub
		
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
		else if (o == btnMessungSpeichern)
		{ 
			System.out.println(tfVal1.getText());
			System.out.println(tfVal2.getText());
			erzeugeMessung();
//			System.out.println(m);
		}
		else if (o == miLoad)
			dateiLesen();
	}

	public void erzeugeMessung()
	{
		
		switch (cBoxMessArten.getSelectedIndex()) 
		{
		
		case 0:
			try 
			{		
				if (tfVal1.getText() != null &&tfVal2.getText() != null)
				{
					this.m = new Messung(this.getDateMessung(),
							Double.parseDouble(tfVal1.getText()),
							Double.parseDouble(tfVal2.getText())
							);
					break;
				}	
				
			} catch (Exception e) {
				System.out.println("geht nicht!");
			}
		break;
					
		default:
			
			try 
			{		
				if (tfVal1.getText() != null )
				{
					this.m = new Messung(this.getDateMessung(),
							Double.parseDouble(tfVal1.getText())
							);
					break;
				}	
				
			} catch (Exception e) {
				System.out.println("geht nicht!");
			}
		break;
			
		}
		
		messungen.add(m);
		
	}
	
	public Date getDateMessung()
	{
				
		return dateMessung;
	}


	public void setDateMessung(Date dateMessung)
	{
		this.dateMessung = dateMessung;
		tfMessZeit.setText(sDForm.format(dateMessung).toString());
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


	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		// TODO Auto-generated method stub
		
	}


	public static void main(String[] args)
	{
		MainWindow mw = new MainWindow();
		mw.Show();
	
	}

}
