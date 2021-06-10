package uti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements ActionListener {

// here we have the tasks as a check box list
// on the right side will be the completed tasks
	JFrame frame;
	
	JPanel mainPanel =  new JPanel();
	JPanel toDoPanel = new JPanel();// left panel keeps the to-do tasks
	JPanel donePanel = new JPanel();// left panel keeps the completed tasks
	
	JButton newTasks = new JButton("Give me more!");
	
	User user;
	/** 
	 * TODO: calculating the result from the test 
	*/
	int result = 23;
	int click = 0;

	public MainPanel(JFrame frame) {
		this.frame = frame;
		this.frame.setSize(850, 400);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);

		mainPanel.add(toDoPanel);
		mainPanel.add(donePanel);
		toDoPanel.setLayout(new BoxLayout(toDoPanel, BoxLayout.X_AXIS));
	
		toDoPanel.setBorder(BorderFactory.createEmptyBorder());
		toDoPanel.setLayout(new BoxLayout(toDoPanel, BoxLayout.Y_AXIS));
		donePanel.setBorder(BorderFactory.createEmptyBorder());
		donePanel.setLayout(new BoxLayout(donePanel, BoxLayout.Y_AXIS));
		
		getNewTasks();
		mainPanel.add(newTasks);
		
		this.setVisible(true);

	}
	
	ArrayList<JCheckBox> listWithTasks = new ArrayList<>();
	//ArrayList<String> tasks = new ArrayList<>();
	
	public void getNewTasks() {
		// the donePanel must be cleaned before giving new tasks
		while(!(listWithTasks.isEmpty())) {
			donePanel.remove(listWithTasks.get(0));
			listWithTasks.remove(0);
		}
		
		if (result > 0 && result < 11) {
			user = new Newbie(result);
			generateNewTask(user);
		}
		else if (result > 10 && result < 21) {
			user = new Experienced(result);
			generateNewTask(user);
		}
		else {
			user = new Master(result);
			generateNewTask(user);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		click++;
		if (click == user.buttonCount) {
			/**
			 * TODO: JOptionPane: "You are the boss of ZeroWaste!"
			 */
		}
		else {
			/**
			 *  TODO: when clicked the current data must me erased and the next 7 tasks must me shown
			 */
//			try (/* if all the checkBoxes are clicked: */) {
//				getNetTasks();
//			}
//			catch {
//				// if not all the checkBoxes are clicked:
//				// => exception (custom)
//			}
		}
			 
	}
	
	public void generateNewTask(User user) {
		Scanner sc = new Scanner(ZeroWasteApp.class.getResourceAsStream("tasks/" + user.userName + ".txt")); 
		
		while (sc.hasNext()) {
			JCheckBox option = new JCheckBox(sc.nextLine());
			option.setOpaque(true);
			listWithTasks.add(option);
			toDoPanel.add(option);
		}
	}

}
