package com.cooksystems.bootcamp.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.cooksystems.bootcamp.client.Client;

public class ClientApp{
	Client client;
	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientApp window = new ClientApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientApp() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DefaultListModel<String> model = new DefaultListModel<String>();
		
		model.addElement("Files Here");
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		
		
		JList<String> listShared = new JList<String>();
		listShared.setModel(model);		
		
		JPanel panelBottom = new JPanel();
		panelBottom.add(listShared);
		
		
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelTop.add(comboBox);
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String serverIp = comboBox.getSelectedItem().toString();
				try {
					client = new Client(serverIp);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panelTop.add(btnConnect);
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.close();
			}
		});
		btnDisconnect.setHorizontalAlignment(SwingConstants.LEFT);
		panelTop.add(btnDisconnect);



		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panelBottom);
		frame.getContentPane().add(panelTop, BorderLayout.NORTH);




		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.5);
		frame.getContentPane().add(splitPane);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.5);
		splitPane.setLeftComponent(splitPane_1);

		JPanel panelRemote = new JPanel();
		splitPane_1.setLeftComponent(panelRemote);

		JPanel panelDownload = new JPanel();
		splitPane_1.setRightComponent(panelDownload);

		JPanel panelLocal = new JPanel();
		splitPane.setRightComponent(panelLocal);

		JList listLocal = new JList();
		listLocal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelLocal.add(listLocal);

		listLocal.setModel(model);



	}

}
