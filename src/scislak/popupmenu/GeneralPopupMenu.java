package scislak.popupmenu;

import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import scislak.program.AboutMe;
import scislak.program.Stick;
import scislak.storage.NotesMemory;
import scislak.storage.StickParameters;

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
				new Stick();
			}
		});
	}
	
	protected void addShowAllNotes() {
		MenuItem item = new MenuItem("Show all notes");
		menu.add(item);
		item.addActionListener((ActionEvent e) -> {
			for(Stick stick: NotesMemory.getSticks()) {
				stick.setShow();
			}
		});
	}
	
	protected void addHideAllNotes() {
		MenuItem item = new MenuItem("Hide all notes");
		menu.add(item);
		item.addActionListener((ActionEvent e) -> {
			for(Stick st: NotesMemory.getSticks()) {
				st.updateStickParameters();
				st.setHide();
			}
		});
	}
	
	public PopupMenu getPopupMenu() {
		return menu;
	}
}
