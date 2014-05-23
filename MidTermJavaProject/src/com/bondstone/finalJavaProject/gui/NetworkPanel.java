package com.bondstone.finalJavaProject.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.bondstone.finalJavaProject.FileShare;
import com.bondstone.finalJavaProject.UnknownCommandException;
import com.bondstone.finalJavaProject.client.NoConnectionException;

public class NetworkPanel extends FilePanel
{
	private static final long		serialVersionUID	= 200811050788577766L;
	private FileShare				fileShare;

	private static BufferedImage	UPLOAD_BACKGROUND;

	static
	{
		try
		{
			UPLOAD_BACKGROUND = ImageIO.read(new File("images\\upload.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();

			UPLOAD_BACKGROUND = null;
		}
	}

	public NetworkPanel(final FileShare fileShare)
	{
		super(UPLOAD_BACKGROUND);

		this.fileShare = fileShare;

		statusLabel.setText("Not Connected");

		JMenuItem downloadMenuItem = new JMenuItem("Download");

		try
		{
			downloadMenuItem.setIcon(new ImageIcon(ImageIO.read(new File("images\\download.png"))));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		downloadMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final String fileName = model.get(popupIndex);

				initiateDownload(fileName);
			}
		});

		listItemPopupMenu.add(downloadMenuItem, 0);

		JLabel ipLabel = new JLabel("IP Address:");
		final JComboBox<String> ipComboBox = new JComboBox<>();

		for (String string : fileShare.getIPAddresses())
			ipComboBox.addItem(string);

		ipComboBox.setSelectedIndex(0);

		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File("images\\nav-connect.gif"));
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
		}

		final JButton connectButton = new JButton("Connect", new ImageIcon(image));
		connectButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						connectButton.setEnabled(false);
						String name = fileShare.connect((String) ipComboBox.getSelectedItem());
						connectButton.setEnabled(true);

						if (name != null)
						{
							statusLabel.setForeground(Color.BLACK);
							statusLabel.setText("Connected to " + name);

							updateList();
						}
						else
						{
							statusLabel.setForeground(Color.RED);
							statusLabel.setText("Could not Connect");
						}

						statusLabel.repaint();
					}
				}).start();
			}
		});

		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0);

		gbc.gridwidth = 2;
		add(statusLabel, gbc);

		gbc.gridx = 2;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(refresh, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		add(ipLabel, gbc);

		gbc.gridx++;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(ipComboBox, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		add(connectButton, gbc);

		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(scrollPane, gbc);
	}

	@Override
	protected void updateList()
	{
		try
		{
			final ArrayList<String> files = fileShare.getRemoteFiles();

			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					model.removeAllElements();

					for (String file : files)
						model.addElement(file);
				}
			});
		}
		catch (NoConnectionException e)
		{
			e.printStackTrace();

			JOptionPane.showMessageDialog(null, e.getMessage(), "Not Connected", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	protected void listDoubleClicked(MouseEvent mouseEvent)
	{
		final String fileName = model.get(list.locationToIndex(mouseEvent.getPoint()));
		System.out.println("Downloading " + fileName);

		initiateDownload(fileName);
	}

	private void initiateDownload(final String fileName)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					fileShare.downloadFile(fileName);

					statusLabel.setForeground(Color.BLACK);
					statusLabel.setText("Download Successful");
				}
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();

					statusLabel.setForeground(Color.RED);
					statusLabel.setText("File No Longer Exists");
				}
				catch (NoConnectionException e1)
				{
					e1.printStackTrace();

					statusLabel.setForeground(Color.RED);
					statusLabel.setText("No Connection");
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				catch (UnknownCommandException e1)
				{
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null, "Server responded with an unknown command", "Server Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}).start();
	}
}
