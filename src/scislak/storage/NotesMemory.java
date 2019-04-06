package scislak.storage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class NotesMemory {
	private final static String PREF = "PREF";
	private Preferences prefs = Preferences.userRoot().node(PREF);
	private final static String NOTEBOOKS_SIZE = "NOTEBOOKS_SIZE";
	private final static String NOTEBOOK = "NOTEBOOK";
	private final static String NOTEBOOK_TYPE = "NOTEBOOK_TYPE";
	private final static String TITLE = "TITLE";
	private final static String NOTE_X = "LOCALIZATION_X";
	private final static String NOTE_Y = "LOCALIZATION_Y";
	private final static String NOTE_TEXT = "NOTE_TEXT"; 
	
	private static List<StickParameters> notes;
	private static List<String> notebooks;
	
	static {
		notes = new ArrayList<StickParameters>();
		notebooks = new ArrayList<String>();
	}
	
	public void addNote(StickParameters sp) {
		addNote(sp.getLocalization().x, sp.getLocalization().y, sp.getTitle(), sp.getNote(), sp.getNotebook());
	}
	
	public void addNote(int x, int y, String title, String note, String notebook) {
		notes.add(new StickParameters(x, y, title, note, notebook));
	}
	
	public void addNotebook(String notebook) {
		notebooks.add(notebook);
	}
	
	public void addAllToPref() {
		for(int i = 0; i < getStoagedSize(); i++) {
			StickParameters stick = notes.get(i);
			prefs.put(TITLE +i, stick.getTitle());
			prefs.putInt(NOTE_X +i, stick.getLocalization().x);
			prefs.putInt(NOTE_Y +i, stick.getLocalization().y);
			prefs.put(NOTE_TEXT +i, stick.getNote());
			prefs.put(NOTEBOOK_TYPE +i, stick.getNotebook());
		}
	}
	
	public void loadAllFromPref() {
		for(int i = 0; i < getStoagedSize(); i++) {
			int x = prefs.getInt(NOTE_X +i, 10);
			int y = prefs.getInt(NOTE_Y +i, 10);
			String title = prefs.get(TITLE +i, "");
			String notebook = prefs.get(NOTEBOOK_TYPE, StickParameters.DEFAULT_NOTEBOOK);
			String text = prefs.get(NOTE_TEXT +i, "");
			StickParameters stick = new StickParameters(x, y, title, text, notebook);
			addNote(stick);
		}
	}
	
	public void loadNotebooks() {
		int size = prefs.getInt(NOTEBOOKS_SIZE, 1);
		notebooks.clear();
		for(int i = 0; i < size; i++) {
			notebooks.add(i, prefs.get(NOTEBOOK +i, "User"));
		}
	}
	
	public void saveNotebooks() {
		prefs.putInt(NOTEBOOKS_SIZE, notebooks.size());
		for(int i = 0; i < notebooks.size(); i++) {
			prefs.put(NOTEBOOK +i, notebooks.get(i));
		}
	}
	
	public static void changeNotebook(int index, String name) {
		try {
			notebooks.set(index, name);
		}catch(ArrayIndexOutOfBoundsException e) {
		}
		catch(IndexOutOfBoundsException e1) {
		}
	}

	public static List<String> getNotesbooks(){
		return notebooks;
	}
	
	public static void changeNotebookOfNote(String oldName, String newName) {
		for(StickParameters stick: notes) {
			if(stick.getNotebook().equals(oldName))
				stick.setNotebook(newName);
		}
	}
	
	public List<StickParameters> getNotes(){
		return notes;
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
	
	public static void removeFromNotebook(int index) {
		notebooks.remove(index);
	}
	
	public static void setAllNotebooks(ArrayList<String> listOfNotebooks) {
		notebooks = listOfNotebooks;
	}
}
