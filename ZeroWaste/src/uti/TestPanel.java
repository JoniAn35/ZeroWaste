package uti;

import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 * 
 * @author yoanaangelova
 * TODO: 
 * 		Роси: да се оправи изчиснявнето на резултата и  JOptionPane-а (размер, текст и бутон)
 *
 */

public class TestPanel extends JPanel implements ActionListener {
	JFrame frame;

	JPanel testPanel;
	JScrollPane scrollPane;
	JButton resultButton;
	File testFile;
	Scanner scanner;
	JLabel[] testLabel;
	
	User user;

	Map<String, Integer> answers; // String - key(answer); integer - value(points)

	public TestPanel(JFrame frame) throws IOException {
		this.user = user;
		
		this.frame = frame;
		this.frame.setSize(850, 400);
		this.frame.setLocationRelativeTo(null);

		testPanel = new JPanel();// shows the test
		testPanel.setBorder(BorderFactory.createEmptyBorder());
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
		this.add(testPanel);
		
		JScrollPane scr = new JScrollPane(testPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setPreferredSize(new Dimension(850, 400));
		add(scr);

		testLabel = new JLabel[10];
		resultButton = new JButton("Results"); // shows the results after the test is completed
		resultButton.addActionListener(this);
		answers = new HashMap<String, Integer>();

		scanner = new Scanner(TestPanel.class.getResourceAsStream("test/test.txt"));
		Pattern p = Pattern.compile("Question:");

		getQuestions(p);// finds the questions in the file
		getAnswers(answers);// finds the answers
		groupAnswers(answers);// puts the answers in groups of three
		printTest(Questions, answers);// puts questions in labels
		
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
//			line = String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 850, line);
			if (line.startsWith("Question:")) {
				Questions.add(line);
			}
		}

		System.out.println("Questions: " + Questions.size());
	}
	
	String currentLine;

	public Map<String, Integer> getAnswers(Map<String, Integer> answerMap) throws FileNotFoundException {
		Scanner sc = new Scanner(TestPanel.class.getResourceAsStream("test/test.txt"));
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if (nextLine.isEmpty()) {
				continue;
			}
			if (nextLine.charAt(0) == '1') {
				answerMap.put(nextLine.substring(2), 1);
			} else if (nextLine.charAt(0) == '2') {
				answerMap.put(nextLine.substring(2), 2);
			} else if (nextLine.charAt(0) == '3') {
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

	JRadioButton[] radioButtons = new JRadioButton[30];
	
	public void printTest(ArrayList<String> questions, Map<String, Integer> answerMap) {
		ArrayList<String> answersList = new ArrayList<String>(answerMap.keySet());
		for (int j = 0; j < 30; j++) {
			radioButtons[j] = new JRadioButton();
			String answerText = answersList.get(j);
			answerText = String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 600, answerText);
			radioButtons[j].setText(answerText);
		}
		for (int i = 1; i <= 10; i++) {
			testLabel[i - 1] = new JLabel();
			String questionText = questions.get(i - 1);
			questionText = String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 600, questionText);
			testLabel[i - 1].setText(questionText.toString());
			testPanel.add(testLabel[i - 1]);
			int index = (3 * i) - 1;
			testPanel.add(radioButtons[index]);
			testPanel.add(radioButtons[index - 1]);
			testPanel.add(radioButtons[index - 2]);

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int result = calculateResult(radioButtons, answers);
		UIManager.put("OptionPane.DEFAULT_OPTION", "Let's go");
		int n = JOptionPane.showConfirmDialog(null, 
                user.print(), "Your result", JOptionPane.DEFAULT_OPTION);
		if (n == JOptionPane.DEFAULT_OPTION) {
			this.setVisible(false);
			MainPanel main = new MainPanel(this.frame, result, this.user);
		}
	}
	
	public int calculateResult(JRadioButton[] radioButtons, Map<String, Integer> answerMap) {
		int result = 0;
		for (int j = 0; j < 30; j++) {
			if (radioButtons[j].isSelected()) {
				String t = radioButtons[j].getText();
				if(answerMap.containsKey(t)) {
					result += answerMap.get(t);
				}
				else {
					System.out.println("No");
				}
				
			}
		}
		return result;
	}

}
