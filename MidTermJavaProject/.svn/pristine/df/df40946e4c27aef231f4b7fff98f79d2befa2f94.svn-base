package com.bondstone.finalJavaProject.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

public abstract class FilePanel extends JPanel implements ActionListener, MouseListener
{
	private static final long						serialVersionUID	= 4158965008442425237L;
	protected JList<String>							list;
	protected JScrollPane							scrollPane;
	protected DefaultListModel<String>				model				= new DefaultListModel<>();
	protected AlternatingRowColorListCellRenderer	cellRenderer		= new AlternatingRowColorListCellRenderer(FileShareWindow.FIRST_ROW_COLOR,
																				FileShareWindow.SECOND_ROW_COLOR);
	protected RefreshAction							refreshAction		= new RefreshAction(this);
	protected JButton								refresh				= new JButton(refreshAction);
	protected JLabel								statusLabel			= new JLabel();
	protected GridBagConstraints					gbc					= new GridBagConstraints();
	protected JPopupMenu							listItemPopupMenu	= new JPopupMenu();
	protected JPopupMenu							popupMenu			= new JPopupMenu();
	protected int									popupIndex			= 0;

	public FilePanel(BufferedImage background)
	{
		super(new GridBagLayout());

		list = new CustomBackgroundJList<>(background);
		scrollPane = new JScrollPane(list);

		listItemPopupMenu.add(new JMenuItem(refreshAction));

		popupMenu.add(new JMenuItem(refreshAction));

		list.setModel(model);
		list.setCellRenderer(cellRenderer);
		list.addMouseListener(this);

		scrollPane.setPreferredSize(new Dimension(300, 200));
	}

	protected abstract void updateList();

	protected abstract void listDoubleClicked(MouseEvent mouseEvent);

	@Override
	public void actionPerformed(ActionEvent e)
	{
		updateList();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 2)
			listDoubleClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.isPopupTrigger())
		{
			popupIndex = list.locationToIndex(e.getPoint());

			Rectangle bounds = list.getCellBounds(popupIndex, Integer.MAX_VALUE);

			if (popupIndex >= 0 && bounds != null && bounds.contains(e.getPoint()))
			{
				list.setSelectedIndex(popupIndex);

				listItemPopupMenu.show(list, e.getX(), e.getY());
			}
			else
				popupMenu.show(list, e.getX(), e.getY());
		}
	}
}
