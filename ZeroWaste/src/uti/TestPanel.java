import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
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

public class TestPanel extends JFrame implements ActionListener {
	
	JPanel testPanel;
	JScrollBar scrollBar;
	JButton resultButton;
	File testFile;
	Scanner scanner;
	JLabel[] testLabel;
	
	Map<String, Integer> answers; // String - key(answer); integer - value(points)
	
	public TestPanel() throws IOException {

//		JFrame frame = new JFrame();

		testPanel = new JPanel();// shows the test
		testPanel.setBorder(BorderFactory.createEmptyBorder());
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
		
		this.setSize(850, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.add(testPanel);

		
		testLabel = new JLabel[10];
		scrollBar = new JScrollBar();
		testPanel.add(scrollBar);
		resultButton = new JButton("Results"); // shows the results after the test is completed
		resultButton.addActionListener(this);
		answers = new HashMap<String, Integer>();
		testFile = new File("C:\\Users\\tsvet\\Music\\Workspace.eclipse\\ZeroWaste\\src\\test");
		scanner = new Scanner(testFile);
		Pattern p = Pattern.compile("Question:");
		
		getQuestions(p, scanner.nextLine());// finds the questions in the file
		getAnswers(answers);// finds the answers
		groupAnswers(answers);// puts the answers in groups of three 
		printTest(Questions, answers);//puts questions in labels
			
		
// The code uses Pattern and Matcher classes to search for the string. 
//If a line contains the string we are looking for we store its line number in a List. 
		
		
		                            
		
//		testPanel.add(resultButton);		
		this.setVisible(true);
		

//		frame.add(testPanel, BorderLayout.CENTER); // add panel to the frame
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setTitle("Main Panel");
//		frame.pack(); // set the window to match a certain size
//		frame.setVisible(true); // set the window to be visible

	}

    ArrayList<String> Questions = new ArrayList<String>();
	public ArrayList getQuestions(Pattern patternString, String line) {	
		 Matcher matcher = patternString.matcher(line);
		 while (scanner.hasNextLine()) {
			 line = scanner.nextLine(); 
			 if (matcher.matches()) {			
					 Questions.add(line);
				 }
			
			 }
		
		return Questions;
		 }
		
	String currentLine;
	public Map getAnswers (Map<String, Integer> answerMap) {
		while (scanner.hasNextLine()) {
			if (scanner.nextLine().charAt(0) == 1) {
				currentLine = scanner.toString() ;
				answerMap.put(currentLine, 1);
			} else if (scanner.nextLine().charAt(0) == 2) {
				currentLine = scanner.toString() ;
				answerMap.put(currentLine, 2);
		} else if (scanner.nextLine().charAt(0) == 3) {
			currentLine = scanner.toString() ;
			answerMap.put(currentLine, 3);
	}
		}
		return answerMap;
	}	 
	
	// for each question there will be a group of three buttons
	ButtonGroup[] buttonGroups = new ButtonGroup[10];
	public ButtonGroup[] groupAnswers (Map<String, Integer> answerMap) {
			// converting the Map to an array
			ArrayList<String> answersList = new ArrayList<String>(answerMap.keySet());
			// creating an array of 30 radio buttons
			JRadioButton[] rButton = null;
			//setting the name of each button
			for (int j = 0; j < 30; j ++) {
				rButton = new JRadioButton[30];
				rButton[j].setName(answersList.get(j));			
			}
			
		// adding every three buttons to a group
		// for example: i = 3; the buttons will be 7,8,9
		for (int i = 0; i < 10; i++) {
			buttonGroups[i].add(rButton[3*i]);
			buttonGroups[i].add(rButton[3*i - 1]);
			buttonGroups[i].add(rButton[3*i - 2]);
		}
		return buttonGroups;
	}
	
	public void printTest (ArrayList questions, Map<String, Integer> answerMap) {
		JRadioButton[] radioButtons = null;
		ArrayList<String> answersList = new ArrayList<String>(answerMap.keySet());
		for (int j = 0; j < 30; j ++) {
			radioButtons = new JRadioButton[30];
			radioButtons[j].setName(answersList.get(j));			
		}
		for (int i = 0; i < 10; i ++) {
		testLabel[i].setText(questions.get(i).toString());
		testPanel.add(testLabel[i]);		
		testPanel.add(radioButtons[3*i]);
		testPanel.add(radioButtons[3*i - 1]);
		testPanel.add(radioButtons[3*i - 2]);

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
			MainPanel main = new MainPanel();
		}
	}


}