package vitaTracker;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableRowSorter;
import vitaTracker.Messung.messArtEnum;
import vitaTracker.Util.*;


/**
 * 
 * @author Benjamin Dux
 *
 */
public class MainWindow extends JFrame implements ActionListener, WindowListener, ItemListener
{

	private JPanel 				pnBtnFoot, pnFilter, pnChart;  
	private JPanel				pnHeadPanel,pnHdPnlLnSt;
	private JPanel				pnEingabe, pnEingabeInner;

	
	private JButton				btnFelderLoeschen, btnDatenHolen,btnMessZeitSetzen, btnMessCommit;
	private JButton				btnMessAdd;
	private JMenuBar 			menuBar;
	private JMenu 				datei, extras;
	private JMenuItem 			miLoad, miSave, miExit, miEval;
	private JLabel				lblVal1, lblVal2, lblMessZeit, lblUnit,lblFilterSelect;
	private JTextField			tfMessZeit;
	private ValueField			tfVal1, tfVal2;
	
	private JTable				tblMessung;
	private JScrollPane 		scrpTableScroll;
	private WindowTableModel	tmWTableModel;
	private String[]			strArrmessArten, strArrMessUnits, strArrtableColNames;
	private Date				dateMessung;
	private Calendar			calDateMess;
	private SimpleDateFormat	sDForm;
	private JComboBox<String> 	cBoxMessArten,cBoxMsngUnit,cbMessFilter;
	private URL 				urlIconURL;
	private DateTimePicker 		dtpDaTime;
	private Messung 			mObjMessung;
	private LinkedList<Messung>	liLiMessungen;
	private Object[][]			objArrTable;
	private File				file = new File("user.home");
	private StatusBar			sbStaBarMainWin;
	private BorderLayout		blFrameLayout, blEgPnl, blHdPn, blPnHdPnlLnSt;
	private GridLayout			blFltrPn, blFlterPl;
	private TableRowSorter<WindowTableModel> sorter;
	
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
	
	private void initFrame()
	{
		tblMessung.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tblMessung.repaint();
		this.pack();
	}
	
