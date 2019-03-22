package scislak.storage;

import java.awt.Point;

public class StickParameters {
	private Point localization;
	private String note;
	
	public StickParameters(int x, int y, String note) {
		this.localization = new Point(x, y);
		this.note = note;
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
}
