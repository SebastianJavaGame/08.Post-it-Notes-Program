package scislak.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import scislak.program.Stick;

public class CreatePopupMenu extends GeneralPopupMenu{
	
	public CreatePopupMenu(JFrame frame) {
		super(frame);
	}
	
	@Override
	protected void create() {
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
