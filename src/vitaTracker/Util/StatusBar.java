package vitaTracker.Util;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class StatusBar extends JLabel
{
	
	public StatusBar()
	{
		this.setBorder( LineBorder.createGrayLineBorder() );
		
		this.setLayout( new BorderLayout() );
		
	}
	
	public void setMessage(String message)
	{
		this.setText( " " + message );
	}

}
