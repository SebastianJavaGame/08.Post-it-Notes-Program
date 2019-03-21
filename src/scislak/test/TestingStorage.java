package scislak.test;

import static org.junit.Assert.*;

import org.junit.Test;

import scislak.storage.Note;
import scislak.storage.NotesMemory;

public class TestingStorage {

	@Test
	public void addNotesToStorage() {
		NotesMemory n = new NotesMemory();
		n.addNote(new Note("Note1"));
		n.addNote("Note2");
		n.addNote(new Note("Note3"));
		assertEquals(3, n.getStoagedSize());
	}
	
	@Test
	public void checkTrueNotes() {
		NotesMemory n = new NotesMemory();
		n.addNote(new Note("Note1"));
		n.addNote("Note2");
		n.addNote("Note3");
		
		assertEquals("Note1", n.getIndex(0));
		assertEquals("Note2", n.getIndex(1));
		assertEquals("Note3", n.getIndex(2));
	}
	
	@Test
	public void changedData() {
		NotesMemory n = new NotesMemory();
		n.addNote(new Note("Note1"));
		n.addNote("Note2");
		
		n.changeNote(0, "ABC");
		n.changeNote(1, "Arnold");
		
		assertEquals("ABC", n.getIndex(0));
		assertEquals("Arnold", n.getIndex(1));
	}

}
