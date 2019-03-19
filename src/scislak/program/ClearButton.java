package scislak.program;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ClearButton {
	public static JButton buttonAsImage(String path) {
		JButton button = new JButton(new ImageIcon(path));
		button.setBorderPainted(false);
		button.setBorder(null);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setContentAreaFilled(false);
		
		return button;
	}
}
