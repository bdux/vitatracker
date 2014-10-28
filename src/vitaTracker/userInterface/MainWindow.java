package vitaTracker.userInterface;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.jws.Oneway;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TabExpander;

import vitaTracker.dataHandler.WindowTableModel;
import vitaTracker.Util.*;
import vitaTracker.dataHandler.messungen.Messung;
import vitaTracker.dataHandler.messungen.Messung.messArtEnum;

/**
 * 
 * @author Benjamin Dux
 *
 */
public class MainWindow extends JFrame implements ActionListener, WindowListener, ItemListener
{

	private JPanel 				chartPanel, eingabePanel, eingabeInnerPanel, headPanel,pnlBtnFootPanel;
	private JButton				btnFelderLoeschen, btnDatenHolen,btnMessZeitSetzen, btnMessCommit;
	private JButton				btnMessAdd;
	private JMenuBar 			menuBar;
	private JMenu 				datei, extras;
	private JMenuItem 			miLoad, miSave, miExit;
	private JLabel				lblVal1, lblVal2, /*glucoVal, gewichtVal*/ messZeit, lblUnit;
	private JTextField			/*tfVal1, tfVal2 tfGlucoVal, tfGewichtVal*/ tfMessZeit;
	private ValueField			tfVal1, tfVal2;
	
	private JTable				messungTabelle;
	private JScrollPane 		tableScroll;
//	private WindowTableModel	wTableModel;
	private String[]			strArrmessArten, strMessUnits, tableColumnNames;
	private Date				dateMessung;
	private Calendar			calDateMess;
	private SimpleDateFormat	sDForm;
	private JComboBox<String> 	cBoxMessArten,cBoxMsngUnit;
	private URL 				iconURL;
	private DateTimePicker 		dtp;
	private Messung 			m;
	private LinkedList<Messung>	messungen;
	private Object[][]			objArrTable;
	private File				file = new File("user.home");
	private StatusBar			statusBar;
	private BorderLayout		FrameLayout, ePBl, hPBl;
	private GridLayout			bFPl;
	
	
	private boolean messWasCreated = false;

	
	// Diverse Parameter Ints 
	public static final	int BLUTDRUCK = 0, BLUTZUCKER = 1, GEWICHT = 2,
							DEFAULT_SELECTION = GEWICHT, BP_UNIT = 0, WEIGHT_METRIC = 1, 
							WEIGHT_IMPERIAL = 2, GLUCO_MOL = 3, GLUCO_MG = 4;
	
	public MainWindow()
	{
		initializeComponents();
		initData();
		
		
	}
	
	private void Show()
	{
				
		this.setVisible(true);
		initFrame();
		
	}
	
	private void initData()
	{
		
		
		
	}
	
	
	
	private void initFrame()
	{
		messungTabelle.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		messungTabelle.repaint();
		this.pack();
	}
	
