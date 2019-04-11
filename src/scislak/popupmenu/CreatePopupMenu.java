package scislak.popupmenu;

import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;

import scislak.program.Stick;
import scislak.storage.NotesMemory;
import scislak.storage.StickParameters;

public class CreatePopupMenu extends GeneralPopupMenu{
	
	public CreatePopupMenu(JFrame frame) {
		super(frame);
	}
	
	@Override
	protected void create() {
		addNewStick();
		addHideAllNotes();
		addEditTitle();
		addDuplicate();
		addAlwaysOnTop();
		addDeleteNote();
	}
	
	private void addDeleteNote() {
		MenuItem item = new MenuItem("Delete note");
		menu.add(item);
		item.addActionListener((ActionEvent e) -> {
			for(StickParameters stick: NotesMemory.getNotes()) {
				if(stick.getFrame() == frame) {
					frame.dispose();
					int index = NotesMemory.getNotes().indexOf(stick);
					NotesMemory.getNotes().remove(index);
					NotesMemory.getSticks().remove(index);
					return;
				}
			}
		});
	}
	
	private void addAlwaysOnTop() {
		CheckboxMenuItem item = new CheckboxMenuItem("Always on top");
		menu.add(item);
		item.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				for(Stick stick: NotesMemory.getSticks()) {
					if(stick.getFrame() == frame) {
						if(item.getState()) {
							stick.getFrame().setAlwaysOnTop(true);
						}else {
							stick.getFrame().setAlwaysOnTop(false);
						}
					}
				}
			}
		});
	}
	
	private void addDuplicate() {
		MenuItem item = new MenuItem("Duplicate");
		menu.add(item);
		item.addActionListener((ActionEvent e) -> {
			for(Stick stick: NotesMemory.getSticks()) {
				if(stick.getFrame() == frame) {
					Stick st = new Stick();
					st.setArea(stick.getArea());
					return;
				}
			}
		});
	}
	
	private void addEditTitle() {
		MenuItem item = new MenuItem("Edit title");
		menu.add(item);
		item.addActionListener((ActionEvent e) -> {
			for(Stick stick: NotesMemory.getSticks()) {
				if(stick.getFrame() == frame) {
					stick.changeTitle();
				}
			}
		});
	}
}
