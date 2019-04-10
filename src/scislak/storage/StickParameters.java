package scislak.storage;

import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;

public class StickParameters {
	public static int iterator = 0;
	public static final String DEFAULT_NOTEBOOK = "User";
	private String title;
	private Point localization;
	private String note;
	private String notebook;
	private LocalDateTime created;
	private JFrame frame;
	
	public StickParameters(int x, int y, String title, String note, String notebook, JFrame frame) {
		this.localization = new Point(x, y);
		this.title = title;
		this.note = note;
		this.notebook = notebook;
		this.frame = frame;
		created = LocalDateTime.now();
	}
	
	public StickParameters(int x, int y, String note, JFrame frame) {
		this.localization = new Point(x, y);
		this.title = "New Note";
		this.note = note;
		this.notebook = DEFAULT_NOTEBOOK;
		this.frame = frame;
		created = LocalDateTime.now();
	}

	public Point getLocalization() {
		return localization;
	}

	public void setLocalization(Point localization) {
		this.localization = localization;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNotebook() {
		return notebook;
	}

	public void setNotebook(String notebook) {
		this.notebook = notebook;
	}
	
	public String getCreatedTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dtf.format(created);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}	
}
