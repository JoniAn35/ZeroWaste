package uti;

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
import javax.swing.JScrollBar;

public class TestPanel extends JPanel implements ActionListener {
	JFrame frame;

	JPanel testPanel;
	JScrollBar scrollBar;
	JButton resultButton;
	File testFile;
	Scanner scanner;
	JLabel[] testLabel;

	Map<String, Integer> answers; // String - key(answer); integer - value(points)

	public TestPanel(JFrame frame) throws IOException {
		this.frame = frame;
		this.frame.setSize(850, 400);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);

		testPanel = new JPanel();// shows the test
		testPanel.setBorder(BorderFactory.createEmptyBorder());
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
		this.add(testPanel);

		testLabel = new JLabel[10];
		scrollBar = new JScrollBar();
		testPanel.add(scrollBar);
		resultButton = new JButton("Results"); // shows the results after the test is completed
		resultButton.addActionListener(this);
		answers = new HashMap<String, Integer>();

//		testFile = new File("/Users/yoanaangelova/Desktop/java/ZeroWaste/ZeroWaste/src/uti/test/test.txt");
		scanner = new Scanner(TestPanel.class.getResourceAsStream("test/test.txt"));
		Pattern p = Pattern.compile("Question:");

		// getQuestions(p, scanner.nextLine());// finds the questions in the file
		getQuestions(p);// finds the questions in the file
		getAnswers(answers);// finds the answers
		groupAnswers(answers);// puts the answers in groups of three
		printTest(Questions, answers);// puts questions in labels

		scrollBar = new JScrollBar();
		testPanel.add(scrollBar);
		resultButton = new JButton("Results"); // shows the results after the test is completed
		resultButton.addActionListener(this);

		testPanel.add(resultButton);

		this.setVisible(true);
		this.frame.add(this);
	}

	ArrayList<String> Questions = new ArrayList<String>();

	public void getQuestions(Pattern patternString) {
		String line = "";
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.startsWith("Question:")) {
				Questions.add(line);
			}
		}

		System.out.println("Questions: " + Questions.size());
	}

	/*
	 * public void getQuestions(Pattern patternString, String line) { Matcher
	 * matcher = patternString.matcher(line); while (scanner.hasNextLine()) { line =
	 * scanner.nextLine(); if (matcher.matches()) { Questions.add(line); }
	 * 
	 * }
	 * 
	 * // return Questions; }
	 */

	String currentLine;

	public Map<String, Integer> getAnswers(Map<String, Integer> answerMap) throws FileNotFoundException {
//		testFile = new File(TestPanel.class.getResourceAsStream("test/test.txt"));
		Scanner sc = new Scanner(TestPanel.class.getResourceAsStream("test/test.txt"));
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if (nextLine.isEmpty()) {
				continue;
			}
			if (nextLine.charAt(0) == '1') {
//				currentLine = sc.toString();
				answerMap.put(nextLine.substring(2), 1);
			} else if (nextLine.charAt(0) == '2') {
//				currentLine = sc.toString();
				answerMap.put(nextLine.substring(2), 2);
			} else if (nextLine.charAt(0) == '3') {
//				currentLine = sc.toString();
				answerMap.put(nextLine.substring(2), 3);
			}
		}
		System.out.println("answerMap: " + answerMap.size());
		return answerMap;
	}

	// for each question there will be a group of three buttons
	ButtonGroup[] buttonGroups = new ButtonGroup[10];

	public ButtonGroup[] groupAnswers(Map<String, Integer> answerMap) {
		// converting the Map to an array
		ArrayList<String> answersList = new ArrayList<String>(answerMap.keySet());
		System.out.print(answerMap.keySet().size());
		// creating an array of 30 radio buttons
		JRadioButton[] rButton = new JRadioButton[30];
		// setting the name of each button
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

	public void printTest(ArrayList questions, Map<String, Integer> answerMap) {
		JRadioButton[] radioButtons = new JRadioButton[30];
		ArrayList<String> answersList = new ArrayList<String>(answerMap.keySet());
		for (int j = 0; j < 30; j++) {
			radioButtons[j] = new JRadioButton();
			radioButtons[j].setText(answersList.get(j));
		}
		for (int i = 1; i <= 10; i++) {
			testLabel[i - 1] = new JLabel();
			testLabel[i - 1].setText(questions.get(i - 1).toString());
			testPanel.add(testLabel[i - 1]);
			int index = (3 * i) - 1;
			testPanel.add(radioButtons[index]);
			testPanel.add(radioButtons[index - 1]);
			testPanel.add(radioButtons[index - 2]);

		}
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
			MainPanel main = new MainPanel(this.frame);
		}
	}

}
