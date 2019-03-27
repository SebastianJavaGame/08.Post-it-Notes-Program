package scislak.popupmenu;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import scislak.program.AboutMe;
import scislak.program.TableOfNotes;

public class IconPopup extends GeneralPopupMenu{

	public IconPopup(JFrame frame) {
		super(frame);
	}
	
	@Override
	protected void create() {
		addNewStick(); 
		//addNewNoteFromClipboard();
		//addShowAllNotes();
		//addHideAllNotes();
		//addNotebooks();
		addExplorer();
		//addStore();
		//addSetting();
		addAbout();
		addHelp();
		addExit();
	}
	
	//Methods of options Popup icon
	private void addExit() {
		MenuItem item = new MenuItem("Exit");
		menu.add(item);
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void addAbout() {
		MenuItem item = new MenuItem("About");
		menu.add(item);
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutMe();
			}
		});
	}
	
	private void addExplorer() {
		MenuItem item = new MenuItem("Explorer");
		menu.add(item);
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new TableOfNotes();
			}
		});
	}
}
