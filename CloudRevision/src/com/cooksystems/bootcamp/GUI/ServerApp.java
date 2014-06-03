package com.cooksystems.bootcamp.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cooksystems.bootcamp.server.Server;

public class ServerApp {
	Server server;
	private JFrame frame;
	Thread serverThread;
	ActionEvent connect;
	JButton jb;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerApp window = new ServerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerApp() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server = new Server();
				jb = (JButton)e.getSource();
				jb.setEnabled(false);
				connect = e;
				
			}
		});

		JButton btnStopServer = new JButton("Stop Server");
		btnStopServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.close();
				jb = (JButton)connect.getSource();
				jb.setEnabled(true);
			}
		});

		panel.add(btnStartServer);
		panel.add(btnStopServer);
	}

}
