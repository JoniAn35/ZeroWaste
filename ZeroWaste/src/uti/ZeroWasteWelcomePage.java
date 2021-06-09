package uti;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ZeroWasteWelcomePage extends JFrame implements ActionListener{
	JButton start = new JButton("Start");

	public ZeroWasteWelcomePage() {
		// setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
//		panel.setAlignmentY(CENTER_ALIGNMENT);
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		this.setSize(850, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.add(panel, BorderLayout.CENTER);

		JLabel title = new JLabel("ZERO WASTE");
		title.setFont(new Font("Verdana", Font.ITALIC + Font.BOLD, 40));
		title.setForeground(new Color(0, 153, 0));
		panel.add(title);

		String text = "<html><div style='text-align: center;'><p>Our mission is to make people more aware of their surrounding."
				+ "<br />" + "We aim to show how easy the techiques of zero waste actually are." + "<br />"
				+ "One could save the environment while fulfilling the tasks given and enjoying themselves</p></div></html>";
		JLabel info = new JLabel(text);
		info.setForeground(new Color(34, 139, 34));
		panel.add(info);
		
		start.addActionListener(this);
		panel.add(start, BorderLayout.CENTER);

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon("/Users/yoanaangelova/Desktop/java/ZeroWaste/ZeroWaste/src/border.png"));
		imagePanel.add(imageLabel);
		imagePanel.setAlignmentY(BOTTOM_ALIGNMENT);
		panel.add(imagePanel);
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.setVisible(false);
		TestPanel test = new TestPanel();
	}

}