	/*
	 * initialisiert die Fensterkomponenten
	 */
	private void initializeComponents()
	{
		ePBl = new BorderLayout(5, 5);
		hPBl = new BorderLayout(2, 2);
		bFPl = new GridLayout(0, 1);
		FrameLayout = new BorderLayout();
		
		
		this.setBounds(100, 100, 400, 350);
		this.setLocationRelativeTo(null);
		this.setLayout(FrameLayout);
		
		this.setMinimumSize(new Dimension(400,350));
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(true);
		
		iconURL = getClass().getResource("120px-Health-stub.gif");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		this.setTitle("VitaTracker");
		
		tableColumnNames = new String[] {"Messungsart", "Wert", "Einheit", "Messzeitpunkt"};
		
		messungen = new LinkedList<Messung>();
				
		objArrTable = new Object[messungen.size()+1][tableColumnNames.length];  
		
		
//		wTableModel= new WindowTableModel(objArrTable);
		messungTabelle = new JTable(objArrTable, tableColumnNames);

		tableScroll = new JScrollPane(messungTabelle);
		this.add(tableScroll, FrameLayout.CENTER);
		
		strArrmessArten = new String[] {"Blutdruck","Blutzucker","Gewicht"};
		strMessUnits = new String[] {"mmHg","Kg","lbs","mmol/L","mg/dL"};
		dateMessung = new Date(System.currentTimeMillis());
		sDForm = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
		
		statusBar = new StatusBar();
		statusBar.setMessage("Bereit");
		this.add(statusBar, FrameLayout.PAGE_END);
		
		eingabePanel = new JPanel();
		eingabePanel.setLayout(ePBl);
		eingabePanel.setPreferredSize(new Dimension(200,250));
		eingabePanel.setBackground(Color.LIGHT_GRAY);
		this.add(eingabePanel, FrameLayout.LINE_START);
		
		eingabeInnerPanel = new JPanel();
		eingabeInnerPanel.setLayout(null);
		eingabeInnerPanel.setPreferredSize(new Dimension(200,250));
		eingabeInnerPanel.setBackground(eingabePanel.getBackground());
		eingabePanel.add(eingabeInnerPanel, ePBl.CENTER);
		
		pnlBtnFootPanel = new JPanel();
		pnlBtnFootPanel.setLayout(bFPl);
		pnlBtnFootPanel.setBackground(eingabePanel.getBackground());
		eingabePanel.add(pnlBtnFootPanel, ePBl.PAGE_END);
		
		headPanel = new JPanel();
		headPanel.setBackground(eingabePanel.getBackground());
		headPanel.setLayout(hPBl);
		this.add(headPanel, FrameLayout.PAGE_START);
		
		cBoxMessArten = new JComboBox<String>(strArrmessArten);
		cBoxMessArten.setSelectedItem(strArrmessArten[DEFAULT_SELECTION]);
		cBoxMessArten.setPreferredSize(new Dimension(200,25));
		cBoxMessArten.addItemListener(this);
		headPanel.add(cBoxMessArten, hPBl.LINE_START);
		
		
		cBoxMsngUnit = new JComboBox<String>();
		cBoxMsngUnit.setPreferredSize(new Dimension(100,25));
		cBoxMsngUnit.addItemListener(this);
		headPanel.add(cBoxMsngUnit, hPBl.LINE_END);
	
		
		lblUnit = new JLabel("Einheit: ");
		lblUnit.setPreferredSize(new Dimension(200, 25));
		headPanel.add(lblUnit, hPBl.CENTER);
				
		lblVal1 = new JLabel("Systolischer Wert");
		lblVal1.setBounds(15, 15, 150, 25);
		eingabeInnerPanel.add(lblVal1);
		
		

		tfVal1 = new ValueField(this);
		tfVal1.setBounds(15, 35, 75, 25);
		eingabeInnerPanel.add(tfVal1);
		
		lblVal2 = new JLabel("Diastolischer Wert");
		lblVal2.setBounds(15, 70, 150, 25);
		eingabeInnerPanel.add(lblVal2);

		
		
		tfVal2 = new ValueField(this);
		tfVal2.setBounds(15,90,75,25);
		eingabeInnerPanel.add(tfVal2);
		
		messZeit = new JLabel("Messzeitpunkt: ");
		messZeit.setBounds(15, 155, 150, 25);
		eingabeInnerPanel.add(messZeit);
				
		tfMessZeit = new JTextField(sDForm.format(dateMessung).toString());
		tfMessZeit.setBounds(15, 180, 150, 25);
		tfMessZeit.setEnabled(false);
		eingabeInnerPanel.add(tfMessZeit);
		
		btnMessZeitSetzen = new JButton(">");
		btnMessZeitSetzen.setFont(btnMessZeitSetzen.getFont().deriveFont(Font.PLAIN));
		btnMessZeitSetzen.setMargin(null);
		btnMessZeitSetzen.setBounds(170, 180 , 25, 25);
		btnMessZeitSetzen.addActionListener(this);
		eingabeInnerPanel.add(btnMessZeitSetzen);

		btnMessAdd = new JButton("Messung hinzufügen");
		btnMessAdd.addActionListener(this);
		btnMessAdd.setEnabled(true);
		pnlBtnFootPanel.add(btnMessAdd);
		
		btnMessCommit = new JButton("Messungen Sichern");
		btnMessCommit.addActionListener(this);
		btnMessCommit.setEnabled(false);
		pnlBtnFootPanel.add(btnMessCommit);
		
		menuBar = new JMenuBar();
		datei = WinUtil.createMenu(menuBar, "Datei", "menuName", 'D');
		
		miLoad = WinUtil.createMenuItem(datei, "Laden", WinUtil.MenuItemType.ITEM_PLAIN, this, "Laden", null, 'L', null);
		miSave = WinUtil.createMenuItem(datei, "Speichern", WinUtil.MenuItemType.ITEM_PLAIN, this, "Speichern", null, 'A', null);
		miExit = WinUtil.createMenuItem(datei, "Beenden", WinUtil.MenuItemType.ITEM_PLAIN, this, "Beenden", null, 'N', null);
		this.setJMenuBar(menuBar);
		
//		addTableEntry();

		
		setUIEntries(cBoxMessArten.getSelectedIndex());
		this.pack();
	}
	
	
	/**
	 * Ändert die Ansicht für das Eingabepanel entsprechend der auswahl der cBoxMessArten
	 * <br></br>
	 * @param int messung: Integer Wert zur Übergabe and das Messeinheiten Array 
	 * 
	 */
	
