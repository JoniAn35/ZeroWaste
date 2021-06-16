package uti;

import java.io.IOException;

import javax.swing.JFrame;

public class ZeroWasteApp {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(850, 450);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Zero Waste");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			// creating the first panel and giving it this frame as parameter
			ZeroWasteWelcomePage welcomePage = new ZeroWasteWelcomePage(frame);
			// once the action in this panel is done and the button is pressed it'll be disabled and the next will be shown
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		frame.pack();
		frame.setVisible(true);
	}
}
