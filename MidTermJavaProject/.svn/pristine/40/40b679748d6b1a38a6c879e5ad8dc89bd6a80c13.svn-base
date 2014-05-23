package com.bondstone.finalJavaProject.gui;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.bondstone.finalJavaProject.server.FileFileFilter;

public class LocalFilePanel extends FilePanel
{
	private static final long		serialVersionUID	= 74231844435200375L;
	private File					localDirectory;
	private String					localPath;

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

	public LocalFilePanel(final String localPath)
	{
		super(UPLOAD_BACKGROUND);

		this.localPath = localPath;

		statusLabel.setText("Local Files");

		JMenuItem openMenuItem = new JMenuItem("Open");

		try
		{
			openMenuItem.setIcon(new ImageIcon(ImageIO.read(new File("images\\open.png"))));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		openMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final String fileName = localPath + model.get(popupIndex);

				try
				{
					Desktop.getDesktop().open(new File(fileName));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cannot Open File", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JMenuItem deleteMenuItem = new JMenuItem("Delete");

		try
		{
			deleteMenuItem.setIcon(new ImageIcon(ImageIO.read(new File("images\\delete.png"))));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		deleteMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final String fileName = localPath + model.get(popupIndex);

				new File(fileName).delete();

				updateList();
			}
		});

		listItemPopupMenu.add(deleteMenuItem, 0);
		listItemPopupMenu.add(openMenuItem, 0);

		localDirectory = new File(localPath);

		if (!localDirectory.exists())
			localDirectory.mkdir();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(2, 5, 2, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		add(statusLabel, gbc);

		gbc.gridx++;
		gbc.anchor = GridBagConstraints.EAST;
		add(refresh, gbc);

		gbc.gridy++;
		gbc.gridx = 0;
		gbc.weighty = 1;
		gbc.weightx = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(scrollPane, gbc);

		updateList();
	}

	@Override
	protected void updateList()
	{
		final File[] files = localDirectory.listFiles(new FileFileFilter());

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				model.removeAllElements();

				for (File file : files)
					model.addElement(file.getName());
			}
		});
	}

	@Override
	protected void listDoubleClicked(MouseEvent mouseEvent)
	{
		int index = list.locationToIndex(mouseEvent.getPoint());

		if (index >= 0)
		{
			final String fileName = localPath + model.get(index);

			try
			{
				Desktop.getDesktop().open(new File(fileName));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();

				JOptionPane.showMessageDialog(null, e1.getMessage(), "Cannot Open File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
