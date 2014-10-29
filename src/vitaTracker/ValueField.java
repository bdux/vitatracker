package vitaTracker;

import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.*;


import javax.swing.*;

/**
 * 
 * @author Benjamin Dux
 *
 */
public class ValueField extends JTextField implements KeyListener, FocusListener
{
	
	MainWindow caller;

	protected final double MAX_GLUCOVALUE = 500.0, MAX_METRICWEIGHT = 250.0, MAX_IMPERIALWEIGHT = 362.2;
	protected final double MIN_VALUE = 0.0;

	
	public ValueField()
	{
		super();
		
		this.addKeyListener(this);
		this.addFocusListener(this);
		
	}
	
	
	public ValueField(MainWindow caller)
	{
		this();
		this.caller = caller;
				
	}
	
	
	public boolean validate(String s)
	{
		boolean retval = false;
		
		try
		{
			Double.parseDouble(s);
			retval = true;
			caller.setStatusBarText("Bereit");
		}
		catch(Exception ex)
		{
//			JOptionPane.showMessageDialog(null, "Fehlerhafte Eingabe", "Fehlerhafte Eingabe", JOptionPane.ERROR_MESSAGE);
			caller.setStatusBarText("Ungültige Eingabe.");
		}
		
			
		return retval;
	}
	

	private double convertTextField2Value(ValueField tf, KeyEvent e)
	{
		// Das Zeichen kann irgendwo innerhalb des Textfeldes 
		// hinzugefügt worden sein.

		// Hilfzeichenkette initialisieren
		//
		String tmpText = "";
		
		// Position, an der das Zeichen eingefügt wurde, ermitteln
		//
		int charPos = tf.getSelectionStart();
		
		// Laufvariable für die while-Schleife
		//
		int i = 0;

		// Wenn das Zeichen irgendwo innerhalb des Textes eingefügt wurde
		//
		if (charPos < tf.getText().length())
		{
			// Hilfszeichenkette aufbereiten, indem die Zeichen aus dem
			// Textfeld und das Zeichen KeyEvent übernommen werden.
			//
			while (i < tf.getText().length())
			{
				// Hier muss das Zeichen aus dem KeyEvent eingefügt werden.
				if (i == charPos)
				{
					tmpText += e.getKeyChar();
					
					// Alle restlichen Zeichen aus dem Textfeld ab der
					// Laufvariablen i übernehmen.
					//
					tmpText += tf.getText().substring(i);
					break;
				}

				// Zeichen aus dem TextFeld übernehmen.
				//
				tmpText += tf.getText().charAt(i++);

			}
		}
		// Das Zeichen wurde am Ende hinzugefügt
		else
			tmpText = tf.getText() + e.getKeyChar();

		return Double.parseDouble(tmpText);

	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		KeyboardFocusManager.
		getCurrentKeyboardFocusManager().
		focusNextComponent();
		
	}


	@Override
	public void keyReleased(KeyEvent e) {}


	@Override
	public void keyTyped(KeyEvent e)
	{
		ValueField vf = null;

		
		if (e.getSource() instanceof ValueField)
			vf = (ValueField) e.getSource();
		else
			return;

		// Steuertasten ignorieren
		if (Character.isISOControl(e.getKeyChar()))
			return;
							
		// Überprüfung auf Ziffer 0 - 9
		if (e.getKeyChar()!=KeyEvent.VK_PERIOD & !Character.isDigit(e.getKeyChar())  )
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			caller.setStatusBarText("Ungültige Eingabe!");
			return;
		}
		
		vf.replaceSelection("");

		// Überprüfung der maximal einzugebenden Zeichen.
		// Es ist zu berücksichtigen, dass das Zeichen im KeyEvent
		// erst auf dem Weg in das Textfeld ist.
		// Wenn also die Anzahl der Zeichen im Textfeld bereits >= 3 ist,
		// darf kein weiteres Zeichen mehr angenommen werden.
		if (vf.getText().length() >= 6)
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			return;
		}

		// Überprüfung auf den Maximalwert:

		// Den Inhalt des konvertierten Textfeldes inklusive des Zeichens aus
		// dem Event auf Überschreitung des Maximalwerts prüfen
		if (convertTextField2Value(vf, e) > MAX_GLUCOVALUE)
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			return;
		}
		
		
	}
	



	@Override
	public void focusGained(FocusEvent e)
	{}


	@Override
	public void focusLost(FocusEvent e)
	{
		Object o = e.getSource();
		if (o instanceof ValueField)
		{
			if(	validate(this.getText()))
			{
				caller.setStatusBarText("Bereit.");
		
			}					
		}
	}
	
}
