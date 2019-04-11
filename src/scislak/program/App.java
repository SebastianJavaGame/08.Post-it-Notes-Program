package scislak.program;

import scislak.storage.NotesMemory;

public class App {
	public static void main(String[] args) {
		NotesMemory m = new NotesMemory();
		m.loadAllFromPref();
		m.loadNotebooks();
		m.createSticksFromParameters();
		
		if(NotesMemory.getSticks().size() == 0)
			new Stick();
	}
}
