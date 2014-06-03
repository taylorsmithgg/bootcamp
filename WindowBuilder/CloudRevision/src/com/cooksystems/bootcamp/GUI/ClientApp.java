package com.cooksystems.bootcamp.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import com.cooksystems.bootcamp.cloud.Client;
import com.cooksystems.bootcamp.cloud.Handler;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class ClientApp extends Handler{
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
		
		for(File f : listFiles()){
			model.addElement(f.toString());	
		}		

		String[] stringArray = listServers();
		final JComboBox<String> comboBox = new JComboBox<String>(stringArray);
		JList listShared = new JList();
		JPanel panelBottom = new JPanel();
		JPanel panelTop = new JPanel();

		JButton btnConnect = new JButton("Connect");
		btnConnect.setHorizontalAlignment(SwingConstants.LEFT);
		panelBottom.add(listShared);
		panelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelTop.add(comboBox);
		panelTop.add(btnConnect);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		listShared.setModel(model);
		frame.getContentPane().add(panelBottom);
		frame.getContentPane().add(panelTop, BorderLayout.NORTH);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.close();
			}
		});
		btnDisconnect.setHorizontalAlignment(SwingConstants.LEFT);
		panelTop.add(btnDisconnect);
		
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

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String port = comboBox.getSelectedItem().toString();
				client = new Client(port);
				client.execute();
			}
		});
		
	}

}
