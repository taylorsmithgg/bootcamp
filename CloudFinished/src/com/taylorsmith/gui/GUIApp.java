package com.taylorsmith.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import com.taylorsmith.server.Server;

class GUIApp {

	private JFrame frame;
	private String host;
    private Client client;

	private JButton temp;

	private DefaultListModel<String> remoteListModel = new DefaultListModel<>();
	private DefaultListModel<String> localListModel = new DefaultListModel<>();
	private DefaultListModel<String> downloadedListModel = new DefaultListModel<>();

	private JList<String> downloadedList = new JList<>();
	private final JList<String> localList = new JList<>();
	private JList<String> remoteList = new JList<>();

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

	private GUIApp() {
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

		final JComboBox<String> menuHost = new JComboBox<>();
		for (String s : server.hostList) {
			menuHost.addItem(s);
		}
		panel.add(menuHost);

		/***
		 * Connection button action: get the selected value from menuHost and
		 * create new Client using it as an argument to direct connections to
		 * the intended host
		 */
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				host = menuHost.getSelectedItem().toString();
				client = new Client(host);

				for (String file : client.getLocalFiles()) {
					localListModel.addElement(file);
				}

				try {
					for (String file : client.getServerFiles()) {
						remoteListModel.addElement(file);
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

		/***
		 * Disconnect button action: closes the client's current socket, calls
		 * the XML logging method, and disables the connect button to avoid
		 * spamming
		 */
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				temp.setEnabled(true);

				client.disconnect();

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

        JLabel lblDeveloper = new JLabel("");
		panel.add(lblDeveloper);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.5);
		splitPane.setLeftComponent(splitPane_1);

		panelRemote(splitPane_1);
		panelDownload(splitPane_1);
		localPanel(splitPane);
	}

	private void localPanel(JSplitPane splitPane) {
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

		/***
		 * Refresh action for local files
		 */
		JButton btnRefreshLocal = new JButton("Refresh");
		btnRefreshLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				localListModel = new DefaultListModel<>();

				for (String file : client.getLocalFiles()) {
					localListModel.addElement(file);
				}

				localList.setModel(localListModel);
			}
		});
		panel_1.add(btnRefreshLocal);
	}

	private void panelDownload(JSplitPane splitPane_1) {
		JPanel panelDownloaded = new JPanel();
		splitPane_1.setRightComponent(panelDownloaded);
		panelDownloaded.setLayout(new BorderLayout(0, 0));

		JScrollPane downloadedScroll = new JScrollPane();
		panelDownloaded.add(downloadedScroll);

		JPanel panel = new JPanel();

		panelDownloaded.add(panel, BorderLayout.SOUTH);

		downloadedList = new JList<>();
		downloadedList.setBorder(new TitledBorder(null, "Downloaded Files",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		downloadedScroll.setViewportView(downloadedList);

		JButton btnRefreshDownload = new JButton("Refresh");
		btnRefreshDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downloadedListModel = new DefaultListModel<>();

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

		remoteList = new JList<>();
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
				remoteListModel = new DefaultListModel<>();

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