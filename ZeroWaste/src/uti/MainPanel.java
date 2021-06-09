package uti;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JFrame implements ActionListener {

// here we have the tasks as a check box list
// on the right side will be the completed tasks

	public MainPanel() {
		JPanel mainPanel = new JPanel();
		this.setSize(850, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JPanel toDoPanel = new JPanel();// left panel keeps the to-do tasks
		JPanel donePanel = new JPanel();// left panel keeps the completed tasks
		mainPanel.add(toDoPanel);
		mainPanel.add(donePanel);
		toDoPanel.setLayout(new BoxLayout(toDoPanel, BoxLayout.X_AXIS));
	
		toDoPanel.setBorder(BorderFactory.createEmptyBorder());
		toDoPanel.setLayout(new BoxLayout(toDoPanel, BoxLayout.Y_AXIS));
		donePanel.setBorder(BorderFactory.createEmptyBorder());
		donePanel.setLayout(new BoxLayout(donePanel, BoxLayout.Y_AXIS));
		
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
