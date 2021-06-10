package uti;

import java.io.IOException;

import javax.swing.JFrame;

public class ZeroWasteApp {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(850, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Zero Waste");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			ZeroWasteWelcomePage welcomePage = new ZeroWasteWelcomePage(frame);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		frame.pack();
		frame.setVisible(true);
	}
}
