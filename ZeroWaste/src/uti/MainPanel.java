package uti;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MainPanel extends JPanel implements ActionListener {

	// here we have the tasks as a check box list
	// on the right side will be the completed tasks
	JFrame frame;

	JPanel mainPanel;
	JPanel tasksPanel;
	JPanel buttonPanel;
	JPanel toDoPanel; // left panel keeps the to-do tasks
	JPanel donePanel; // right panel keeps the completed tasks
	
	ArrayList<String> dones;

	JButton newTasks;

	User user;

	int click = 0;

	public MainPanel(JFrame frame, User user) {
		this.user = user;

		this.frame = frame;
		this.frame.setSize(850, 450);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);

		toDoPanel = new JPanel();
		toDoPanel.setBorder(BorderFactory.createEmptyBorder());
		toDoPanel.setAlignmentX(LEFT_ALIGNMENT);
		toDoPanel.setLayout(new GridLayout(7, 1));

		donePanel = new JPanel();
		donePanel.setBorder(BorderFactory.createEmptyBorder());
		donePanel.setAlignmentX(LEFT_ALIGNMENT);
		donePanel.setLayout(new GridLayout(7, 1));
		
		buttonPanel = new JPanel();
		newTasks = new JButton("Give me more!");
		buttonPanel.add(newTasks);
		newTasks.addActionListener(this);
		
		tasksPanel = new JPanel();
		tasksPanel.add(toDoPanel);
		tasksPanel.add(donePanel);
		tasksPanel.setLayout(new GridLayout(1, 2));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(tasksPanel);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.frame.add(mainPanel);
		
		dones = new ArrayList<>();
		getNewTasks();

		this.setVisible(true);

	}

	Scanner getTasks;
	ArrayList<String> tasks = new ArrayList<>();
	
	public void fillTaskLists() throws FileNotFoundException {
		getTasks = new Scanner(MainPanel.class.getResourceAsStream("tasks/" + user.userName + ".txt"));

		while (getTasks.hasNext()) {
			// putting the tasks taken from the file in a ArrayList
			tasks.add(getTasks.nextLine());
		}
		
		// shuffling the tasks on order to print them every time differently
		Collections.shuffle(tasks);
		getTasks.close();
	}

	ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
	int checkBoxesTicked = 0;

	public void createCheckBoxes() {
		for (int i = 0; i < 7; i++) {
			String text = tasks.get(i);
			text = String.format("<html>%s</html>", text);
			JCheckBox option = new JCheckBox(text);
			
			toDoPanel.add(option);
			checkBoxes.add(option);
			option.setOpaque(true);
			dones.add(tasks.get(i));

			option.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JCheckBox option = (JCheckBox)e.getSource();
					// once a given check-box is selected (the task is done) it will be erased from the tasks panel 
					// and added to de done panel
					if (option.isSelected()) {
						checkBoxesTicked++;
						toDoPanel.remove(option);
						donePanel.add(option);
						toDoPanel.updateUI();
						option.setEnabled(false);
					}

				}
			});

		}
		tasks.removeAll(dones);
	}

	public void getNewTasks() {
		// the donePanel must be cleaned before printing new tasks
		while (!(checkBoxes.isEmpty())) {
			donePanel.remove(checkBoxes.get(0));
			checkBoxes.remove(0);
		}

		try {
			fillTaskLists();
			createCheckBoxes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		click++; // counting how many times the button is clicked
		
		try {
			if ((checkBoxesTicked != 0 && checkBoxesTicked % 7 == 0) || (user.buttonCount * 7 == checkBoxesTicked)) {
				if (click >= user.buttonCount) {
					// when the user has pressed the button the wanted times (he has done all tasks) the program closes
					UIManager.put("OptionPane.okButtonText", "Great!");
					JOptionPane.showMessageDialog(this, "You are the boss of ZeroWaste! You have no more tasks to do!", 
							"Congratulations!", JOptionPane.CLOSED_OPTION);
					System.exit(0);
				} else {
					try {
						getNewTasks();
						donePanel.updateUI();
						toDoPanel.updateUI();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			} else {
				throw new UncheckedBoxesException();
			}
		} 
		catch (UncheckedBoxesException e) {
			click --;
		}

	}

}
