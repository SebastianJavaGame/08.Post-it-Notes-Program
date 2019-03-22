package scislak.test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

import scislak.storage.NotesMemory;
import scislak.storage.StickParameters;

public class TestingStorage {

	@Test
	public void addNotesToStorage() {
		NotesMemory n = new NotesMemory();
		n.addNote(new StickParameters(10, 20, "Note1"));
		n.addNote(50, 89, "Note2");
		n.addNote(new StickParameters(80, 90, "Note3"));
		assertEquals(3, n.getStoagedSize());
	}
	
	@Test
	public void checkTrueNotes() {
		NotesMemory n = new NotesMemory();
		n.addNote(new StickParameters(90, 55, "Note1"));
		n.addNote(100, 89, "Note2");
		n.addNote(567, 821, "Note3");
		
		assertEquals("Note1", n.getText(0));
		assertEquals(100, n.getLocation(1).x);
		assertEquals(821, n.getLocation(2).y);
	}
	
	@Test
	public void changedData() {
		NotesMemory n = new NotesMemory();
		n.addNote(new StickParameters(645, 342, "Note1"));
		n.addNote(87, 434, "Note2");
		
		n.changeLocation(0, new Point(100, 200));
		n.changeText(1, "Arnold");
		
		assertEquals(100, n.getLocation(0).x);
		assertEquals(200, n.getLocation(0).y);
		assertEquals("Arnold", n.getText(1));
	}

}
