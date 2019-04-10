package scislak.popupmenu;

import javax.swing.JFrame;

public class CreatePopupMenu extends GeneralPopupMenu{
	
	public CreatePopupMenu(JFrame frame) {
		super(frame);
	}
	
	@Override
	protected void create() {
		addNewStick();
		addHideAllNotes();
		//addChangeColor();
		//addChangeOpacity();
		//addAlarm();
		//addPrint();
		//addShare();
		//addExport();
		//addEditTitle();
		//addDuplicate();
		//addAlwaysOnTop();
		//addLockNote();
		//addMinimize();
		//addHide();
		//addMoveTo();
		//addDeleteNote();
	}
	
	//Method of Popup Frame
}
