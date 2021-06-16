package uti;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class UncheckedBoxesException extends Exception{
	
	public UncheckedBoxesException() {
		UIManager.put("OptionPane.okButtonText", "Okay");
		JOptionPane.showMessageDialog(null, "You haven't done all your tasks in order to move to other. Please fulfill all of them!", 
				"ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
}
