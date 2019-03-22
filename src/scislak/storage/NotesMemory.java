package scislak.storage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class NotesMemory {
	private final static String PREF = "PREF";
	private Preferences prefs = Preferences.systemRoot().node(PREF);
	private final static String NOTE_X = "LOCALIZATION_X";
	private final static String NOTE_Y = "LOCALIZATION_Y";
	private final static String NOTE_TEXT = "NOTE_TEXT"; 
	
	private List<StickParameters> notes;
	
	public NotesMemory() {
		notes = new ArrayList<StickParameters>();
	}
	
	public void addNote(StickParameters sp) {
		addNote(sp.getLocalization().x, sp.getLocalization().y, sp.getNote());
	}
	
	public void addNote(int x, int y, String note) {
		notes.add(new StickParameters(x, y, note));
	}
	
	public void addAllToPref() {
		for(int i = 0; i < getStoagedSize(); i++) {
			StickParameters stick = notes.get(i);
			prefs.putInt(NOTE_X +i, stick.getLocalization().x);
			prefs.putInt(NOTE_Y +i, stick.getLocalization().y);
			prefs.put(NOTE_TEXT +i, stick.getNote());
		}
	}
	
	public void loadAllToPref() {
		for(int i = 0; i < getStoagedSize(); i++) {
			int x = prefs.getInt(NOTE_X +i, 10);
			int y = prefs.getInt(NOTE_Y +i, 10);
			String text = prefs.get(NOTE_TEXT +i, "");
			StickParameters stick = new StickParameters(x, y, text);
			addNote(stick);
		}
	}

	public int getStoagedSize() {
		return notes.size();
	}
	
	public String getText(int index) {
		return notes.get(index).getNote();
	}
	
	public Point getLocation(int index) {
		return notes.get(index).getLocalization();
	}

	public void changeLocation(int i, Point point) {
		notes.get(i).setLocalization(point);
	}

	public void changeText(int i, String string) {
		notes.get(i).setNote(string);
	}
}
