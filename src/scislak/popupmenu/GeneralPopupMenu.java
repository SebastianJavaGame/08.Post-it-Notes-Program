package scislak.popupmenu;

import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public abstract class GeneralPopupMenu {
	protected PopupMenu menu;
	protected JFrame frame;
	
	public GeneralPopupMenu(JFrame frame) {
		this.frame = frame;
		menu = new PopupMenu();
		create();
		frame.add(menu);
	}
	
	protected abstract void create();
	
	public void show(Point point) {
		menu.show(frame, (int)point.getX()-frame.getX(), (int)point.getY()+20-frame.getY());
	}
	
	protected void addNewStick() {
		MenuItem item = new MenuItem("New stick");
		menu.add(item);
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Option 1");
			}
		});
	}
	
	public PopupMenu getPopupMenu() {
		return menu;
	}
}
