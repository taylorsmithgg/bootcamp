package com.bondstone.finalJavaProject.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JList;

public class CustomBackgroundJList<T> extends JList<T>
{
	private static final long	serialVersionUID	= 1220505065047375492L;
	private BufferedImage		backgroundImage;

	public CustomBackgroundJList(BufferedImage backgroundImage)
	{
		super();
		this.backgroundImage = backgroundImage;

		setBackground(new Color(255, 255, 255, 0));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		if (backgroundImage != null)
			g.drawImage(backgroundImage, getWidth() - backgroundImage.getWidth(), getHeight() - backgroundImage.getHeight(), null);

		super.paintComponent(g);

		g.setColor(new Color(255, 255, 255, 200));
		g.drawRect(0, 0, getWidth(), getHeight());

	}

	public BufferedImage getBackgroundImage()
	{
		return backgroundImage;
	}

	public void setBackgroundImage(BufferedImage backgroundImage)
	{
		this.backgroundImage = backgroundImage;
	}
}
