package com.bondstone.finalJavaProject.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class RefreshAction extends AbstractAction
{
	private static final long	serialVersionUID	= -2391028805062371586L;
	private static ImageIcon	REFRESH_ICON;

	static
	{
		try
		{
			REFRESH_ICON = new ImageIcon(ImageIO.read(new File("images\\refresh.png")));
		}
		catch (IOException e)
		{
			e.printStackTrace();

			REFRESH_ICON = null;
		}
	}

	private ActionListener		actionListener;

	public RefreshAction(ActionListener actionListener)
	{
		this.actionListener = actionListener;

		putValue(Action.NAME, "Refresh");
		putValue(Action.SMALL_ICON, REFRESH_ICON);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		actionListener.actionPerformed(arg0);
	}
}
