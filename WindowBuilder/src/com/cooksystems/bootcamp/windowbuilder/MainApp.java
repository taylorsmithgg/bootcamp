package com.cooksystems.bootcamp.windowbuilder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainApp {

	private JFrame frame;
	private JLabel lblInitialString;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelBottom = new JPanel();
		frame.getContentPane().add(panelBottom, BorderLayout.SOUTH);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInitialString.setText(textField.getText());
			}
		});
		panelBottom.add(btnSubmit);
		
		JPanel panelTop = new JPanel();
		frame.getContentPane().add(panelTop, BorderLayout.NORTH);
		
		lblInitialString = new JLabel("Initial String");
		panelTop.add(lblInitialString);
		
		JPanel panelCenter = new JPanel();
		frame.getContentPane().add(panelCenter, BorderLayout.CENTER);
		
		textField = new JTextField();
		panelCenter.add(textField);
		textField.setColumns(10);
	}

	protected JLabel getLblInitialString() {
		return lblInitialString;
	}
}
