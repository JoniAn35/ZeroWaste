package uti;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class TestPanel extends JPanel implements ActionListener {
	JFrame frame;

	JPanel testPanel;
	JScrollPane scr;
	JButton resultButton;
	File testFile;
	Scanner scanner;
	JLabel[] testLabel;

	User user;

	Map<String, Integer> answers; // String - key(answer); integer - value(points)

	public TestPanel(JFrame frame) throws IOException {
		this.frame = frame;
		this.frame.setSize(850, 450);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);

		testPanel = new JPanel(); // shows the test
		testPanel.setBorder(BorderFactory.createEmptyBorder());
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
		this.add(testPanel);

		scr = new JScrollPane(testPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setPreferredSize(new Dimension(850, 420));
		add(scr);

		testLabel = new JLabel[10];
		resultButton = new JButton("Results"); // shows the results after the test is completed
		resultButton.addActionListener(this);
		answers = new HashMap<>();

		scanner = new Scanner(TestPanel.class.getResourceAsStream("test/test.txt"));
		getQuestions(); // finds the questions in the file
		getAnswers(answers); // finds the answers
		groupAnswers(answers); // puts the answers in groups of three
		printTest(questions, answers); // puts questions in labels

		resultButton = new JButton("Results"); // shows the results after the test is completed
		resultButton.addActionListener(this);

		testPanel.add(resultButton);

		this.setVisible(true);
		this.frame.add(this);
	}
	
	/**
	 * the pattern of the file with the questions:
	 * 		Question: *textOfQuestion*
	 * 		*number* *textOfOption*
	 * 		*number* *textOfOption*
	 *		*number* *textOfOption*
	 *
	 *		Question: *textOfQuestion*
	 * 		*number* *textOfOption*
	 * 		*number* *textOfOption*
	 *		*number* *textOfOption*
	 *
	 *		...
	 */

	ArrayList<String> questions = new ArrayList<>();
	
	public void getQuestions() {
		String line = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			// if the line starts whit "Question:" is must be added to the ArrayList this the texts of the questions
			if (line.startsWith("Question:")) {
				questions.add(line);
			}
		}
	}

	public Map<String, Integer> getAnswers(Map<String, Integer> answerMap) {
		Scanner sc = new Scanner(TestPanel.class.getResourceAsStream("test/test.txt"));
		
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if (nextLine.isEmpty()) { // the empty line between the questions must be missed
				continue; 
			}
			
			// the number at the beginning of the line is the points the user will get if he press this answer
			if (nextLine.charAt(0) == '1') {
				answerMap.put(nextLine.substring(2), 1);
			} else if (nextLine.charAt(0) == '2') {
				answerMap.put(nextLine.substring(2), 2);
			} else if (nextLine.charAt(0) == '3') {
				answerMap.put(nextLine.substring(2), 3);
			}
		}
		
		sc.close();
		return answerMap;
	}

	// for each question there will be a group of three buttons
	ButtonGroup[] buttonGroups = new ButtonGroup[10];

	public ButtonGroup[] groupAnswers(Map<String, Integer> answerMap) {
		// converting the Map to an array with the keys of the map
		ArrayList<String> answersList = new ArrayList<>(answerMap.keySet());
		// creating an array with 30 radio buttons
		JRadioButton[] rButton = new JRadioButton[30];
		
		// setting the text to each button
		for (int j = 0; j < 30; j++) {
			rButton[j] = new JRadioButton();
			rButton[j].setText(answersList.get(j));
		}

		// adding every three buttons to a group
		// for example: i = 3; the buttons will be 7,8,9
		for (int i = 1; i <= 10; i++) {
			int index = (3 * i) - 1;
			buttonGroups[i - 1] = new ButtonGroup();
			buttonGroups[i - 1].add(rButton[index]);
			buttonGroups[i - 1].add(rButton[index - 1]);
			buttonGroups[i - 1].add(rButton[index - 2]);
		}
		
		return buttonGroups;
	}

	JRadioButton[] radioButtons = new JRadioButton[30];

	public void printTest(ArrayList<String> questions, Map<String, Integer> answerMap) {
		// adding the questions and the options to the panel
		
		ArrayList<String> answersList = new ArrayList<>(answerMap.keySet());
		for (int j = 0; j < 30; j++) {
			radioButtons[j] = new JRadioButton();
			String answerText = answersList.get(j);
			answerText = String.format("<html>%s</html>", answerText);
			radioButtons[j].setText(answerText);
		}
		
		for (int i = 1; i <= 10; i++) {
			testLabel[i - 1] = new JLabel();
			String questionText = questions.get(i - 1);
			questionText = String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 600, questionText);
			testLabel[i - 1].setText(questionText);
			testPanel.add(testLabel[i - 1]);
			int index = (3 * i) - 1;
			testPanel.add(radioButtons[index]);
			testPanel.add(radioButtons[index - 1]);
			testPanel.add(radioButtons[index - 2]);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		calculateResult(radioButtons, answers);

		// creating a message that will tell the user how much recycle he/she is
		UIManager.put("OptionPane.DEFAULT_OPTION", "Let's go");
		String message = user.print();
		message = String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 300, message);
		JOptionPane.showConfirmDialog(null, message, "Your result", JOptionPane.DEFAULT_OPTION);

		this.setVisible(false);
		// when the button is pressed this panel will be disabled and the main panel will be shown
		MainPanel main = new MainPanel(this.frame, this.user);
	}

	public void calculateResult(JRadioButton[] radioButtons, Map<String, Integer> answerMap) {
		int result = 0;
		
		// based on the option that has been pressed we calculate the result of the test
		for (int j = 0; j < 30; j++) {
			if (radioButtons[j].isSelected()) {
				String t = radioButtons[j].getText().replace("<html>", "").replace("</html>", "");
				if (answerMap.containsKey(t)) {
					result += answerMap.get(t);
				}
			}
		}

		// based on the result the type of user is created
		// this will be important for the main panel therefore it is passed to its constructor
		if (0 <= result && result < 17) {
			user = new Newbie(result);
		} else if (16 < result && result < 25) {
			user = new Experienced(result);
		} else {
			user = new Master(result);
		}
	}

}
