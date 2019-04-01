package scislak.program;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import scislak.storage.NotesMemory;
import scislak.storage.StickParameters;

public class TableOfNotes extends JFrame{
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private NotesMemory memory;
	private DefaultTableModel noteBookModel;
	private DefaultTableModel noteModel;
	private JTable notebookTable;
	
	public TableOfNotes() {
		super("Explorer");
		frame = this;
		memory = new NotesMemory();
		noteBookModel = new DefaultTableModel();
		noteModel = new DefaultTableModel();
		notebookTable = new JTable(noteBookModel);
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
	
	protected void updateNoteTableRow(String filterNotebook) {
		clearNotesTable();
		System.out.println(memory.getNotes().size());
		for(StickParameters stick: memory.getNotes()) {
			if(stick.getNotebook().equals(filterNotebook))
				addNoteAsRow(stick);
		}
	}
	
	private void addNoteAsRow(StickParameters stick) {
		noteModel.addRow(new Object[] {stick.getTitle(), stick.getNotebook(), stick.getCreatedTime()});
	}
	
	private void clearNotesTable() {
		while(noteModel.getRowCount() > 0)
		    noteModel.removeRow(0);
	}
	
	private String getSelectedNotebookName() {
		return notebookTable.getValueAt(notebookTable.getSelectedRow(), 0).toString();
	}
	
	class Notebook extends JPanel{
		private static final long serialVersionUID = 1L;
		
		public Notebook() {
			super(new BorderLayout());
			initTable();
		}
		
		private void initTable() {
			memory.loadNotebooks();
			
			notebookTable = new JTable(noteBookModel);
			noteBookModel.addColumn("Notebook name");
			updateNotebookTableRow();
			JScrollPane scrollPane = new JScrollPane(notebookTable);
			
			notebookTable.setRowSelectionInterval(0, 0);
			this.add(BorderLayout.CENTER, scrollPane);
			this.add(BorderLayout.SOUTH, new ManageExplorerFrame());
		}
	}
	
	class ManageExplorerFrame extends JPanel{
		private static final long serialVersionUID = 1L;

		public ManageExplorerFrame() {
			super(new BorderLayout());
			init();
		}
		
		private void init() {
			JButton newNotebook = new JButton("Create notebook");
			JButton newNote = new JButton("Create new note");
			JButton deleteNotebook = new JButton("Delete notebook");
			
			newNotebookListener(newNotebook);
			changeNameListener();
			filterNotesOfNotebook();
			newNoteListener(newNote);
			deleteNotebookListener(deleteNotebook);
			
			this.add(BorderLayout.NORTH, newNotebook);
			this.add(BorderLayout.CENTER, newNote);
			this.add(BorderLayout.SOUTH, deleteNotebook);
		}
		
		private void deleteNotebookListener(JButton button) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(noteBookModel.getRowCount() > 1) {
						int index = notebookTable.getSelectedRow();
						noteBookModel.removeRow(index);
						
						if(index > 0)
							notebookTable.setRowSelectionInterval(index-1, index-1);
						else
							notebookTable.setRowSelectionInterval(index, index);
					}
				}
			});
		}
		
		private void filterNotesOfNotebook() {
			notebookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		        public void valueChanged(ListSelectionEvent event) {
		            updateNoteTableRow(getSelectedNotebookName());
		        }
		    });
		}
		
		private void changeNameListener() {
			//TODO
		}
		
		private void newNoteListener(JButton button) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					StickParameters stick = new StickParameters(getX(), getY(), "New Note", "Empty", getSelectedNotebookName());
					addNoteAsRow(stick);
					memory.addNote(stick);
				}
			});
		}
		
		private void newNotebookListener(JButton button) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					memory.addNotebook("Default_" + StickParameters.iterator);
					StickParameters.iterator++;
					TableOfNotes.this.updateNotebookTableRow();
				}
			});
		}
	}
	
	protected void updateNotebookTableRow() {	
		int i = noteBookModel.getRowCount();
		for(String notebook: memory.getNotesbooks()) {
			if(i == 0) 
				noteBookModel.addRow(new Object[] {notebook});
			i--;
		}
	}
	
	class Notes extends JPanel{
		private static final long serialVersionUID = 1L;
		private NotesMemory memory;
		
		public Notes() {
			super(new BorderLayout());
			memory = new NotesMemory();
			initTable();
		}
		
		private void initTable() {
			memory.loadNotebooks();
			
			JTable notesTable = new JTable(noteModel);
			noteModel.addColumn("Title");
			noteModel.addColumn("Notebook");
			noteModel.addColumn("Created date");
			TableOfNotes.this.updateNoteTableRow(getSelectedNotebookName());
			JScrollPane scrollPane = new JScrollPane(notesTable);
			this.add(BorderLayout.CENTER, scrollPane);
		}
	}
}
