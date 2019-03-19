package scislak.program;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import scislak.popupmenu.CreatePopupMenu;

public class Stick extends JFrame{	
	private static final long serialVersionUID = 1L;
	private static boolean isIcon = false;
	private CreatePopupMenu menu;
	private JTextArea area;
	private Point coord;
	
	public Stick(){
		super("title");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 300);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width)-10, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height)/5);
		setMinimumSize(new Dimension(100, 100));
		setLocationRelativeTo(null);
		setUndecorated(true);
		addInside();
		if(!isIcon) addIcon();
		setVisible(true);
		menu = new CreatePopupMenu();
		menu.create(this);
	}
	
	private void addInside() {
		coord = null;
		JPanel titleBar = new JPanel(new BorderLayout());
		JPanel titleBarButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel titleBarTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel title = new JLabel("New stick");
		JButton minimalizeButton = ClearButton.buttonAsImage("resources/menu.png");		
		JButton exitButton = ClearButton.buttonAsImage("resources/error.png");
		
		area = new JTextArea();
		area.setBackground(Color.YELLOW);
		area.setFont(new Font("Serif", Font.ITALIC, 15));
		titleBar.add(BorderLayout.CENTER, titleBarTitle);
		titleBar.add(BorderLayout.EAST, titleBarButtons);
		titleBarTitle.add(title);
		titleBarButtons.add(minimalizeButton);
		titleBarButtons.add(exitButton);
		add(BorderLayout.NORTH, titleBar);
		getContentPane().add(area);
		
		titleBar.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				coord = null;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				coord = e.getPoint();
				
			}			
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		titleBar.addMouseMotionListener(new MouseMotionListener() {			
			@Override
			public void mouseMoved(MouseEvent arg0) {}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Point currCoords = arg0.getLocationOnScreen();
				setLocation(currCoords.x - coord.x, currCoords.y - coord.y);
			}
		});
		
		exitButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO change to dispose only this frame no exit app
				System.exit(0);
			}
		});
		
		minimalizeButton.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.show(minimalizeButton.getLocationOnScreen());
			}
		});
	}
	
	private void addIcon() {
		Toolkit mainToolkit = Toolkit.getDefaultToolkit();
		SystemTray mainTray = SystemTray.getSystemTray();
		Image trayIconImage = mainToolkit.getImage("resources/logo.png");
		TrayIcon mainTrayIcon = new TrayIcon(trayIconImage);
		mainTrayIcon.setImageAutoSize(true);
		try {
			mainTray.add(mainTrayIcon);
			isIcon = true;
		} catch (AWTException e) {}
		mainTrayIcon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("adf");
			}
		});
	}
}