	public void setStatusBarText(String s)
	{
		statusBar.setText(s);		
	}
	
	public void setbtnMessAddEnabledState( boolean bool)
	{
		
		btnMessAdd.setEnabled(bool);
		
	}
	
	private void setUIEntries(int messung)
	{
		cBoxMsngUnit.removeAllItems();
		switch (messung)
		{
		case BLUTDRUCK:
			
			cBoxMsngUnit.addItem(strMessUnits[BP_UNIT]);
			lblVal1.setText("Systolischer Wert");
			lblVal2.setText("Diastolischer Wert");
			lblVal2.setVisible(true);
			tfVal2.setEnabled(true);
			tfVal2.setEnabled(true);
			
			break;

		case BLUTZUCKER:
			
			cBoxMsngUnit.addItem(strMessUnits[GLUCO_MG]);
			cBoxMsngUnit.addItem(strMessUnits[GLUCO_MOL]);
			lblVal1.setText("Blutzuckerwert");
			lblVal2.setVisible(false);
			tfVal2.setText(null);
			tfVal2.setEnabled(false);
//			tfVal2.setEnabled(false);
			
			break;
			
		case GEWICHT:
			
			cBoxMsngUnit.addItem(strMessUnits[WEIGHT_METRIC]);
			cBoxMsngUnit.addItem(strMessUnits[WEIGHT_IMPERIAL]);
			lblVal1.setText("Gewicht");
			lblVal2.setVisible(false);
			tfVal2.setText(null);
//			tfVal2.setVisible(false);
			tfVal2.setEnabled(false);
			
			
			break;
		}
		
	}

