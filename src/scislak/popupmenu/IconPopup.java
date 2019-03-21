package scislak.popupmenu;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class IconPopup extends GeneralPopupMenu{

	public IconPopup(JFrame frame) {
		super(frame);
	}
	
	public void create() {
		menu = new JPopupMenu();
		JMenuItem newStick = new JMenuItem("New Stick");
		JMenuItem deleteStick = new JMenuItem("Delete Stick");
		JMenuItem exit = new JMenuItem("Exit");
		
		menu.add(newStick);
		menu.add(deleteStick);
		menu.add(exit);
		frame.add(menu);
	}
}
