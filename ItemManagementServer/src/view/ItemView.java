package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import controller.ItemController;
import exceptions.InputException;
import model.Item;

public class ItemView extends JDialog {

	//	Data Attributes
	private Item data = new Item();
	private boolean updated = false;

	//	User Interface Attributes
	private JPanel contentPanel;
		//	NORTH
		private JPanel panelNorth;
			private JLabel lblTitle;
		//	CENTER
		private JPanel panelCenter;
			private JLabel lblName;
			private JLabel lblCode;
			private JLabel lblPrice;
			private JLabel lblStockAmount;
			private JLabel lblDescription;
			private JTextField txtName;
			private JTextField txtCode;
			private JSpinner spinnerPrice;
			private JSpinner spinnerStockAmount;
			private JTextArea taDescription;
		//	SOUTH
		private JPanel panelSouth;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ItemView dialog = new ItemView(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ItemView(Item data) {
		initializeComponents(data);
	}
	
	//	Method to initialize all UI Components
	public void initializeComponents(Item data) {
		
		//	Frame
		contentPanel = new JPanel();
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 330, 370);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
			//	NORTH
			panelNorth = new JPanel();
			panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				lblTitle = new JLabel("Item");
				if (data == null)
					lblTitle.setText("Add Item");
				else
				{
					lblTitle.setText("Edit item");
					this.data = data;
				}
				lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
			panelNorth.add(lblTitle);
		contentPanel.add(panelNorth, BorderLayout.NORTH);
			//	CENTER
			panelCenter = new JPanel();
			panelCenter.setLayout(null);
				// Add Labels
				lblName = new JLabel("Name: ");
				lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblName.setBounds(10, 10, 46, 13);
				
				lblCode = new JLabel("Code:");
				lblCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblCode.setBounds(10, 41, 46, 13);

				lblPrice = new JLabel("Price: ");
				lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblPrice.setBounds(10, 71, 46, 13);
				
				lblStockAmount = new JLabel("Stock Amount:");
				lblStockAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblStockAmount.setBounds(10, 102, 90, 13);

				lblDescription = new JLabel("Description: ");
				lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblDescription.setBounds(10, 132, 71, 13);
			panelCenter.add(lblName);
			panelCenter.add(lblCode);		
			panelCenter.add(lblPrice);
			panelCenter.add(lblStockAmount);
			panelCenter.add(lblDescription);
				//	Add Data Enter Fields
				txtName = new JTextField();
				txtName.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtName.setBounds(91, 7, 200, 19);
				txtName.setColumns(10);

				txtCode = new JTextField();
				txtCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtCode.setBounds(91, 38, 200, 19);
				txtCode.setColumns(10);

				spinnerPrice = new JSpinner();
				spinnerPrice.setModel(new SpinnerNumberModel(0.00, 0.00, null, 1.00));
				JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerPrice,"0.00");
				spinnerPrice.setEditor(editor);
				JFormattedTextField txt = ((JSpinner.NumberEditor) spinnerPrice.getEditor()).getTextField();
				NumberFormatter formatterPrice = (NumberFormatter) txt.getFormatter(); 
				DecimalFormat decimalFormatPrice = new DecimalFormat("0.00"); 
				decimalFormatPrice.setMinimumFractionDigits(2);
				formatterPrice.setFormat(decimalFormatPrice); 
				formatterPrice.setAllowsInvalid(false);
				spinnerPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
				spinnerPrice.setBounds(91, 69, 200, 20);

				spinnerStockAmount = new JSpinner();
				spinnerStockAmount.setModel(new SpinnerNumberModel(0, 0, null, 1));
				txt = ((JSpinner.NumberEditor) spinnerStockAmount.getEditor()).getTextField();
				NumberFormatter formatterStockAmount = (NumberFormatter) txt.getFormatter(); 
				DecimalFormat decimalFormatStockAmount = new DecimalFormat("0"); 
				formatterStockAmount.setFormat(decimalFormatStockAmount); 
				formatterStockAmount.setAllowsInvalid(false);
				spinnerStockAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
				spinnerStockAmount.setBounds(91, 100, 200, 20);

				taDescription = new JTextArea();
				taDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
				taDescription.setBounds(91, 130, 200, 120);
				taDescription.setLineWrap(true);
			panelCenter.add(txtName);
			panelCenter.add(txtCode);
			panelCenter.add(spinnerPrice);
			panelCenter.add(spinnerStockAmount);
			panelCenter.add(taDescription);
		contentPanel.add(panelCenter, BorderLayout.CENTER);
			//	SOUTH
			panelSouth = new JPanel();
			panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				JButton btnCancelButton = new JButton("Cancel");
				btnCancelButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnCancelButton.addActionListener(new CancelButtonListener());
				
				JButton btnSaveButton = new JButton("Save");
				btnSaveButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnSaveButton.addActionListener(new SaveButtonListener());
			panelSouth.add(btnCancelButton);
			panelSouth.add(btnSaveButton);
		contentPanel.add(panelSouth, BorderLayout.SOUTH);

		//	Load the data into the Data Input Fields if NOT NULL
		if(data != null)
			loadData();
	}
	
	public boolean confirmUpdate()
	{
		return updated;
	}
	
	//	Method to Load Data in Input Fields
	public void loadData()
	{
		txtName.setText(data.getName());
		txtCode.setText(data.getCode());
		spinnerPrice.setValue(data.getPrice());
		spinnerStockAmount.setValue(data.getStockAmount());
		taDescription.setText(data.getDescription());
	}
	
	//	Save Button Event
	private class SaveButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{				
			//	Set the data variables
			data.setName(txtName.getText());
			data.setCode(txtCode.getText());
			data.setPrice((Double)spinnerPrice.getValue());
			data.setStockAmount((Integer)spinnerStockAmount.getValue());
			data.setDescription(taDescription.getText());
			
			//	Insert or Update the data
			ItemController controller = new ItemController();
			int result;
			try {
				if(data.getId() == 0)
					result = controller.insert(data);
				else
					result = controller.update(data);
				if (result == 1)
				{
					//	Close the form after update completed
					updated = true;
					ItemView.this.dispose();
				}
			} catch (InputException exception) {
				exception.displayMessage();
			}
			catch (ClassNotFoundException | SQLException exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
			} 
		}
	}
	
	//	Cancel Button Event
	private class CancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//	Exit the form
			ItemView.this.dispose();
		}
	}
}
