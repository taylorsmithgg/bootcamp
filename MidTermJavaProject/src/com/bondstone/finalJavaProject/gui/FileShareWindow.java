package com.bondstone.finalJavaProject.gui;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.bondstone.finalJavaProject.FileShare;

public class FileShareWindow extends JFrame implements Runnable, WindowListener
{
	private static final long	serialVersionUID		= 2031908296443534437L;
	private FileShare			fileShare;
	private static final int	OFFSET					= 5;
	public static final Color	LIST_BACKGROUND_COLOR	= Color.WHITE;											// new
																												// Color(241
																												// -
																												// OFFSET,
																												// 245
																												// -
																												// OFFSET,
																												// 250
																												// -
																												// OFFSET);
																												// //
																												// Color.LIGHT_GRAY;
	public static final Color	FIRST_ROW_COLOR			= new Color(241 - OFFSET, 245 - OFFSET, 250 - OFFSET);
	public static final Color	SECOND_ROW_COLOR		= Color.WHITE;											// new
																												// Color(241,
																												// 241,
																												// 241);

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
		}

		new FileShareWindow();
	}

	public FileShareWindow()
	{
		try
		{
			fileShare = new FileShare();
		}
		catch (SAXException | IOException | ParserConfigurationException e)
		{
			e.printStackTrace();

			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			System.exit(-1);
		}

		SwingUtilities.invokeLater(this);
	}

	@Override
	public void run()
	{
		setTitle("Bondstone File Sharing Program");
		try
		{
			setIconImage(ImageIO.read(new File("images\\cloud-storage-icon.png")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		addWindowListener(this);

		JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new LocalFilePanel(fileShare.getSHARE_DIRECTORY_LOCATION()),
				new NetworkPanel(fileShare));
		verticalSplitPane.setResizeWeight(.5);

		JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, verticalSplitPane, new DownloadedFilesPanel(
				fileShare.getDOWNLOAD_DIRECTORY_LOCATION()));
		horizontalSplitPane.setResizeWeight(.5);

		add(horizontalSplitPane);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		try
		{
			fileShare.exit();

			System.exit(0);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();

			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			System.exit(-1);
		}
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}
}
