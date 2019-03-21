package scislak.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class NotesMemory {
	private final static String pref = "PREF";
	private Preferences prefs = Preferences.systemRoot().node(pref);
	private List<Note> notes;
	
	public NotesMemory() {
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note note) {
		addNote(note.getNote());
	}
	
	public void addNote(String note) {
		notes.add(new Note(note));
	}

	public int getStoagedSize() {
		return notes.size();
	}

	public String getIndex(int i) {
		return notes.get(i).getNote();
	}

	public void changeNote(int i, String string) {
		notes.get(i).setNote(string);
	}

}
