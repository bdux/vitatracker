package vitaTracker;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;
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
	private String				stBarDefault;
	private Date				dateMessung;
	private Calendar			calDateMess;
	private SimpleDateFormat	sDForm;
	private JComboBox<String> 	cBoxMessArten,cBoxMsngUnit,cbMessFilter;
	private URL 				urlIconURL;
	private DateTimePicker 		dtpDaTime;
	private Messung 			mObjMessung;
	private LinkedList<Messung>	liLiMessungen;
	private Object[][]			objArrTable;
	private StatusBar			sbStaBarMainWin;
	private BorderLayout		blFrameLayout, blEgPnl, blHdPn, blPnHdPnlLnSt;
	private GridLayout			blFltrPn, blFlterPl;
	private final String fileRead = "Messungen.txt";
	private final String fileSave = "Messungen.txt";
	private File				file = new File(fileRead);
	
	
	private boolean messWasCreated = false;

	
	// Diverse Parameter Ints 
	public static final	int BLUTDRUCK = 0, BLUTZUCKER = 1, GEWICHT = 2,
							DEFAULT_SELECTION = GEWICHT, BP_UNIT = 0, WEIGHT_METRIC = 1, 
							WEIGHT_IMPERIAL = 2, GLUCO_MOL = 3, GLUCO_MG = 4;

	public static final String M_STR_BLUTDRUCK = "Blutdruck", M_STR_BLUTZUCKER = "Blutzucker", M_STR_GEWICHT = "Gewicht", M_STR_ALLE = "Alle";
	public static final String UN_STR_BP_UNIT = "mmHg", UN_WEIGHT_METRIC = "Kg", UN_WEIGHT_IMPERIAL = "lbs", UN_GLUCO_MOL = "mmol/L", UN_GLUCO_MG = "mg/DL";
	public static final String COL_NAME_MESSART = "Messungsart", COL_NAME_VALUE = "Wert", COL_NAME_UNIT = "Einheit", COL_NAME_TIME = "Messzeitpunkt";
	
	
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
	
	/**
	 * initialisiert die Fensterkomponenten
	 */
	private void initializeComponents()
	{
		blEgPnl 		= new BorderLayout(5, 5);
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
		
		urlIconURL 			= getClass().getResource("/resource/120px-Health-stub.gif");
		ImageIcon icon 		= new ImageIcon(urlIconURL);
		this.setIconImage(icon.getImage());
		this.setTitle("VitaTracker");
		
		strArrtableColNames = new String[] {COL_NAME_MESSART, COL_NAME_VALUE, COL_NAME_UNIT, COL_NAME_TIME};
		strArrmessArten 	= new String[] {M_STR_BLUTDRUCK,M_STR_BLUTZUCKER,M_STR_GEWICHT};
		strArrMessUnits 	= new String[] {UN_STR_BP_UNIT,UN_WEIGHT_METRIC,UN_WEIGHT_IMPERIAL,UN_GLUCO_MOL,UN_GLUCO_MG};
		dateMessung			= new Date(System.currentTimeMillis());
		sDForm 				= new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
		
		liLiMessungen 		= new LinkedList<Messung>();
		
		tblMessung 			= new JTable();
		tblMessung.setAutoCreateRowSorter(true);
	
		scrpTableScroll 	= new JScrollPane(tblMessung);
		this.add(scrpTableScroll, blFrameLayout.CENTER);
		
		sbStaBarMainWin		= new StatusBar();
		sbStaBarMainWin.setMessage("Bereit");
		this.add(sbStaBarMainWin, blFrameLayout.PAGE_END);
		
		pnEingabe 			= new JPanel();
		pnEingabe.setLayout(blEgPnl);
		pnEingabe.setPreferredSize(new Dimension(200,250));
		pnEingabe.setBackground(Color.LIGHT_GRAY);
		this.add(pnEingabe, blFrameLayout.LINE_START);
		
		pnEingabeInner 		= new JPanel();
		pnEingabeInner.setLayout(null);
		pnEingabeInner.setPreferredSize(new Dimension(200,250));
		pnEingabeInner.setBackground(pnEingabe.getBackground());
		pnEingabe.add(pnEingabeInner, blEgPnl.CENTER);
		
		pnBtnFoot			 = new JPanel();
		pnBtnFoot.setLayout(blFltrPn);
		pnBtnFoot.setBackground(pnEingabe.getBackground());
		pnEingabe.add(pnBtnFoot, blEgPnl.PAGE_END);
		
		pnHeadPanel 		= new JPanel();
		pnHeadPanel.setBackground(pnEingabe.getBackground());
		pnHeadPanel.setLayout(blHdPn);
		this.add(pnHeadPanel, blFrameLayout.PAGE_START);
		
		pnFilter 			= new JPanel();
		pnFilter.setLayout(blFlterPl);
		pnFilter.setBackground(pnHeadPanel.getBackground());
		pnHeadPanel.add(pnFilter, blHdPn.LINE_END);


		
		lblFilterSelect 	= new JLabel("Filtern nach: ");
		lblFilterSelect.setPreferredSize(new Dimension(200, 25));
		pnFilter.add(lblFilterSelect);
		
		// Line Start im pnHeadPanel
		
		pnHdPnlLnSt 		= new JPanel();
		pnHdPnlLnSt.setLayout(blPnHdPnlLnSt);
		pnHdPnlLnSt.setPreferredSize(new Dimension(200,25));
		pnHeadPanel.add(pnHdPnlLnSt, blHdPn.LINE_START);
		
		cBoxMessArten 		= new JComboBox<String>(strArrmessArten);
		cBoxMessArten.setSelectedItem(M_STR_GEWICHT);
		cBoxMessArten.setPreferredSize(new Dimension(125,25));
		cBoxMessArten.addItemListener(this);
		pnHdPnlLnSt.add(cBoxMessArten, blPnHdPnlLnSt.LINE_START);
		
		cbMessFilter 		= new JComboBox<String>(strArrmessArten);
		cbMessFilter.addItem(M_STR_ALLE);
		cbMessFilter.setSelectedItem(M_STR_ALLE);
		cbMessFilter.addItemListener(this);
		pnFilter.add(cbMessFilter);
		
		cBoxMsngUnit 		= new JComboBox<String>();
		cBoxMsngUnit.setPreferredSize(new Dimension(75,25));
		cBoxMsngUnit.addItemListener(this);
		pnHdPnlLnSt.add(cBoxMsngUnit, blPnHdPnlLnSt.LINE_END);
		
		// LineStart im HeadPanel
		
	
		// Eingabepanel
		
		lblVal1 			= new JLabel("Systolischer Wert");
		lblVal1.setBounds(15, 15, 150, 25);
		pnEingabeInner.add(lblVal1);

		tfVal1 				= new ValueField(this);
		tfVal1.setBounds(15, 35, 75, 25);
		pnEingabeInner.add(tfVal1);
		
		lblVal2 			= new JLabel("Diastolischer Wert");
		lblVal2.setBounds(15, 70, 150, 25);
		pnEingabeInner.add(lblVal2);
		
		tfVal2				= new ValueField(this);
		tfVal2.setBounds(15,90,75,25);
		pnEingabeInner.add(tfVal2);
		
		lblMessZeit 		= new JLabel("Messzeitpunkt: ");
		lblMessZeit.setBounds(15, 155, 150, 25);
		pnEingabeInner.add(lblMessZeit);
				
		tfMessZeit = new JTextField(sDForm.format(dateMessung).toString());
		tfMessZeit.setBounds(15, 180, 150, 25);
		tfMessZeit.setEnabled(false);
		pnEingabeInner.add(tfMessZeit);
		
		btnMessZeitSetzen 	= new JButton(">");
		btnMessZeitSetzen.setFont(btnMessZeitSetzen.getFont().deriveFont(Font.PLAIN));
		btnMessZeitSetzen.setMargin(null);
		btnMessZeitSetzen.setBounds(170, 180 , 25, 25);
		btnMessZeitSetzen.addActionListener(this);
		pnEingabeInner.add(btnMessZeitSetzen);

		btnMessAdd 			= new JButton("Messung hinzufügen");
		btnMessAdd.addActionListener(this);
		btnMessAdd.setEnabled(true);
		pnBtnFoot.add(btnMessAdd);
		
		btnMessCommit 		= new JButton("Messungen Sichern");
		btnMessCommit.addActionListener(this);
		btnMessCommit.setEnabled(false);
		pnBtnFoot.add(btnMessCommit);
		
		//Menüleiste
		menuBar 			= new JMenuBar();
		datei = WinUtil.createMenu(menuBar, "Datei", "menuName", 'D');
		extras = WinUtil.createMenu(menuBar, "Extras", "Extras", 'X');
		
		miLoad = WinUtil.createMenuItem(datei, "Laden", WinUtil.MenuItemType.ITEM_PLAIN, this, "Laden", null, 'L', null);
		miSave = WinUtil.createMenuItem(datei, "Speichern", WinUtil.MenuItemType.ITEM_PLAIN, this, "Speichern", null, 'A', null);
		miExit = WinUtil.createMenuItem(datei, "Beenden", WinUtil.MenuItemType.ITEM_PLAIN, this, "Beenden", null, 'N', null);
		miEval = WinUtil.createMenuItem(extras, "Auswertung", WinUtil.MenuItemType.ITEM_PLAIN, this, "Auswertung", null, 'W',null);
		this.setJMenuBar(menuBar);
			
		setUIEntries(cBoxMessArten.getSelectedIndex());
		this.pack();
	}
	
	
	/**
	 * Ändert die Ansicht für das Eingabepanel entsprechend der auswahl der cBoxMessArten
	 * <br></br>
	 * @param int messung: Integer Wert zur Übergabe and das Messeinheiten Array 
	 * 
	 */
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

	
	