	/*
	 * initialisiert die Fensterkomponenten
	 */
	private void initializeComponents()
	{
		blEgPnl 			= new BorderLayout(5, 5);
		blHdPn 			= new BorderLayout(2, 2);
		blPnHdPnlLnSt	= new BorderLayout();
		blFltrPn		= new GridLayout(0, 1);
		blFlterPl		= new GridLayout(1, 0);
		blFrameLayout 	= new BorderLayout();
		
		
		this.setBounds(100, 100, 400, 350);
		this.setLocationRelativeTo(null);
		this.setLayout(blFrameLayout);
		
		this.setMinimumSize(new Dimension(400,350));
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.setResizable(true);
		
		urlIconURL = getClass().getResource("/resource/120px-Health-stub.gif");
		ImageIcon icon = new ImageIcon(urlIconURL);
		this.setIconImage(icon.getImage());
		this.setTitle("VitaTracker");
		
		strArrtableColNames = new String[] {"Messungsart", "Wert", "Einheit", "Messzeitpunkt"};
		strArrmessArten = new String[] {"Blutdruck","Blutzucker","Gewicht"};
		strArrMessUnits = new String[] {"mmHg","Kg","lbs","mmol/L","mg/dL"};
		dateMessung = new Date(System.currentTimeMillis());
		sDForm = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
			
		liLiMessungen = new LinkedList<Messung>();
		
		tblMessung = new JTable();
		tblMessung.setAutoCreateRowSorter(true);
		scrpTableScroll = new JScrollPane(tblMessung);
		
		this.add(scrpTableScroll, blFrameLayout.CENTER);
		
		sbStaBarMainWin = new StatusBar();
		sbStaBarMainWin.setMessage("Bereit");
		this.add(sbStaBarMainWin, blFrameLayout.PAGE_END);
		
		pnEingabe = new JPanel();
		pnEingabe.setLayout(blEgPnl);
		pnEingabe.setPreferredSize(new Dimension(200,250));
		pnEingabe.setBackground(Color.LIGHT_GRAY);
		this.add(pnEingabe, blFrameLayout.LINE_START);
		
		pnEingabeInner = new JPanel();
		pnEingabeInner.setLayout(null);
		pnEingabeInner.setPreferredSize(new Dimension(200,250));
		pnEingabeInner.setBackground(pnEingabe.getBackground());
		pnEingabe.add(pnEingabeInner, blEgPnl.CENTER);
		
		pnBtnFoot = new JPanel();
		pnBtnFoot.setLayout(blFltrPn);
		pnBtnFoot.setBackground(pnEingabe.getBackground());
		pnEingabe.add(pnBtnFoot, blEgPnl.PAGE_END);
		
		pnHeadPanel = new JPanel();
		pnHeadPanel.setBackground(pnEingabe.getBackground());
		pnHeadPanel.setLayout(blHdPn);
		this.add(pnHeadPanel, blFrameLayout.PAGE_START);
		
		pnFilter = new JPanel();
		pnFilter.setLayout(blFlterPl);
		pnFilter.setBackground(pnHeadPanel.getBackground());
		pnHeadPanel.add(pnFilter, blHdPn.LINE_END);


		
		lblFilterSelect = new JLabel("Filtern nach: ");
		lblFilterSelect.setPreferredSize(new Dimension(200, 25));
		pnFilter.add(lblFilterSelect);
		
		// Line Start im pnHeadPanel
		
		pnHdPnlLnSt = new JPanel();
		pnHdPnlLnSt.setLayout(blPnHdPnlLnSt);
		pnHdPnlLnSt.setPreferredSize(new Dimension(200,25));
		pnHeadPanel.add(pnHdPnlLnSt, blHdPn.LINE_START);
		
		cBoxMessArten = new JComboBox<String>(strArrmessArten);
		cBoxMessArten.setSelectedItem(strArrmessArten[DEFAULT_SELECTION]);
		cBoxMessArten.setPreferredSize(new Dimension(125,25));
		cBoxMessArten.addItemListener(this);
		pnHdPnlLnSt.add(cBoxMessArten, blPnHdPnlLnSt.LINE_START);
		
		cbMessFilter = new JComboBox<String>();
		for (int i = 0;i<strArrmessArten.length;i++)
			cbMessFilter.addItem(strArrmessArten[i]);
		cbMessFilter.addItem("Alle");
		cbMessFilter.setSelectedItem("Alle");
		cbMessFilter.addItemListener(this);
		pnFilter.add(cbMessFilter);
		
		cBoxMsngUnit = new JComboBox<String>();
		cBoxMsngUnit.setPreferredSize(new Dimension(75,25));
		cBoxMsngUnit.addItemListener(this);
		pnHdPnlLnSt.add(cBoxMsngUnit, blPnHdPnlLnSt.LINE_END);
		
		// LineStart im HeadPanel
		
	
		// Eingabepanel
		
		lblVal1 = new JLabel("Systolischer Wert");
		lblVal1.setBounds(15, 15, 150, 25);
		pnEingabeInner.add(lblVal1);
		
		

		tfVal1 = new ValueField(this);
		tfVal1.setBounds(15, 35, 75, 25);
		pnEingabeInner.add(tfVal1);
		
		lblVal2 = new JLabel("Diastolischer Wert");
		lblVal2.setBounds(15, 70, 150, 25);
		pnEingabeInner.add(lblVal2);

		
		
		tfVal2 = new ValueField(this);
		tfVal2.setBounds(15,90,75,25);
		pnEingabeInner.add(tfVal2);
		
		lblMessZeit = new JLabel("Messzeitpunkt: ");
		lblMessZeit.setBounds(15, 155, 150, 25);
		pnEingabeInner.add(lblMessZeit);
				
		tfMessZeit = new JTextField(sDForm.format(dateMessung).toString());
		tfMessZeit.setBounds(15, 180, 150, 25);
		tfMessZeit.setEnabled(false);
		pnEingabeInner.add(tfMessZeit);
		
		btnMessZeitSetzen = new JButton(">");
		btnMessZeitSetzen.setFont(btnMessZeitSetzen.getFont().deriveFont(Font.PLAIN));
		btnMessZeitSetzen.setMargin(null);
		btnMessZeitSetzen.setBounds(170, 180 , 25, 25);
		btnMessZeitSetzen.addActionListener(this);
		pnEingabeInner.add(btnMessZeitSetzen);

		btnMessAdd = new JButton("Messung hinzuf�gen");
		btnMessAdd.addActionListener(this);
		btnMessAdd.setEnabled(true);
		pnBtnFoot.add(btnMessAdd);
		
		btnMessCommit = new JButton("Messungen Sichern");
		btnMessCommit.addActionListener(this);
		btnMessCommit.setEnabled(false);
		pnBtnFoot.add(btnMessCommit);
		
		//Men�leiste
		menuBar = new JMenuBar();
		datei = WinUtil.createMenu(menuBar, "Datei", "menuName", 'D');
		extras = WinUtil.createMenu(menuBar, "Extras", "Extras", 'X');
		
		miLoad = WinUtil.createMenuItem(datei, "Laden", WinUtil.MenuItemType.ITEM_PLAIN, this, "Laden", null, 'L', null);
		miSave = WinUtil.createMenuItem(datei, "Speichern", WinUtil.MenuItemType.ITEM_PLAIN, this, "Speichern", null, 'A', null);
		miExit = WinUtil.createMenuItem(datei, "Beenden", WinUtil.MenuItemType.ITEM_PLAIN, this, "Beenden", null, 'N', null);
		miEval = WinUtil.createMenuItem(extras, "Auswertung", WinUtil.MenuItemType.ITEM_PLAIN, this, 
				"Auswertung", null, 'W',null);
		this.setJMenuBar(menuBar);
		


		
		setUIEntries(cBoxMessArten.getSelectedIndex());
		this.pack();
	}
	
	
	/**
	 * �ndert die Ansicht f�r das Eingabepanel entsprechend der auswahl der cBoxMessArten
	 * <br></br>
	 * @param int messung: Integer Wert zur �bergabe and das Messeinheiten Array 
	 * 
	 */
	
