package vitaTracker.Util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.*;

import vitaTracker.MainWindow;


/*
 * calendar.get() indizes:
 * 5: Calendar.DAY_OF_MONTH
 * 2: Calendar.MONTH
 * 1: Calendar.YEAR
 * 11: Calendar.HOUR_OF_DAY
 * 12: Calendar.MINUTE
 * 13: Calendar.SECOND
 * 
 * 
 * 
 */


public class DateTimePicker extends JFrame implements ActionListener, WindowListener
{
	
	private Calendar c;
	private static final int[] DATUM = {5,2,1,11,12,13};
	// private static final int[] DATUM = {Calendar.DAY_OF_MONTH,Calendar.MONTH,Calendar.YEAR,Calendar.YEAR,Calendar.MINUTE,Calendar.SECOND};
	
	private JButton[] plus 	= new JButton[6];
	private JTextField[] tf = new JTextField[6];
	private JButton[] minus = new JButton[6];
	private MainWindow owner;
	
	
	public static void main(String[] args)
	{
		DateTimePicker d = new DateTimePicker();

	}
	
	public DateTimePicker()
	{
		
//		DateTimePicker d = new DateTimePicker();
		c = Calendar.getInstance();
		initializeComponents();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	public DateTimePicker(MainWindow owner)
	{
		
		this();
		this.owner = owner;
		this.setLocationRelativeTo(owner);
		
	}
	

	private void initializeComponents()
	{
		this.setUndecorated(true);
		this.setLayout(new GridLayout(3,6));
		
		this.addWindowListener(this);
		this.setPreferredSize(new Dimension(240,75));
		
		
		ImageIcon imageIconUp = new ImageIcon(getClass().getResource("/resource/up.png"));
		imageIconUp.setImage(imageIconUp.getImage().getScaledInstance(35, 25, Image.SCALE_AREA_AVERAGING));
		
		ImageIcon imageIconDown = new ImageIcon(getClass().getResource("/resource/down.png"));
		imageIconDown.setImage(imageIconDown.getImage().getScaledInstance(35, 25, Image.SCALE_AREA_AVERAGING));
		
		for (int i = 0; i<6; i++)
		{
			plus[i]  = new JButton();
			plus[i].addActionListener(this);
			plus[i].setIcon(imageIconUp);
			this.add(plus[i]);
			
		}
		
		
		for (int i = 0; i<6; i++)
		{
			tf[i] = new JTextField();
			tf[i].setFont(tf[i].getFont().deriveFont(Font.BOLD));
			tf[i].setHorizontalAlignment(SwingConstants.CENTER);
			
			this.add(tf[i]);
		
		}
		
	
		
		
		for (int i = 0; i<6; i++)
		{
			minus[i]  = new JButton();
			minus[i].addActionListener(this);
			minus[i].setIcon(imageIconDown);
			this.add(minus[i]);
			
		}
		
		this.pack();
		setTextFields();
		
	}

	private void setTextFields()
	{
		for (int i = 0; i<6 ; i++)
		{
			
			int value = c.get(DATUM[i]);
			if(i == 1)
				value++;
			tf[i].setText(String.format("%02d", value));
			
		}
		
	}
	
	private void textFieldsToString()
	{
		System.out.println(String.format("%02d.%02d.%04d, %02d:%02d:%02d", 
				c.get(DATUM[0]),c.get(DATUM[1])+1,c.get(DATUM[2]),c.get(DATUM[3]),c.get(DATUM[4]),c.get(DATUM[5])
				
				));
		System.out.println("\n"+ this.toString());
		System.out.println(Integer.MAX_VALUE);
	}

	
	
	
	@Override
	public String toString()
	{
		return String.format("%04d%02d%02d%02d%02d%02d",
		c.get(DATUM[2]),c.get(DATUM[1])+1,c.get(DATUM[1]),c.get(DATUM[3]),c.get(DATUM[4]),c.get(DATUM[5]
				));
				
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		for (int i = 0; i<6; i++)
		{
			if(o == plus[i])
			{
				c.add(DATUM[i], 1);
				setTextFields();
			}
			if (o == minus[i])
			{
				c.add(DATUM[i], -1);
				setTextFields();
			}
		}
		
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		if (owner !=null)
		{
			
			owner.setDateMessung(c.getTime());
			
		}
		
//		textFieldsToString();
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		this.dispose();
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

}
