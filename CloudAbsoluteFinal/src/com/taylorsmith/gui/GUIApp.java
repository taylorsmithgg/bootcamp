package com.taylorsmith.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import com.taylorsmith.client.Client;
import com.taylorsmith.cloud.Handler;
import com.taylorsmith.server.Server;

public class GUIApp {

	private JFrame frame;
	private String host;
	JLabel lblDeveloper;
	public Client client;

	JButton temp;

	DefaultListModel<String> remoteListModel = new DefaultListModel<String>();
	DefaultListModel<String> localListModel = new DefaultListModel<String>();
	DefaultListModel<String> downloadedListModel = new DefaultListModel<String>();

	JList<String> downloadedList = new JList<String>();
	JList<String> localList = new JList<String>();
	JList<String> remoteList = new JList<String>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIApp window = new GUIApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIApp() {
		initialize();
	}

	private void initialize() {

		Server server = new Server();

		frame = new JFrame();
		frame.setBounds(100, 100, 446, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		final JComboBox<String> menuHost = new JComboBox<String>();

		menuHost.addItem("Select");
		menuHost.addItem("localhost");
		menuHost.addItem(Handler.Tyler);

		panel.add(menuHost);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				host = menuHost.getSelectedItem().toString();
				client = new Client(host);

				for (String file : client.getLocalFiles()) {
					localListModel.addElement(file);
					System.out.println(file + "added");
				}

				try {
					for (String file : client.getServerFiles()) {
						remoteListModel.addElement(file.toString());
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				remoteList.setModel(remoteListModel);
				localList.setModel(localListModel);

				temp = (JButton) e.getSource();
				temp.setEnabled(false);
			}
		});
		panel.add(btnConnect);

		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				temp.setEnabled(true);

				try {
					client.disconnect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (!remoteListModel.isEmpty()) {
					remoteListModel.removeAllElements();
				}
				if (!localListModel.isEmpty()) {
					localListModel.removeAllElements();
				}
				if (!downloadedListModel.isEmpty()) {
					downloadedListModel.removeAllElements();
				}
			}
		});
		panel.add(btnDisconnect);

		lblDeveloper = new JLabel("");
		panel.add(lblDeveloper);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.5);
		splitPane.setLeftComponent(splitPane_1);

		// Remote section
		panelRemote(splitPane_1);

		// Downloaded section
		panelDownload(splitPane_1);

		// Local section
		localPanel(splitPane);
	}

	private JPanel localPanel(JSplitPane splitPane) {
		JPanel localPanel = new JPanel();
		splitPane.setRightComponent(localPanel);
		localPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane localScroll = new JScrollPane();
		localPanel.add(localScroll);

		localList.setBorder(new TitledBorder(null, "Local Files",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		localScroll.setViewportView(localList);

		JPanel panel = new JPanel();
		localPanel.add(panel, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		localPanel.add(panel_1, BorderLayout.SOUTH);

		JButton btnRefreshLocal = new JButton("Refresh");
		btnRefreshLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				localListModel = new DefaultListModel<String>();

				for (String file : client.getLocalFiles()) {
					localListModel.addElement(file);
				}

				localList.setModel(localListModel);
			}
		});
		panel_1.add(btnRefreshLocal);
		return localPanel;
	}

	private void panelDownload(JSplitPane splitPane_1) {
		JPanel panelDownloaded = new JPanel();
		splitPane_1.setRightComponent(panelDownloaded);
		panelDownloaded.setLayout(new BorderLayout(0, 0));

		JScrollPane downloadedScroll = new JScrollPane();
		panelDownloaded.add(downloadedScroll);

		downloadedList = new JList<String>();
		downloadedList.setBorder(new TitledBorder(null, "Downloaded Files",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		downloadedScroll.setViewportView(downloadedList);

		JPanel panel = new JPanel();
		panelDownloaded.add(panel, BorderLayout.SOUTH);

		JButton btnRefreshDownload = new JButton("Refresh");
		btnRefreshDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downloadedListModel = new DefaultListModel<String>();

				for (String file : client.getDownloadHistory()) {
					downloadedListModel.addElement(file);
				}

				downloadedList.setModel(downloadedListModel);
			}
		});
		panel.add(btnRefreshDownload);
	}

	private void panelRemote(JSplitPane splitPane_1) {
		JPanel remotePanel = new JPanel();
		remotePanel.setToolTipText("");
		splitPane_1.setLeftComponent(remotePanel);
		remotePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane remoteScroll = new JScrollPane();
		remotePanel.add(remoteScroll);

		remoteList = new JList<String>();
		remoteList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = remoteList.locationToIndex(e.getPoint());
					if (index >= 0) {
						Object o = remoteList.getModel().getElementAt(index);
						try {
							System.out.println("File: " + o.toString());
							client.downloadFile(o.toString());
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		remoteList.setBorder(new TitledBorder(null, "Remote Files",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		remoteScroll.setViewportView(remoteList);

		JPanel panel = new JPanel();
		remotePanel.add(panel, BorderLayout.SOUTH);

		JButton btnRefreshRemote = new JButton("Refresh");
		btnRefreshRemote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remoteListModel = new DefaultListModel<String>();

				try {
					for (String file : client.getServerFiles()) {
						remoteListModel.addElement(file);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				remoteList.setModel(remoteListModel);
			}
		});
		panel.add(btnRefreshRemote);
	}

}