	public void setStatusBarText(String s)
	{
		sbStaBarMainWin.setText(s);		
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
			
			cBoxMsngUnit.addItem(strArrMessUnits[BP_UNIT]);
			lblVal1.setText("Systolischer Wert");
			lblVal2.setText("Diastolischer Wert");
			lblVal2.setVisible(true);
			tfVal2.setEnabled(true);
			tfVal2.setEnabled(true);
			
			break;

		case BLUTZUCKER:
			
			cBoxMsngUnit.addItem(strArrMessUnits[GLUCO_MG]);
			cBoxMsngUnit.addItem(strArrMessUnits[GLUCO_MOL]);
			lblVal1.setText("Blutzuckerwert");
			lblVal2.setVisible(false);
			tfVal2.setText(null);
			tfVal2.setEnabled(false);

			
			break;
			
		case GEWICHT:
			
			cBoxMsngUnit.addItem(strArrMessUnits[WEIGHT_METRIC]);
			cBoxMsngUnit.addItem(strArrMessUnits[WEIGHT_IMPERIAL]);
			lblVal1.setText("Gewicht");
			lblVal2.setVisible(false);
			tfVal2.setText(null);
			tfVal2.setEnabled(false);
			
			
			break;
		}
		
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
		

		dateiLesenBufferedReader(fc.getSelectedFile().toString());
	}

	private void dateiLesenBufferedReader(String string) {
		// TODO Auto-generated method stub
		
	}

	private void initData()
	{
		objArrTable = new Object[liLiMessungen.size()+1][strArrtableColNames.length];
		//Tableheader im Array vorbereiten
		for (int i=0;i<strArrtableColNames.length;i++)
			objArrTable[0][i] = strArrtableColNames[i];
		updateTableData();
		
	}

	private void updateTableData()
		{	
			tmWTableModel = new WindowTableModel(objArrTable);
			tblMessung.setModel(tmWTableModel);
		}

	private void addTableEntry(int row)
	{
		
		extendMessArray(objArrTable);
		objArrTable[row][0] = liLiMessungen.getLast().getStrMessArt();
		
		if (cBoxMessArten.getSelectedIndex() == BLUTDRUCK)
		{	
			objArrTable[row][1] = liLiMessungen.getLast().getValueAtIndex(0) + " / " + liLiMessungen.getLast().getValueAtIndex(1);
		} 
		else	
		{	
			objArrTable[row][1] = liLiMessungen.getLast().getValueAtIndex(0);
		}
		
		objArrTable[row][2] = cBoxMsngUnit.getSelectedItem().toString();
		objArrTable[row][3] = sDForm.format(liLiMessungen.getLast().getDate());
		
		updateTableData();
				
	}
		
	
		
	private void filterTable(String string)
	{
		
	}

	private void extendMessArray(Object[][] in)
	{
		int sourceLength= in.length;
		
		Object[][] newArray = new Object[sourceLength+1][strArrtableColNames.length];
		System.arraycopy(in,0,newArray,0,sourceLength);
		

		objArrTable = new Object[sourceLength+1][strArrtableColNames.length];
		objArrTable = Arrays.copyOf(newArray, in.length+1);
		
	
		
		
	}
	
	
	
	private void addMessung(Messung m)
	{
		
			liLiMessungen.add(m);
		
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
					
					this.mObjMessung = new Messung(this.getDateMessung(),Double.parseDouble(tfVal1.getText()),Double.parseDouble(tfVal2.getText()),
					messArtEnum.blutDruck);
					
					
					tfVal1.setText("");
					tfVal2.setText("");
					messWasCreated = true;
					sbStaBarMainWin.setText("Bereit");
					}
					
					else
					sbStaBarMainWin.setText("Der systolische Wert muss gr�sser als der Diastolische sein.");
					
				}	
				else
					sbStaBarMainWin.setText("Messung kann nicht �bernommen werden.");
		
				break;
				
			case BLUTZUCKER:
			
				if (tfVal1.getText() != null )
				{
					this.mObjMessung = new Messung(this.getDateMessung(),Double.parseDouble(tfVal1.getText()),
							0,
							messArtEnum.blutZucker);
					tfVal1.setText("");
					messWasCreated = true;
				}
				
				break;
				
			case GEWICHT:
				
				if (tfVal1.getText() != null )
				{
					this.mObjMessung = new Messung(this.getDateMessung(),Double.parseDouble(tfVal1.getText()),
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
			sbStaBarMainWin.setText("Ung�ltige Eingabe");
		} 
		
			
		if (messWasCreated)
		{	
			addMessung(mObjMessung);
			addTableEntry(liLiMessungen.size());
			messWasCreated = false;		
		}
	
		
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
			dtpDaTime = new DateTimePicker(this);
		
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
		else if(o == cbMessFilter)
		{
			filterTable(cbMessFilter.getSelectedItem().toString());
		}
		
		
	}
	
	public static void main(String[] args)
		{
			MainWindow mw = new MainWindow();
			mw.Show();
		
		}



}
