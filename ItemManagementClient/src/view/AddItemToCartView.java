package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import controller.ItemController;
import exceptions.InputException;
//import controller.ItemController;
//import exception.InputException;
import model.Item;
import model.OrderedItem;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class AddItemToCartView extends JDialog {

	// Data Attributes
	private Item selectedItem = new Item();
	private OrderedItem orderedItem = new OrderedItem();
	private int itemAmount = 0;
	private boolean isSelected = false;	// boolean to determine if an item is selected
	private boolean isEdit = false;		// boolean to determine the operation is add or edit

	// User Interface Attributes
	private JPanel contentPanel;
		// NORTH
		private JPanel panelNorth;
			private JLabel lblTitle;
		// SOUTH
		private JPanel panelSouth;
			private JButton btnCancel;
			private JButton btnConfirm;
		// CENTER
		private JPanel panelCenter;
			// Labels
			private JLabel lblName;
			private JLabel lblCode;
			private JLabel lblUnitPrice;
			private JLabel lblAmount;
			
			// Input Fields		
			private JTextField txtItemId;
			private JTextField txtName;
			private JTextField txtCode;
			private JTextField txtPrice;
			private	JSpinner spinnerAmount;
			
			private JButton btnSelectItem;

	/**
	 * Main method for debugging purposes
	 */
	public static void main(String[] args) {
		try {
			AddItemToCartView dialog = new AddItemToCartView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	// Constructor for Add Item To Cart
	public AddItemToCartView() {
		
		initializeComponents(null, 0);
	}
	
	// Constructor for Edit Item Amount
	public AddItemToCartView(OrderedItem orderedItem) {
		
		selectedItem = orderedItem.getItem();
		itemAmount = orderedItem.getQuantity();

		initializeComponents(selectedItem, itemAmount);
		isEdit = true;
		
		loadItemData();
	}

	// Method to create the UI Components
	private void initializeComponents(Item data, int amount) {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 442, 271);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
			// NORTH
			panelNorth = new JPanel();
			panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				lblTitle = new JLabel("");
				lblTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
				
				// Determine the text in the lblTitle
				if(data == null)
					lblTitle.setText("Add Item");
				else
					lblTitle.setText("Edit Amount");
			panelNorth.add(lblTitle);
		contentPanel.add(panelNorth, BorderLayout.NORTH);
		
			// SOUTH
			panelSouth = new JPanel();
			panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				btnCancel = new JButton("Cancel");
				btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnCancel.addActionListener(new CancelButtonListener());
				
				btnConfirm = new JButton("Confirm");
				btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnConfirm.addActionListener(new ConfirmButtonListener());
			panelSouth.add(btnCancel);
			panelSouth.add(btnConfirm);
		contentPanel.add(panelSouth, BorderLayout.SOUTH);
			
			// CENTER
			panelCenter = new JPanel();
			panelCenter.setLayout(null);
				// Labels
				lblName = new JLabel("Name: ");
				lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblName.setBounds(10, 39, 46, 13);
			
				lblCode = new JLabel("Code");
				lblCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblCode.setBounds(10, 68, 46, 13);

				lblUnitPrice = new JLabel("Unit Price");
				lblUnitPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblUnitPrice.setBounds(10, 103, 65, 13);

				lblAmount = new JLabel("Amount: ");
				lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblAmount.setBounds(10, 136, 56, 13);
			panelCenter.add(lblName);
			panelCenter.add(lblCode);
			panelCenter.add(lblUnitPrice);
			panelCenter.add(lblAmount);
				// Input Fields
				txtName = new JTextField();
				txtName.setEditable(false);
				txtName.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtName.setEnabled(true);
				txtName.setText("");
				txtName.setBounds(85, 37, 210, 19);
				txtName.setColumns(10);
				
				txtCode = new JTextField();
				txtCode.setEditable(false);
				txtCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtCode.setBounds(85, 66, 210, 19);
				txtCode.setColumns(10);

				txtPrice = new JTextField();
				txtPrice.setEditable(false);
				txtPrice.setEnabled(true);
				txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtPrice.setText("");
				txtPrice.setBounds(85, 100, 210, 19);
				txtPrice.setColumns(10);

				spinnerAmount = new JSpinner();
				spinnerAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
				spinnerAmount.setBounds(85, 134, 210, 20);
				spinnerAmount.setModel(new SpinnerNumberModel(1, 1, null, 1));
				JFormattedTextField txt = ((JSpinner.NumberEditor) spinnerAmount.getEditor()).getTextField();
				NumberFormatter formatter = (NumberFormatter) txt.getFormatter(); 
				DecimalFormat decimalFormat = new DecimalFormat("0"); 
				formatter.setFormat(decimalFormat); 
				formatter.setAllowsInvalid(false);

				btnSelectItem = new JButton("Select Item");
				btnSelectItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnSelectItem.setBounds(308, 10, 102, 144);
				btnSelectItem.addActionListener(new SelectItemButtonListener());
				
				// Disable the Select item button if Edit Item Amount
				if(data != null)
				{	
					selectedItem = data;
					itemAmount = amount;
					loadItemData();
					btnSelectItem.setEnabled(false); 
				}

			panelCenter.add(txtName);
			panelCenter.add(txtCode);
			panelCenter.add(txtPrice);
			panelCenter.add(spinnerAmount);
			panelCenter.add(btnSelectItem);
		contentPanel.add(panelCenter, BorderLayout.CENTER);		
	}
	
	public OrderedItem getOrderedItem() {
		return orderedItem;
	}
	
	public boolean isSelected() {
		
		return isSelected;
	}
	
	public boolean isEdit() {
		
		return isEdit;
	}
		
	public Item getSelectedItem() {
		
		return selectedItem;
	}
	
	public int getItemAmount() {
		
		return itemAmount;
	}
	
	// Method to Load Item and Amount into the Input Fields
	private void loadItemData() {
		
		txtName.setText(selectedItem.getName());
		txtCode.setText(selectedItem.getCode());
		txtPrice.setText(String.format("%.2f", selectedItem.getPrice()));
		if(itemAmount > 0)
			spinnerAmount.setValue(itemAmount);
	}
	
	private class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			AddItemToCartView.this.dispose();
		}
	}

	private class ConfirmButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			try {
				int orderedQuantity = (int)spinnerAmount.getValue();
				
				ItemController itemController = new ItemController();
				itemController.validateItem(selectedItem, orderedQuantity);
				isSelected = true;
				
				// Set the orderedItem using the user input
				orderedItem.setItem(selectedItem);
				orderedItem.setQuantity(orderedQuantity);
				
				AddItemToCartView.this.dispose();
				
			} catch(InputException exception) {
				exception.displayMessage();
				isSelected = false;
			}
		}
	}
	
	private class SelectItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			SelectItemView selectItemView = new SelectItemView();
			selectItemView.setVisible(true);
			
			// Load the selected item data if there is item selected
			if(selectItemView.isSelected()) {
				
				selectedItem = selectItemView.getSelectedItem();
				loadItemData();
			}
		}
	}
}

