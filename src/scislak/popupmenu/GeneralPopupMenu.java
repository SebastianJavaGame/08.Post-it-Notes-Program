package scislak.popupmenu;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public abstract class GeneralPopupMenu {
	protected JPopupMenu menu;
	protected JFrame frame;
	
	public GeneralPopupMenu(JFrame frame) {
		this.frame = frame;
		create();
	}
	
	protected abstract void create();
	
	public void show(Point point) {
		menu.show(frame, (int)point.getX()-frame.getX(), (int)point.getY()+20-frame.getY());
	}
	
	
}
