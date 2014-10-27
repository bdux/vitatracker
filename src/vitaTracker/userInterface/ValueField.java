package vitaTracker.userInterface;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;


/**
 * 
 * @author Benjamin Dux
 *
 */
public class ValueField extends JFormattedTextField
{
	
	ValueVerifier vv;
	
	
	public ValueField()
	{
		super();
		vv = new ValueVerifier(this);
		this.setInputVerifier(vv);
		
		
	}
	
	
	public ValueField (DecimalFormat format)
	{
		this();
		
		this.setFormatter(new NumberFormatter(format));
		
		
	}
	
	
	
	/**
	 * 
	 * @author Benjamin Dux
	 * Überprüft die Eingabe der der Werte des owners
	 *
	 */
	class ValueVerifier extends InputVerifier
	{

		JComponent owner;
		
		/**
		 * 
		 * @param owner übergebener Owner
		 */
		
		public ValueVerifier(JComponent owner)
		{
			
			this.owner = owner;
		}
		
		
		@Override
	    public boolean verify(JComponent input) {
	        String text = ((JTextField) input).getText();
	        try {
	            BigDecimal value = new BigDecimal(text);
	            return (value.scale() <= Math.abs(2)); 
	                        
	        } catch (NumberFormatException e) {
	            System.out.println("fehler");
	        	JOptionPane.showMessageDialog(null, "Ungültige Eingabe");
	            return false;
	        }
	    }

	}
	
}