	private void dateiLesen()
	{
		
		JFileChooser fc = new JFileChooser();
		
		fc.setFileFilter(new FileNameExtensionFilter("Textdateien (*.txt)", "txt"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.setDialogTitle("Textdatei AuswÃ¤hlen");
		
		
		fc.setCurrentDirectory(file);
		
		
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			{
				return;
			}
		
		fc.getSelectedFile();
		

		dateiLesenBufferedReader(fc.getSelectedFile().toString());
	}

	private void dateiLesenBufferedReader(String string) {
		// TODO Auto-generated method stub
		
	}

	private void addTableEntry(int row)
	{
		
				
		if (row <= messungen.size())
		{	
			objArrTable[row][0] = messungen.get(row).getStrMessArt();
			objArrTable[row][1] = messungen.get(row).getValueAtIndex(0);			
			objArrTable[row][2] = strMessUnits[cBoxMsngUnit.getSelectedIndex()];
			objArrTable[row][3] = messungen.get(row).getDate();
			messungTabelle.repaint();
				
		}
		
		else
			
		{
			extendMessArray(objArrTable);
			objArrTable[row][0] = messungen.get(row).getStrMessArt();
			objArrTable[row][1] = messungen.get(row).getValueAtIndex(0);			
			objArrTable[row][2] = strMessUnits[cBoxMsngUnit.getSelectedIndex()];
			objArrTable[row][3] = messungen.get(row).getDate();
			messungTabelle.repaint();
			
		}

	}


		
	private void extendMessArray(Object[][] in)
	{
		int l = in.length;
		
		Object[][] newArray = new Object[in.length+1][tableColumnNames.length];
		System.arraycopy(in, 0, newArray, 0, l+1);
		
//		for(int i=0; i < 30; i++)
//			Arrays.fill(myArray[i], "");

		in = Arrays.copyOf(newArray, l+1);
		objArrTable = in;
		
		
	}
	
	
	
	public void erzeugeMessung()
	{
		
	
		try
		{
			switch (cBoxMessArten.getSelectedIndex()) 
			{
			
			case BLUTDRUCK:
				if (tfVal1.getText() != null && tfVal2.getText() != null)
				{
										
					if (Double.parseDouble(tfVal1.getText()) >= Double.parseDouble(tfVal2.getText()))
					{
					
					this.m = new Messung(this.getDateMessung(),Double.parseDouble(tfVal1.getText()),Double.parseDouble(tfVal2.getText()),
					messArtEnum.blutDruck);
					
					
					tfVal1.setText("");
					tfVal2.setText("");
					messWasCreated = true;
					statusBar.setText("Bereit");
					}
					
					else
					statusBar.setText("Der systolische Wert muss grösser als der Diastolische sein.");
					
				}	
				else
					statusBar.setText("Messung kann nicht übernommen werden.");
		
				break;
				
			case BLUTZUCKER:
			
				if (tfVal1.getText() != null )
				{
					this.m = new Messung(this.getDateMessung(),Double.parseDouble(tfVal1.getText()),
							0,
							messArtEnum.blutZucker);
					tfVal1.setText("");
					messWasCreated = true;
				}
				
				break;
				
			case GEWICHT:
				
				if (tfVal1.getText() != null )
				{
					this.m = new Messung(this.getDateMessung(),Double.parseDouble(tfVal1.getText()),
							0,
							messArtEnum.gewicht);
					tfVal1.setText("");
					messWasCreated = true;
							
				}	
				
				break;
				
			}
			
			
			
		} 
		catch (Exception e)
		{
			statusBar.setText(e.getMessage());
		} 
		
			
		if (messWasCreated)
		{	
			addMessung(m);
			addTableEntry(messungen.size()-1);
			messWasCreated = false;		
		}
	
		
	}
	
	
	
	private void addMessung(Messung m)
	{
		
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
	
	/*die Listener-Methoden*/
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		
		if (o == miExit)
			this.dispose();
		else if (o == btnMessZeitSetzen)
			dtp = new DateTimePicker(this);
		
		else if (o == btnMessAdd)
				erzeugeMessung();		
		
		else if (o == miLoad)
			dateiLesen();
		
		
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
		Object o = e.getSource();
		
		if(o == cBoxMessArten)
		{
			setUIEntries(cBoxMessArten.getSelectedIndex());
		}
		
		
	}
	
	public static void main(String[] args)
		{
			MainWindow mw = new MainWindow();
			mw.Show();
		
		}



}
