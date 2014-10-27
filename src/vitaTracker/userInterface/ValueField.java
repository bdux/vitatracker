package vitaTracker.userInterface;

import java.awt.Toolkit;
import java.awt.event.*;
import java.text.*;

import javax.swing.*;
import javax.swing.text.NumberFormatter;



/**
 * 
 * @author Benjamin Dux
 *
 */
public class ValueField extends JTextField implements KeyListener, FocusListener
{
	
	MainWindow caller;
	
	protected final double MAX_GlUCOVALUE = 500.0, MAX_METRICWEIGHT = 250.0, MAX_IMPERIALWEIGHT = 362.2;
	protected final double MIN_VALUE = 0.0;

	
	
	public ValueField(MainWindow caller)
	{
		super();
		this.caller = caller;
		this.addKeyListener(this);
		this.addFocusListener(this);
		
		
	}
	
	
	private int getSelectedUnits()
	{
		int retval = 0;
		
		
		
		
		
		return retval;
	}
	
	
	public boolean validate(String s)
	{
		boolean retval = false;
		
		try
		{
			if (s == "")
			{
				caller.setbtnMessAddEnabledState(false);
				return retval;
				
			}
			else
				
			Double.parseDouble(s);
			retval = true;
			caller.setStatusBarText("Bereit");
		}
		catch(Exception ex)
		{
//			JOptionPane.showMessageDialog(null, "Fehlerhafte Eingabe", "Fehlerhafte Eingabe", JOptionPane.ERROR_MESSAGE);
			caller.setStatusBarText("Ung�ltige Eingabe.");
		}
		
			
		return retval;
	}
	

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e)
	{
		JTextField tf = null;

		
		if (e.getSource() instanceof ValueField)
			tf = (ValueField) e.getSource();
		else
			return;

		// Steuertasten ignorieren
		if (Character.isISOControl(e.getKeyChar()))
			return;
							
		// �berpr�fung auf Ziffer 0 - 9
		if (!Character.isDigit(e.getKeyChar()) )
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			caller.setStatusBarText("Ung�ltige Eingabe!");
			return;
		}
		
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && tf.getText()=="")
		{
			caller.setStatusBarText("Bereit");
			caller.setbtnMessAddEnabledState(false);
		}

		// Zuerst die markierten Zeichen l�schen.
		tf.replaceSelection("");

		
		if (tf.getText().length() >= 6)
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			return;
		}

		// �berpr�fung auf den Maximalwert:

		// Den Inhalt des konvertierten Textfeldes inklusive des Zeichens aus
		// dem Event auf �berschreitung des Maximalwerts pr�fen
		
		
		
		if (convertTextField2Value(tf, e) > MAX_GlUCOVALUE)
		{
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			return;
		}
		
	}
	
	
	private double convertTextField2Value(JTextField tf, KeyEvent e)
	{
		// Das Zeichen kann irgendwo innerhalb des Textfeldes 
		// hinzugef�gt worden sein.

		// Hilfzeichenkette initialisieren
		//
		String tmpText = "";
		
		// Position, an der das Zeichen eingef�gt wurde, ermitteln
		//
		int charPos = tf.getSelectionStart();
		
		// Laufvariable f�r die while-Schleife
		//
		int i = 0;

		// Wenn das Zeichen irgendwo innerhalb des Textes eingef�gt wurde
		//
		if (charPos < tf.getText().length())
		{
			// Hilfszeichenkette aufbereiten, indem die Zeichen aus dem
			// Textfeld und das Zeichen KeyEvent �bernommen werden.
			//
			while (i < tf.getText().length())
			{
				// Hier muss das Zeichen aus dem KeyEvent eingef�gt werden.
				if (i == charPos)
				{
					tmpText += e.getKeyChar();
					
					// Alle restlichen Zeichen aus dem Textfeld ab der
					// Laufvariablen i �bernehmen.
					//
					tmpText += tf.getText().substring(i);
					break;
				}

				// Zeichen aus dem TextFeld �bernehmen.
				//
				tmpText += tf.getText().charAt(i++);

			}
		}
		// Das Zeichen wurde am Ende hinzugef�gt
		else
			tmpText = tf.getText() + e.getKeyChar();

		return Double.parseDouble(tmpText);

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
				caller.setbtnMessAddEnabledState(true);

			}
			
			else
			{
				caller.setbtnMessAddEnabledState(false);
				
			}					
		}
	}
	
}
