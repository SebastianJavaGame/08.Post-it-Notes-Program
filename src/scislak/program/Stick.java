package scislak.program;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import scislak.popupmenu.CreatePopupMenu;
import scislak.popupmenu.IconPopup;
import scislak.storage.NotesMemory;
import scislak.storage.StickParameters;

public class Stick {
    private JFrame frame = new JFrame();
    private static NotesMemory memory = new NotesMemory();
    private static String notebookType = "User";
    private JTextField title;
    private JTextArea area;
    private CreatePopupMenu menuStick;
    private IconPopup menuIcon;
    private static boolean isIcon = false;
    
    public Stick() {
    	menuStick = new CreatePopupMenu(frame);
    	menuIcon = new IconPopup(frame);
    	createAnsShowGui();
    	createFunctionality();
    	memory.addNote(frame.getX(), frame.getY(), title.getText(), area.getText(), notebookType, frame);
    	NotesMemory.addStick(this);
    }
    
    public Stick(StickParameters stickParam) {
    	System.out.println(stickParam.getLocalization());
    	menuStick = new CreatePopupMenu(frame);
    	menuIcon = new IconPopup(frame);
    	createAnsShowGui();
    	createFunctionality();
    	stickParam.setFrame(frame);
    	frame.setLocation(stickParam.getLocalization());
    	title.setText(stickParam.getTitle());
    	area.setText(stickParam.getNote());
    }

    class MainPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public MainPanel() {
        	setLayout(new BorderLayout());
            area = new JTextArea();
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
        	
    		title = new JTextField("New stick");
    		title.setEditable(false);
    		title.setFocusable(false);
    		title.setBorder(javax.swing.BorderFactory.createEmptyBorder());
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
                    frame.dispose();
                    updateStickParameters();
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
            
            title.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					int i =0;
					for(Stick stick: NotesMemory.getSticks()) {
						if(Stick.this == stick) {
							NotesMemory.getNotes().get(i).setTitle(title.getText());
							offActive();
							return;
						}
						i++;
					}					
				}
				
				private void offActive() {
					title.setEditable(false);
					title.setFocusable(false);
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					title.selectAll();
				}
			});
        }
    }
    
    public void changeTitle() {
		title.setEditable(true);
		title.setFocusable(true);
		title.requestFocus();
	}
    
    public void updateStickParameters() {
		for(int i = 0; i < NotesMemory.getNotes().size(); i++) {
			if(NotesMemory.getNotes().get(i).getFrame() == this.frame) {
				StickParameters stick = NotesMemory.getNotes().get(i);
				stick.setLocalization(frame.getLocation());
				stick.setNote(area.getText());
				stick.setTitle(title.getText());
				return;
			}
		}
	}
    
    public static void updateAllStickParameter() {
    	for(int i = 0; i < NotesMemory.getNotes().size(); i++) {
			StickParameters stick = NotesMemory.getNotes().get(i);
			stick.setLocalization(stick.getFrame().getLocation());
			stick.setNote(stick.getNote());
			stick.setTitle(stick.getTitle());
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
    	if(!isIcon) {
    		isIcon = true;
    		memory.loadNotebooks();
    		addIcon();
    		IconPopup.addNotebooksMenuItems();
    	}
    }
    
    private void addIcon() {
    	TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("resources/logo.png");
            trayIcon = new TrayIcon(image, "Tray Demo", menuIcon.getPopupMenu());
            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
	}
    
    public static void setNotebookType(String type) {
    	notebookType = type;
    }
    
    public void setHide() {
    	frame.setVisible(false);
    }
    
    public void setShow() {
    	frame.setVisible(true);
    }
    
    public JFrame getFrame() {
    	return frame;
    }
    
    public String getArea() {
    	return area.getText();
    }

	public void setArea(String area) {
		this.area.setText(area);
	}
}