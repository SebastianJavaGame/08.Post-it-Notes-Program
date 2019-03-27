package scislak.program;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import scislak.storage.NotesMemory;
import scislak.storage.StickParameters;

public class TableOfNotes extends JFrame{
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	
	public TableOfNotes() {
		super("Explorer");
		frame = this;
		initFrame();
		initInside();
	}
	
	private void initFrame() {
		this.setSize(700, 500);
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
	
	private void initInside() {
		JPanel fillBackgroundPanel = new JPanel(null);
		JLabel titleNoteBook = new JLabel("Notebooks");
		JLabel titleNotes = new JLabel("Notes");
		JPanel notebookPanel = new Notebook();
		JPanel notesPanel = new Notes();
		
		
		this.getContentPane().add(fillBackgroundPanel);
		titleNoteBook.setBounds(10, 10, 190, 30);
		fillBackgroundPanel.add(titleNoteBook);
		titleNotes.setBounds(200, 10, 190, 30);
		fillBackgroundPanel.add(titleNotes);
		notebookPanel.setBounds(10, 50, 190, 400);
		fillBackgroundPanel.add(notebookPanel);
		notesPanel.setBounds(200, 50, 480, 400);
		fillBackgroundPanel.add(notesPanel);
	}
	
	class Notebook extends JPanel{
		private static final long serialVersionUID = 1L;
		private NotesMemory memory;
		private DefaultTableModel model;
		
		public Notebook() {
			super(new BorderLayout());
			memory = new NotesMemory();
			System.out.println(memory.getNotes().size());
			model = new DefaultTableModel();
			initTable();
		}
		
		private void initTable() {
			memory.loadNotebooks();
			
			JTable table = new JTable(model);
			model.addColumn("Notebook name");
			updateTableRow();
			JScrollPane scrollPane = new JScrollPane(table);
			JButton newNotebook = new JButton("Create notebook");
			table.setRowSelectionInterval(0, 0);
			this.add(BorderLayout.CENTER, scrollPane);
			this.add(BorderLayout.SOUTH, newNotebook);
			
			newNotebookListener(newNotebook);
		}
		
		private void newNotebookListener(JButton button) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					memory.addNotebook("Default_" + StickParameters.iterator);
					StickParameters.iterator++;
					updateTableRow();
				}
			});
		}
		
		private void updateTableRow() {	
			int i = model.getRowCount();
			for(String notebook: memory.getNotesbooks()) {
				if(i == 0) 
					model.addRow(new Object[] {notebook});
				i--;
			}
		}
	}
	
	class Notes extends JPanel{
		private static final long serialVersionUID = 1L;
		private NotesMemory memory;
		private DefaultTableModel model;
		
		public Notes() {
			super(new BorderLayout());
			memory = new NotesMemory();
			model = new DefaultTableModel();
			initTable();
		}
		
		private void initTable() {
			memory.loadNotebooks();
			
			JTable table = new JTable(model);
			model.addColumn("Title");
			model.addColumn("Notebook");
			model.addColumn("Created date");
			updateTableRow();
			JScrollPane scrollPane = new JScrollPane(table);
			this.add(BorderLayout.CENTER, scrollPane);
		}
		
		private void updateTableRow() {	
			for(StickParameters stick: memory.getNotes()) {
				model.addRow(new Object[] {stick.getTitle(), stick.getNotebook(), stick.getCreatedTime()});
			}
		}
	}
}
