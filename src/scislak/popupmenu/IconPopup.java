package scislak.popupmenu;

import java.awt.Desktop;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import scislak.program.AboutMe;
import scislak.program.Stick;
import scislak.program.TableOfNotes;
import scislak.storage.NotesMemory;

public class IconPopup extends GeneralPopupMenu{
	private static Menu item;
	
	public IconPopup(JFrame frame) {
		super(frame);
	}
	
	@Override
	protected void create() {
		addNewStick(); 
		addShowAllNotes();
		addHideAllNotes();
		addNotebooks();
		addExplorer();
		addAbout();
		addHelp();
		addExit();
	}

	//Methods of options Popup icon
	private void addExit() {
		MenuItem item = new MenuItem("Exit");
		menu.add(item);
		item.addActionListener((ActionEvent e) -> {
			Stick.updateAllStickParameter();
			NotesMemory m = new NotesMemory();
			m.saveNotebooks();
			m.addAllToPref();
			System.exit(0);
		});
	}
	
	private void addHelp() {
		MenuItem item = new MenuItem("Help");
		menu.add(item);
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.simplestickynotes.com/help/?hl=pl&utm_source=ssn"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
				}
			}
		});
	}
	
	private void addAbout() {
		MenuItem item = new MenuItem("About");
		menu.add(item);
		item.addActionListener((ActionEvent e) -> {
			new AboutMe();
		});
	}
	
	private void addExplorer() {
		MenuItem item = new MenuItem("Explorer");
		menu.add(item);
		item.addActionListener((ActionEvent e) -> {
			new TableOfNotes();
		});
	}
	
	private void addNotebooks() {
		item = new Menu("Notebooks");
		menu.add(item);
		addNotebooksMenuItems();
	}
	
	public static void addNotebooksMenuItems() {
		item.removeAll();
		for(String notebook: NotesMemory.getNotesbooks()) {
			MenuItem notebookItem = new MenuItem(notebook);
			item.add(notebookItem);
			notebookItem.addActionListener((ActionEvent) -> {
				showAllSticksFromNotebook(notebook);
			});
		}
	}
	
	private static void showAllSticksFromNotebook(String notebook) {
		System.out.println(notebook);
	}
}
