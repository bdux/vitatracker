package vitaTracker.Util;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class WinUtil
{
	
	public static enum MenuItemType
	{
		ITEM_PLAIN, ITEM_CHECK, ITEM_RADIO
		
		
	}
	
	/**
	 * createMenu()
	 * <br>
	 * erzeugt ein Menue
	 * <br>
	 * @param menuBar Menueleiste
	 * @param menuText Menuetext
	 * @param shortKey Kurzschluessel
	 * @return Menue
	 *
	 */
	
	public static JMenu createMenu(
					JMenuBar menuBar, String menuText,String menuName, int shortKey)
	{
		JMenu menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuText);
		
		if ( shortKey > 0)
			menu.setMnemonic(shortKey);
		
		menuBar.add(menu);		
		
		return menu;
	}
	
	public static JMenuItem createMenuItem(
			JMenu menu, String miName, MenuItemType miType, ActionListener actionListener, 
			String miText, ImageIcon image, int shortKey, String miToolTip
				) 
	{
		JMenuItem menuItem = new JMenuItem();
		
		
		switch (miType)
		{
		case ITEM_CHECK:
			menuItem = new JCheckBoxMenuItem();
			break;
			
		case ITEM_RADIO:
			menuItem = new JRadioButtonMenuItem();
			break;

		
		}
		
		menuItem.setText(miText);
		menuItem.setName(miName);
		menuItem.setIcon(image);
		menuItem.setToolTipText(miToolTip);
		menuItem.addActionListener(actionListener);
		menuItem.setOpaque(false);
		menu.add(menuItem);
		
		
		
		return menuItem;
		
		
		
	}
	
	
	/**
	 * Untermenue erstellen
	 * @param mainMenu
	 * @param menuText
	 * @param menuName
	 * @param shortKey
	 * @return
	 */
	
	public static JMenu createSubMenu(JMenu mainMenu,String menuText, String menuName, int shortKey )
	{
		
		JMenu subMenu = new JMenu();
		
		subMenu.setText(menuText);
		subMenu.setName(menuName);
		
		if (shortKey > 0)
		subMenu.setMnemonic(shortKey);
		
		mainMenu.add(subMenu);
		
		return subMenu;
		
	}
	
	
	
	
}
