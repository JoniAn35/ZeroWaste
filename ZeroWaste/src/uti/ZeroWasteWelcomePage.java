package uti;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ZeroWasteWelcomePage extends JPanel implements ActionListener{
	JFrame frame;
	
	JPanel mainPanel;
	JPanel topPanel;
	JPanel textPanel;
	JPanel buttonPanel;
	JPanel bottomPanel;
	
	JLabel titleLabel;
	JLabel infoLabel;
	JLabel imageLabel;
	
	JButton start;

	public ZeroWasteWelcomePage(JFrame frame) throws IOException {
		this.frame = frame;
		this.frame.setSize(850, 400);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		this.add(mainPanel);
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));
		mainPanel.add(topPanel);
		
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(textPanel);
		
		titleLabel = new JLabel("ZERO WASTE", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Verdana", Font.ITALIC + Font.BOLD, 40));
		titleLabel.setForeground(new Color(0, 153, 0));
		textPanel.add(titleLabel);
		
		String textHtml = "<html><div style='text-align: center;'><p>Our mission is to make people more aware of their surrounding."
				+ "<br />" + "We aim to show how easy the techiques of zero waste actually are." + "<br />"
				+ "One could save the environment while fulfilling the tasks given and enjoying themselves</p></div></html>";
		infoLabel = new JLabel(textHtml, SwingConstants.CENTER);
		infoLabel.setForeground(new Color(34, 139, 34));
		textPanel.add(infoLabel);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(buttonPanel, FlowLayout.CENTER);
		
		start = new JButton("Start");
		start.addActionListener(this);
		buttonPanel.add(start);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(bottomPanel);
		
		imageLabel = new JLabel(new ImageIcon("/Users/yoanaangelova/Desktop/java/ZeroWaste/ZeroWaste/src/border.png"));
		bottomPanel.add(imageLabel);
		
		this.setVisible(true);
		this.frame.add(this);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.setVisible(false);
		try {
			TestPanel test = new TestPanel(this.frame);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
