/**
 *	Class used to display the Error Message
 */

package exceptions;

import javax.swing.JOptionPane;

public class MessageBoxView {
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}
