package uti;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author yoanaangelova TODO: Цвети: checkBox-ове, взимане на инфо от файл и
 *         прехвърляне в arrayList; разбъркване; извеждане само по 7 (отделен
 *         метод с цикъл) Йони: да довърша бутона Give me more Роси: грешка
 */

public class MainPanel extends JPanel implements ActionListener {

	// here we have the tasks as a check box list
	// on the right side will be the completed tasks
	JFrame frame;

	JPanel mainPanel = new JPanel();
	JPanel tasksPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	// left panel keeps the to-do tasks
	JPanel toDoPanel = new JPanel();
	// right panel keeps the completed tasks
	JPanel donePanel = new JPanel();
	JTextArea doneTasks = new JTextArea();

	JButton newTasks = new JButton("Give me more!");

	User user;

	int click = 0;

	public MainPanel(JFrame frame, User user) {
		this.user = user;

		this.frame = frame;
		this.frame.setSize(850, 400);
		this.frame.setLocationRelativeTo(null);
//		this.frame.setResizable(false);

		this.frame.add(mainPanel);

		mainPanel.add(tasksPanel);
		mainPanel.add(buttonPanel);
		mainPanel.setLayout(new GridLayout(2, 1));

		tasksPanel.add(toDoPanel);
		tasksPanel.add(donePanel);
		tasksPanel.setLayout(new GridLayout(1, 2));

		buttonPanel.add(newTasks);
		newTasks.addActionListener(this);

		toDoPanel.setBorder(BorderFactory.createEmptyBorder());
		toDoPanel.setAlignmentX(LEFT_ALIGNMENT);
		toDoPanel.setLayout(new GridLayout(7, 1));

		donePanel.setBorder(BorderFactory.createEmptyBorder());
		donePanel.setAlignmentX(LEFT_ALIGNMENT);
		donePanel.setLayout(new GridLayout(7, 1));

		getNewTasks();

		this.setVisible(true);

	}

	ArrayList<String> tasks = new ArrayList<>();
	Scanner getTasks;

	ArrayList<String> list = new ArrayList<String>();

	public void fillTaskLists() throws FileNotFoundException {
		getTasks = new Scanner(MainPanel.class.getResourceAsStream("tasks/" + user.userName + ".txt"));

		while (getTasks.hasNext()) {
			tasks.add(getTasks.nextLine());
		}
		Collections.shuffle(tasks);
		getTasks.close();
	}

	ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
	ArrayList<String> dones = new ArrayList<String>();

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
					if (option.isSelected()) {
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
		// the donePanel must be cleaned before giving new tasks
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
		click++;
		if (click >= user.buttonCount) {
			JOptionPane.showMessageDialog(this, "You are the boss of ZeroWaste! You have no more tasks to do!");
		} else {
			try {
				getNewTasks();
				donePanel.updateUI();
				toDoPanel.updateUI();
			}
			catch(Exception e){
				System.out.println(e);
			}
		}

	}

}
