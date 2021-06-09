package uti;

import javax.swing.JFrame;


public class ZeroWasteApp {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		
		frame.setSize(850, 400);
		frame.add(new ZeroWasteWelcomePage());
		
		frame.setTitle("Zero Waste");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
