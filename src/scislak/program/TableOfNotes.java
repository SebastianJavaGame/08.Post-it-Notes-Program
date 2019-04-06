package scislak.program;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import scislak.popupmenu.IconPopup;
import scislak.storage.NotesMemory;
import scislak.storage.StickParameters;

public class TableOfNotes extends JFrame{
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private static NotesMemory memory = new NotesMemory();
	private DefaultTableModel notebookModel;
	private DefaultTableModel noteModel;
	private JTable notebookTable;
	private String  notebookNameBeforeChangeName;
	private int deletingIndex;
	int tmp = 0;
	
	public TableOfNotes() {
		super("Explorer");
		frame = this;
		notebookModel = new DefaultTableModel();
		noteModel = new DefaultTableModel();
		notebookTable = new JTable(notebookModel);
		notebookNameBeforeChangeName = "";
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
				memory.saveNotebooks();
				memory.addAllToPref();
				IconPopup.addNotebooksMenuItems();
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
		clearTable(noteModel);
		for(StickParameters stick: memory.getNotes()) {
			if(stick.getNotebook().equals(filterNotebook))
				addNoteAsRow(stick);
		}
	}
	
	private void addNoteAsRow(StickParameters stick) {
		noteModel.addRow(new Object[] {stick.getTitle(), stick.getNotebook(), stick.getCreatedTime()});
	}
	
	private void clearTable(DefaultTableModel modelTmp) {
		while(modelTmp.getRowCount() > 0)
		    modelTmp.removeRow(0);
	}
	
	class Notebook extends JPanel{
		private static final long serialVersionUID = 1L;
		
		public Notebook() {
			super(new BorderLayout());
			initTable();
		}
		
		private void initTable() {
			memory.loadNotebooks();
			
			notebookTable = new JTable(notebookModel);
			notebookModel.addColumn("Notebook name");
			JScrollPane scrollPane = new JScrollPane(notebookTable);
			
			updateNotebookTableRow();
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
			clickRowOfNotebookListener();
			changeNameListener();
			filterNotesWithNotebook();
			newNoteListener(newNote);
			deleteNotebookListener(deleteNotebook);
			
			this.add(BorderLayout.NORTH, newNotebook);
			this.add(BorderLayout.CENTER, newNote);
			this.add(BorderLayout.SOUTH, deleteNotebook);
		}
		
		private void deleteNotebookListener(JButton button) {		
			button.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(notebookModel.getRowCount() > 1) {
						int index = notebookTable.getSelectedRow();
						deletingIndex = index;
						NotesMemory.removeFromNotebook(index);
						notebookModel.removeRow(index);
					}
				}
			});
		}
		
		//Executing when click row of notebook or after change cell
		private void filterNotesWithNotebook() {
			notebookTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
				if(nameExistInNotebookTable())
					updateNoteTableRow(getSelectedNameOfNotebookTable());
			});
		}
		
		private boolean nameExistInNotebookTable() {
			for(String name: NotesMemory.getNotesbooks()) {
				if(getSelectedNameOfNotebookTable().equals(name))
					return true;
			}
			return false;
		}
		
		private void clickRowOfNotebookListener() {
			notebookTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					notebookNameBeforeChangeName = getSelectedNameOfNotebookTable();
				}
			});
		}
		
		private void changeNameListener() {
			notebookModel.addTableModelListener((TableModelEvent e) ->{
				String name = getSelectedNameOfNotebookTable();
				int index = getSelectedIndexOfNotebookTable();
				
				if(!name.equals("")) {
					NotesMemory.changeNotebook(index, name);
					changeAllNotebookInNotes(notebookNameBeforeChangeName, name);
					updateNoteTableRow(name);
				}
			});
		}
		
		private void changeAllNotebookInNotes(String oldName, String newName) {
			NotesMemory.changeNotebookOfNote(oldName, newName);
		}
		
		private void newNoteListener(JButton button) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					StickParameters stick = new StickParameters(getX(), getY(), "New Note", "Empty", getSelectedNameOfNotebookTable());
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
	
	private String getSelectedNameOfNotebookTable() {
		int actualRowIndex = getSelectedIndexOfNotebookTable();
		if(actualRowIndex < 0)
			actualRowIndex = setCellAfterRemoveNotebook();
		try {
			return notebookModel.getValueAt(actualRowIndex, 0).toString();
		}catch(ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}
	
	private int setCellAfterRemoveNotebook() {
		if(notebookModel.getRowCount() < 2)
			return 0;
		
		if(deletingIndex > 0)
			notebookTable.setRowSelectionInterval(deletingIndex-1, deletingIndex-1);
		else
			notebookTable.setRowSelectionInterval(deletingIndex, deletingIndex);
		
		return deletingIndex;
	}
	
	private int getSelectedIndexOfNotebookTable() {
		return notebookTable.getSelectedRow();
	}
	
	protected void updateNotebookTableRow() {	
		clearTable(notebookModel);
		for(String notebook: NotesMemory.getNotesbooks()) {
			notebookModel.addRow(new Object[] {notebook});
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
			TableOfNotes.this.updateNoteTableRow(TableOfNotes.this.getSelectedNameOfNotebookTable());
			JScrollPane scrollPane = new JScrollPane(notesTable);
			this.add(BorderLayout.CENTER, scrollPane);
		}
	}
}
