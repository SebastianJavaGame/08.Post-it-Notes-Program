package scislak.program;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AboutMe extends JFrame{
	private static JFrame frame;
	
	public AboutMe() {
		super("About me");
		frame = this;
		frameSetting();
		init();
	}
	
	private void frameSetting() {
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		this.setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});
	}
	
	private void init() {
		JTextArea area = new JTextArea();
		String text = "Autor: Sebastian Œciœlak\n\nStworzono na wzór programu 'Simple Sticky Notes'\nKod ¿ród³owy umieszczony na githubie konto 'SebastianJavaGame'\n\nSzukam pracy jako programista e-mail 'sebek2088s4@tlen.pl'";
		area.setEditable(false);
		area.setText(text);
		this.getContentPane().add(area);
	}
}
