package scislak.popupmenu;

import javax.swing.JFrame;

public class IconPopup extends GeneralPopupMenu{

	public IconPopup(JFrame frame) {
		super(frame);
	}
	
	@Override
	protected void create() {
		addNewStick(); 
		addNewNoteFromClipboard();
		addShowAllNotes();
		addHideAllNotes();
		addNotebooks();
		addExplorer();
		addStore();
		addSetting();
		addAbout();
		addHelp();
		addExit();
	}
}
