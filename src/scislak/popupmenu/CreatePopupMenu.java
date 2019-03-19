package scislak.popupmenu;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import scislak.program.Stick;

public class CreatePopupMenu {
	private JPopupMenu menu;
	private JFrame frame;
	
	public void create(JFrame frame) {
		menu = new JPopupMenu();
		JMenuItem newStick = new JMenuItem("New Stick");
		JMenuItem deleteStick = new JMenuItem("Delete Stick");
		JMenuItem exit = new JMenuItem("Exit");
		
		menu.add(newStick);
		menu.add(deleteStick);
		menu.add(exit);
		frame.add(menu);
		
		addNewStickListener(newStick);
		addDeleteStickListener(deleteStick);
		addExitListener(exit);
	}
	
	public void show(Point e) {
		menu.show(frame, (int)e.getX(), (int)e.getY()+20);
	}
	
	//Implementation privates listeners
	private void addNewStickListener(JMenuItem item) {
		item.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Stick();
				menu.setVisible(false);
			}
		});
	}
	
	private void addDeleteStickListener(JMenuItem item) {
		
	}
	
	private void addExitListener(JMenuItem item) {
		item.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
