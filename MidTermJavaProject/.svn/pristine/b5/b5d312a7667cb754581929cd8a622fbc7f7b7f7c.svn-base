package com.bondstone.finalJavaProject.gui;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.filechooser.FileSystemView;

public class AlternatingRowColorListCellRenderer extends JLabel implements ListCellRenderer<String>
{
	private static final long	serialVersionUID	= -6978221439843557769L;
	private Color				color1;
	private Color				color2;

	public AlternatingRowColorListCellRenderer(Color color1, Color color2)
	{
		this.color1 = color1;
		this.color2 = color2;

		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends String> arg0, String value, int index, boolean isSelected, boolean hasFocus)
	{
		File file;
		try
		{
			file = File.createTempFile("temp", value);
			setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
			file.delete();
		}
		catch (IOException e)
		{
		}

		setText(value);

		if (isSelected)
		{
			setForeground(Color.WHITE);
			setBackground(new Color(51, 153, 255));
			setBorder(BorderFactory.createDashedBorder(null));
		}
		else
		{
			setForeground(Color.BLACK);
			setBorder(null);

			if (index % 2 == 0)
				setBackground(color1);
			else
				setBackground(color2);
		}

		return this;
	}
}
