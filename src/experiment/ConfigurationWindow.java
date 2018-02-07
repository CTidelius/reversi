package experiment;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ConfigurationWindow {

	private JFrame frame = new JFrame("Configurations");

	public ConfigurationWindow() {
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JRadioButton r1 = new JRadioButton("Black");
		JRadioButton r2 = new JRadioButton("White");

		r1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r1.setSelected(true);
				r2.setSelected(false);
			}
		});

		r2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r1.setSelected(false);
				r2.setSelected(true);
			}
		});

		JPanel colorRadioPanel = new JPanel();
		colorRadioPanel.add(new JLabel("Player color:"));
		colorRadioPanel.add(r1);
		colorRadioPanel.add(r2);
		colorRadioPanel.setPreferredSize(new Dimension(300, 40));

		JPanel aiSearchDepthPanel = new JPanel();
		JTextField aiSearchDepthField = new JTextField(5);
		aiSearchDepthPanel.add(new JLabel("AI search depth (plies)"));
		aiSearchDepthPanel.add(aiSearchDepthField);
		aiSearchDepthPanel.setPreferredSize(new Dimension(300, 40));

		JPanel aiMaxSearchTimePanel = new JPanel();
		JTextField aiMaxSearchTimeField = new JTextField(5);
		aiMaxSearchTimePanel.add(new JLabel("AI max search time (ms)"));
		aiMaxSearchTimePanel.add(aiMaxSearchTimeField);
		aiMaxSearchTimePanel.setPreferredSize(new Dimension(300, 40));

		JButton startButton = new JButton("Start");
		JButton exitButton = new JButton("Exit");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(exitButton);
		buttonPanel.add(startButton);

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		frame.add(colorRadioPanel);
		frame.add(aiSearchDepthPanel);
		frame.add(aiMaxSearchTimePanel);
		frame.add(buttonPanel);

		frame.setLayout(new GridLayout(4, 1));
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		ConfigurationWindow p = new ConfigurationWindow();
	}

}
