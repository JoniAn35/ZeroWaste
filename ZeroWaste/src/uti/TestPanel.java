package uti;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class TestPanel extends JFrame implements ActionListener {
	JPanel testPanel;
	JScrollBar scrollBar;
	JButton resultButton;
	
	public TestPanel() {

//		JFrame frame = new JFrame();

		testPanel = new JPanel();// shows the test
		testPanel.setBorder(BorderFactory.createEmptyBorder());
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
		
		this.setSize(850, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.add(testPanel);

		scrollBar = new JScrollBar();
		testPanel.add(scrollBar);
		resultButton = new JButton("Results"); // shows the results after the test is completed
		resultButton.addActionListener(this);
		
		testPanel.add(resultButton);
		
		this.setVisible(true);
		

//		frame.add(testPanel, BorderLayout.CENTER); // add panel to the frame
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setTitle("Main Panel");
//		frame.pack(); // set the window to match a certain size
//		frame.setVisible(true); // set the window to be visible

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object[] options = { "Yes, please", "No way!" };
		int n = JOptionPane.showOptionDialog(testPanel, "Would you like green eggs and ham?", "Your results",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
				options, // the titles of buttons
				options[0]); // default button title
		if (n == JOptionPane.YES_OPTION) {
			this.setVisible(false);
			MainPanel main = new MainPanel();
		}
	}


}
