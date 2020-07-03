/**
 *	InputException class contains all the error message of the exceptions
 */

package exceptions;

/**
 * @author User
 *
 */

public class InputException extends Exception{
	
	private String error;
	
	//	Constructor
	public InputException(String error)
	{
		this.error = error;
	}
	
	//	Display the error message based on the given exception
	public void displayMessage()
	{
		if(error == "Duplicate Name")
			MessageBoxView.infoBox("Input Error: Duplicate Name detected.\nPlease enter a new name.", "Input Error: Duplicate Name");
		if(error == "Empty Field")
			MessageBoxView.infoBox("Input Error: Empty Field detected.\nPlease fill in all input fields.", "Input Error: Empty Field");
		if(error == "Over Limit")
		{	
			MessageBoxView.infoBox("Input Error: Over Character Limit.\nOne or more text field(s) exceed its character limit.\n"
					+ "Please try to reduce the character length.", "Input Error: Over Character Limit");
		}
		if(error == "Duplicate Code")
			MessageBoxView.infoBox("Input Error: Duplicate Code detected.\nPlease enter a new code.", "Input Error: Duplicate Code");
		if(error == "Invalid Order Data")
			MessageBoxView.infoBox("Input Error: Invalid Order Data.\nPlease ensure the item list is not empty.", 
					"Input Error: Invalid Order Data");
		if(error == "Invalid Item Amount")
			MessageBoxView.infoBox("Input Error: Invalid Item Amount.\nThe stock quantity for that item in the storage is insufficient to support your purchase.", 
					"Input Error: Invalid Item Amount");
		if(error == "No Item")
			MessageBoxView.infoBox("Input Error: No Item selected. \nPlease select an item.", "Input Error: No Item selected.");
		if(error == "Duplicate Item")
			MessageBoxView.infoBox("Input Error: Duplicate Item detected. \nThis item is already in cart.", "Input Error: Duplicate Item detected.");


	}
}
