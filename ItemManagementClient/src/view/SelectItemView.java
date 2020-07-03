package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
/*
import controller.CustomerController;
import controller.ItemController;
import controller.ItemTypeController;
import model.Item;
import model.ItemType;*/

import controller.ItemController;
import model.Item;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SelectItemView extends JDialog {

	// Data Attributes
	private Item selectedItem = new Item();
	private ArrayList<Item> serverItems = new ArrayList<Item>(); // The list that holds the server items data
	private ArrayList<Item> items = new ArrayList<Item>();	 // The list that will be used to display in item table
	private boolean isSelected = false;
	
	// User Interface Attributes
	private JPanel contentPanel;
		// NORTH
		private JPanel panelNorth;
			private JLabel lblSelectItem;
		// SOUTH
		private JPanel panelSouth;
			private JButton btnCancel;
			private JButton btnSelect;
		// CENTER
		private JPanel panelCenter;
		private JScrollPane scrollPane;
		private JTable itemTable;
			// CENTER NORTH
			private JPanel panelCenterNorth;
				private JLabel lblSearchBy;
				private JComboBox<String> comboBoxSearchCond;
				private JTextField txtSearch;
				private JButton btnSearch;
				private JButton btnReset;

	/**
	 * Launch the application.
	 */
	// Main Method for debugging purposes
	public static void main(String[] args) {
		
				
		try {
			SelectItemView dialog = new SelectItemView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	// Constructor
	public SelectItemView() {
		
		initializeComponents();
		serverItems = ClientApplication.receiveItemList();
		reloadItemTable(serverItems);
	}
	
	// Methods
	// Method to build all UI Components
	private void initializeComponents() {
		
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 745, 395);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
			// NORTH	
			panelNorth = new JPanel();
			panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				lblSelectItem = new JLabel("Select Item");
				lblSelectItem.setFont(new Font("Tahoma", Font.BOLD, 15));
			panelNorth.add(lblSelectItem);
		contentPanel.add(panelNorth, BorderLayout.NORTH);
		
			// SOUTH
			panelSouth = new JPanel();
			panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				btnCancel = new JButton("Cancel");
				btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnCancel.addActionListener(new CancelButtonListener());
				
				btnSelect = new JButton("Select");
				btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnSelect.addActionListener(new SelectButtonListener());
			panelSouth.add(btnCancel);
			panelSouth.add(btnSelect);
		contentPanel.add(panelSouth, BorderLayout.SOUTH);
			
			// CENTER
			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout(0, 0));
				scrollPane = new JScrollPane();
					itemTable = new JTable();
					itemTable.setModel(new ItemTableModel());
					itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(itemTable);
			panelCenter.add(scrollPane, BorderLayout.CENTER);
			
				// CENTER NORTH
				panelCenterNorth = new JPanel();
					lblSearchBy = new JLabel("Search by:");
					lblSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 12));
					
					comboBoxSearchCond = new JComboBox<String>();
					comboBoxSearchCond.setFont(new Font("Tahoma", Font.PLAIN, 12));
					comboBoxSearchCond.addItem("Name");
					comboBoxSearchCond.addItem("Code");
					
					txtSearch = new JTextField();
					txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtSearch.setColumns(10);

					btnSearch = new JButton("Search");
					btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
					btnSearch.addActionListener(new SearchButtonListener());
					
					btnReset = new JButton("Reset");
					btnReset.setFont(new Font("Tahoma", Font.PLAIN, 12));
					btnReset.addActionListener(new ResetButtonListener());
					
					GroupLayout gl_panelCenterNorth = new GroupLayout(panelCenterNorth);
					gl_panelCenterNorth.setHorizontalGroup(
						gl_panelCenterNorth.createParallelGroup(Alignment.LEADING)
							.addGap(0, 721, Short.MAX_VALUE)
							.addGroup(gl_panelCenterNorth.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblSearchBy)
								.addGap(10)
								.addComponent(comboBoxSearchCond, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSearch)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnReset)
								.addGap(12))
					);
					gl_panelCenterNorth.setVerticalGroup(
						gl_panelCenterNorth.createParallelGroup(Alignment.LEADING)
							.addGap(0, 28, Short.MAX_VALUE)
							.addGroup(gl_panelCenterNorth.createSequentialGroup()
								.addGap(5)
								.addGroup(gl_panelCenterNorth.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblSearchBy)
									.addComponent(comboBoxSearchCond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnSearch)
									.addComponent(btnReset)))
					);
				panelCenterNorth.setLayout(gl_panelCenterNorth);
			panelCenter.add(panelCenterNorth, BorderLayout.NORTH);
		contentPanel.add(panelCenter, BorderLayout.CENTER);
	}
	
	
	// Method to get the selected item
	public Item getSelectedItem() {
		
		return selectedItem;
	}
	
	public boolean isSelected() {
		
		return isSelected;
	}
	
	
	private void reloadItemTable(ArrayList<Item> itemList)
	{
		items = itemList;
		itemTable.repaint();
		itemTable.revalidate();

	}
	
	private class SelectButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Get the index of the item selected in the table
	    	int index = itemTable.getSelectedRow();
	    	
	    	// If No item selected, cancel the operation
	    	if(index < 0)
	    		return;
	    	
	    	// If there is item selected, assign the item and set boolean isSelected to true
	    	selectedItem = items.get(index);
	    	isSelected = true;
	    		    	
	    	SelectItemView.this.dispose();
		}
	}
	
	private class CancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			SelectItemView.this.dispose();
		}
	}
	
	private class SearchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String searchColumn = comboBoxSearchCond.getSelectedItem().toString();
			ItemController itemController = new ItemController();
			ArrayList<Item> resultItemList = itemController.searchItem(serverItems, searchColumn, txtSearch.getText());
			reloadItemTable(resultItemList);
		}
	}
	
	
	private class ResetButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			reloadItemTable(serverItems);
		}
	}
	
	
	// Table Model
	private class ItemTableModel implements TableModel
	{
		String header[] = new String[] { "Name", "Code", "Unit Price", "Amount Remaining", "Description"};
		
		public int getColumnCount() 
		{
		   return header.length;
		}
		
		public int getRowCount() 
		{
		   return items.size();
		}   
		    
		public String getColumnName(int column) 
		{
		  return header[column];
		}
		        
		public Class getColumnClass(int column) 
		{
		    return String.class;
		}
		  
		public Object getValueAt(int rowIndex, int columnIndex) 
		{
			Item item = items.get(rowIndex);
			
		 	switch(columnIndex)
			{
			 	case 0: return item.getName();
			   	case 1: return item.getCode();
			   	case 2: return String.format("%.2f", item.getPrice());
			   	case 3: return item.getStockAmount();
			   	case 4: return item.getDescription();
			}
		 	return "";
		}
		    
		public boolean isCellEditable(int rowIndex, int columnIndex) 
		{
			return false;
		}
		    
		 public void setValueAt(Object value, int row, int column) {}

		 public void addTableModelListener(TableModelListener l) {}
		    
		 public void removeTableModelListener(TableModelListener l){}

	}
}