//	public void datenLesenInArrays()
//	{
//		Scanner scanner = null;
//		String zeile;
//		
//		int colCounter = 0;
//		int rowCounter = 0;
//		
//		try 
//		{ 
//			for(int u = 0; u<=1; u++)
//			{
//				colCounter = 0;
//				
//				scanner = new Scanner( new FileInputStream(file) ); 
//				
//				while(scanner.hasNext())
//				{
//					zeile = scanner.nextLine();
//					String[] split = zeile.split(";");
//					
//					// System.out.println( "Zeile " + markenZaehler + " : " + zeile );
//					
//					if(u ==1 && !split[0].equals("#"))
//					{
//						objArrTable[0][colCounter] = split[0];
//						for(int i = 1; i < split.length; i++)
//							objArrTable[i][colCounter] = split[i];
//					}
//					
//					rowCounter = rowCounter < (split.length) ? split.length : rowCounter; 
//					colCounter ++;
//				}
//				
//				if(u == 0)
//					objArrTable = new String[rowCounter][colCounter];
//					
//				scanner.close();
//			}
//		}
//		catch (FileNotFoundException e) {}
//
//	}

	//Wird nicht genutzt, aber vielleicht noch brauchbar
	private int getArrayIndexOf(String[] arr, String s)
	{
		int retValue = -1;
		String searchStr = s;
		String[] searchArr = arr;
		
		for (int i = 0; i<searchArr.length;i++)
		{
			if (searchArr[i] == s )
				retValue = i;
		}
	
		return retValue;
	}
	
	/**
	 * 
	 * anlegen eines neuen Object[][] zur Weitergabe an das WindowTableModel,
	 * erste Spalte bekommt die Strings für den Tabellenheader.
	 */
	private void initData()
	{
		objArrTable = new Object[liLiMessungen.size()+1][strArrtableColNames.length];
		//Tableheader im Array vorbereiten
		for (int i=0;i<strArrtableColNames.length;i++)
			objArrTable[0][i] = strArrtableColNames[i];
		updateTableData(objArrTable);
		readData();
		
	}
	
	/**
	 * erzeugt ein neues WindowTableModel und setzt das WindowTableModel der tblMessung, was auch eine Neuzeichnung der Tabelle auslöst.
	 * @param source das Object[][] das als Grundlage des WindowTableModels genutzt werden soll
	 */
	private void updateTableData(Object[][] source)
	{	
		tmWTableModel = new WindowTableModel(source);
		tblMessung.setModel(tmWTableModel);
	}

	/**
	 * Hilfsmethode zum befüllen der JTable
	 */
	private void addTableEntry()
	{
		extendMessArray(objArrTable);
		updateTableData(fillObjArray(liLiMessungen, objArrTable));
	
	}
	
	/**
	 * 
	 * Befüllt ein Object[][] mit Attributen von Messungsobjekten, die in einer LinkedList vorgehalten werden.
	 * 
	 * @param src die LinkedList<Messung>() als Quelle für die Daten
	 * @param target das Object[][] als Ziel
	 * @return das befüllte Object[][]
	 */
	private Object[][] fillObjArray(LinkedList<Messung> src, Object[][]target)
	{
		int row = src.size();
	
		target[row][0] = src.getLast().getStrMessArt();
		
		if (src.getLast().getmID() == BLUTDRUCK)
		{	
			target[row][1] = src.getLast().getValue1() + "/" + src.getLast().getValue2();
		} 
		else	
		{	
			target[row][1] = src.getLast().getValue1();
		}
		
		target[row][2] = src.getLast().getMessUnit();
		target[row][3] = sDForm.format(src.getLast().getDate());
		
		return target;
	}
	
	/**
	 * @param in das zu Filternde Object[][]
	 * @return das Object[][] das neben dem Header für die Tabelle nur noch die Messungsobjekte mit der gesuchten mID haben
	 */
	private Object[][] filterTable(Object[][] in)
	{
		
		//TODO : das ist noch nicht fertig... wird es wahrscheinlich auch nicht werden... 
		int sourceHeader = in[0].length;
//		int sourceLength= in.length;
		int targetLength = 0;
		int messID = -1;
		int filterOccurence = 1;
		int nextIndex = 1;
		String filterSelection = cbMessFilter.getSelectedItem().toString();
		
		switch (filterSelection)
		{
			case M_STR_BLUTZUCKER:
				
				messID = BLUTZUCKER;
				setStatusBarText("es wird nach Blutzuckerwerten gefiltert.");
				break;
	
			case M_STR_BLUTDRUCK:
				
				messID = BLUTDRUCK;
				setStatusBarText("es wird nach Blutdruckwerten gefiltert.");
				break;
				
			case M_STR_GEWICHT:
				
				messID = GEWICHT;
				setStatusBarText("es wird nach Gewichtswerten gefiltert.");
				break;
			case M_STR_ALLE:
				setStatusBarText("Alle Filter entfernt.");
//				updateTableData(objArrTable);
				return objArrTable;
		}
		
		try
		{
			for (Messung m:liLiMessungen)
			{
				if (m.getmID() == messID)
					filterOccurence++;
			}
			
			targetLength = filterOccurence;

			Object[][] newArray = new Object[targetLength][sourceHeader];
			System.arraycopy(in,0,newArray,0,targetLength);
			
			
			for(Messung m:liLiMessungen)
			{
				if (m.getmID() == messID)
				{
					newArray[nextIndex][0] = m.getStrMessArt();
					if (messID == BLUTDRUCK)
					{	
						newArray[nextIndex][1] = m.getValue1() + "/" + m.getValue2();
					} 
					else	
					{	
						newArray[nextIndex][1] = m.getValue1();
					}
					
					newArray[nextIndex][2] = m.getMessUnit();
					newArray[nextIndex][3] = sDForm.format(m.getDate());
					nextIndex++;
				}
			}
			
//			updateTableData(newArray);
			return newArray;
			
		} 
		catch (Exception e)
		{
			setStatusBarText("nichts zu filtern...");
			return objArrTable;
		}
		
		
	}

	/**
	 * Erweitert ein übergenes Object[][]
	 * @param das zu erweiternde Object[][]
	 */
	
	private void extendMessArray(Object[][] in)
	{
		int sourceHeader = in[0].length;
		int sourceLength= in.length;
		
		Object[][] newArray = new Object[sourceLength+1][sourceHeader];
		System.arraycopy(in,0,newArray,0,sourceLength);
		

		objArrTable = new Object[sourceLength+1][sourceHeader];
		objArrTable = Arrays.copyOf(newArray, in.length+1);
//		in = new Object[sourceLength+1][sourceHeader];
//		in = Arrays.copyOf(newArray, in.length+1);
	}
	
	/**
	 * erzeugt ein Messungsobjekt, unter Berücksichtigung des Ausgewählten indexes der cBoxMessArt.
	 */
	private void createMessung()
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
					cBoxMsngUnit.getSelectedItem().toString() ,messArtEnum.blutDruck);
					
					
					tfVal1.setText("");
					tfVal2.setText("");
					messWasCreated = true;
					sbStaBarMainWin.setText("Bereit");
					}
					
					else
					sbStaBarMainWin.setText("Der systolische Wert muss grösser als der Diastolische sein.");
				}	
				else
					sbStaBarMainWin.setText("Messung kann nicht übernommen werden.");
		
				break;
				
			case BLUTZUCKER:
			
				if (tfVal1.getText() != null )
				{
					this.mObjMessung = new Messung(this.getDateMessung(),Double.parseDouble(tfVal1.getText()),
							0,cBoxMsngUnit.getSelectedItem().toString(),
							messArtEnum.blutZucker);
					tfVal1.setText("");
					messWasCreated = true;
				}
				
				break;
				
			case GEWICHT:
				
				if (tfVal1.getText() != null )
				{
					this.mObjMessung = new Messung(this.getDateMessung(),Double.parseDouble(tfVal1.getText()),
							0,cBoxMsngUnit.getSelectedItem().toString(),
							messArtEnum.gewicht);
					tfVal1.setText("");
					messWasCreated = true;
				}	
				
				break;
			}
			
		} 
		catch (Exception e)
		{
			sbStaBarMainWin.setText("Ungültige Eingabe");
		} 
			
		if (messWasCreated)
		{	
			addMessungToLinkedList(mObjMessung);
			addTableEntry();
			messWasCreated = false;		
		}
	}
	
	/**
	 * fügt ein Messungsobjekt zur LinkedList<Messung> liLiMessungen hinzu
	 * @param m das hizuzufügende Messungsobjekt
	 */
	private void addMessungToLinkedList(Messung m)
	{
		liLiMessungen.add(m);
	}

	private void saveData()
		{
			
			FileWriter fw = null;
			
			try
			{
				fw = new FileWriter(fileSave, false);
				
				for( Messung m : liLiMessungen )
				{
					fw.append(m.getNumericDate() + ";" + m.getValue1() + ";" + m.getValue2() + ";" + m.getMessUnit() + ";" +  m.getmID() +  System.lineSeparator());
				}
				
				
			} 
			catch (Exception e)
			{}
			finally
			{
				if(fw != null)
					try
					{
						fw.close();
					} catch (Exception ex)
					{
						ex.printStackTrace();
					}
			}
	}

	/**
	 * liest eine Textdatei ein und erzeugt Messungen aus semikolon-separierten Zeilen.
	 */
	private void readData()
	{
		
		System.out.println( "loadData() " + fileSave );
		
		liLiMessungen.clear();
		Scanner in = null;
		
		try
		{
			in = new Scanner( new FileInputStream( fileSave ) );
			
			while( in.hasNext() )
			{
				String[] splitted = in.nextLine().split(";");

				switch (Integer.parseInt(splitted[4]))
				{
				case BLUTDRUCK:
					this.mObjMessung = new Messung(
							new Date(Long.parseLong(splitted[0])),
							Double.parseDouble(splitted[1]),
							Double.parseDouble(splitted[2]),
							splitted[3],
							messArtEnum.blutDruck);

					addMessungToLinkedList(mObjMessung);
					addTableEntry();
					break;
				
				case BLUTZUCKER:
					this.mObjMessung = new Messung(
							new Date(Long.parseLong(splitted[0])),
							Double.parseDouble(splitted[1]),
							Double.parseDouble(splitted[2]),
							splitted[3],
							messArtEnum.blutZucker);
					
					addMessungToLinkedList(mObjMessung);
					addTableEntry();
					break;
					
				case GEWICHT:
					this.mObjMessung = new Messung(
							new Date(Long.parseLong(splitted[0])),
							Double.parseDouble(splitted[1]),
							Double.parseDouble(splitted[2]),
							splitted[3] ,messArtEnum.gewicht);
					
					addMessungToLinkedList(mObjMessung);
					addTableEntry();
					break;

				default:
					break;
				}
				
			}
			
		} 
		catch (Exception ex)
		{}
		
		if( in != null )
			in.close();
	}
		
	
	/**
	 * 
	 * @return das Date aus dateMessung.
	 */
	public Date getDateMessung()
	{			
		return dateMessung;
	}

	/**
	 * 
	 * @param das zu setzende Messdatum, zur Übergabe an das tfMessZeit Textfeld.
	 */
	public void setDateMessung(Date dateMessung)
	{
		this.dateMessung = dateMessung;
		tfMessZeit.setText(sDForm.format(dateMessung).toString());
	}
	
	
	/**
	 * setzt den Text der Statusbar im Hauptfenster.
	 * @param s der zu setzende Text.
	 */
	public void setStatusBarText(String s)
	{
		sbStaBarMainWin.setText(s);		
	}

	/**
	 * @deprecated
	 * @param bool HilfsKlasse zum Setzen des Enabled Status des btnMessAdd.
	 */
	public void setbtnMessAddEnabledState( boolean bool)
	{	
		btnMessAdd.setEnabled(bool);
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
				createMessung();		
		
		else if (o == miLoad)
			readData();
		else if (o == miSave)
			saveData();
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
			updateTableData(filterTable(objArrTable));
		}
		
	}
	
	public static void main(String[] args)
		{
			MainWindow mw = new MainWindow();
			mw.Show();
		}
	
	
}
