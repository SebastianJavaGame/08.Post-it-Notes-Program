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
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import scislak.popupmenu.CreatePopupMenu;
import scislak.popupmenu.IconPopup;

public class Stick {
    private JFrame frame = new JFrame();
    private CreatePopupMenu menuStick;
    private IconPopup menuIcon;
    private static boolean isIcon = false;
    
    public Stick() {
    	createAnsShowGui();
    	createFunctionality();
    	menuStick = new CreatePopupMenu(frame);
    	menuIcon = new IconPopup(frame);
    }

    class MainPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public MainPanel() {
        	setLayout(new BorderLayout());
            JTextArea area = new JTextArea();
    		area.setBackground(Color.YELLOW);
    		area.setFont(new Font("Serif", Font.ITALIC, 15));
    		area.setLineWrap(true);
    		add(BorderLayout.CENTER, area);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }
    }

    class BorderPanel extends JPanel {
		private static final long serialVersionUID = 1L;
        int pX, pY;

        public BorderPanel() {
        	setLayout(new BorderLayout());
        	JPanel titleBarButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    		JPanel titleBarTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	
    		JLabel title = new JLabel("New stick");
        	JButton minimalizeButton = ClearButton.buttonAsImage("resources/menu.png");		
    		JButton exitButton = ClearButton.buttonAsImage("resources/error.png");	
    		
    		add(BorderLayout.CENTER, titleBarTitle);
    		add(BorderLayout.EAST, titleBarButtons);
    		titleBarTitle.add(title);
    		titleBarButtons.add(minimalizeButton);
    		titleBarButtons.add(exitButton);
    		
    		minimalizeButton.addMouseListener(new MouseAdapter() {			
    			@Override
    			public void mouseClicked(MouseEvent e) {
    				menuStick.show(minimalizeButton.getLocationOnScreen());
    			}
    		});
    		
            exitButton.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    System.exit(0);
                }
            });
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    pX = me.getX();
                    pY = me.getY();

                }

                 public void mouseDragged(MouseEvent me) {

                    frame.setLocation(frame.getLocation().x + me.getX() - pX,
                            frame.getLocation().y + me.getY() - pY);
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent me) {

                    frame.setLocation(frame.getLocation().x + me.getX() - pX,
                            frame.getLocation().y + me.getY() - pY);
                }
            });
        }
    }

    class OutsidePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public OutsidePanel() {
            setLayout(new BorderLayout());
            add(new MainPanel(), BorderLayout.CENTER);
            add(new BorderPanel(), BorderLayout.PAGE_START);
            setBorder(new LineBorder(Color.white, 3));
        }
    }

    private void createAnsShowGui() {
        ComponentResizer cr = new ComponentResizer();
        cr.setMinimumSize(new Dimension(120, 100));
        cr.setMaximumSize(new Dimension(800, 600));
        cr.registerComponent(frame);
        cr.setSnapSize(new Dimension(10, 10));
        frame.setUndecorated(true);
        frame.add(new OutsidePanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void createFunctionality() {
    	if(!isIcon) addIcon();
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
				menuIcon.show(new Point(100, 100));
			}
		});
	}
}