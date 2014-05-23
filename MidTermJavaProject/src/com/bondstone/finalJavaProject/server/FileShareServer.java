package com.bondstone.finalJavaProject.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FileShareServer implements Runnable
{
	private ServerSocket					serverSocket;
	private ArrayList<FileClientHandler>	fileClientHandlers	= new ArrayList<FileClientHandler>();
	private File							shareDirectory;
	private FileFileFilter					fileFilter			= new FileFileFilter();
	private String							shareDirectoryLocation;
	private volatile boolean				run					= true;
	private final String					userName;

	public FileShareServer(int port, String shareDirectoryLocation, String userName) throws IOException
	{
		serverSocket = new ServerSocket(port);

		this.shareDirectoryLocation = shareDirectoryLocation;
		this.userName = userName;

		shareDirectory = new File(shareDirectoryLocation);

		if (!shareDirectory.exists())
			shareDirectory.mkdir();
		else if (shareDirectory.isFile())
			throw new IllegalArgumentException("Share directory cannot be a File!");

		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void run()
	{
		while (run)
		{
			try
			{
				Socket socket = serverSocket.accept();

				synchronized (fileClientHandlers)
				{
					fileClientHandlers.add(new FileClientHandler(this, socket));
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		try
		{
			serverSocket.close();
		}
		catch (IOException e)
		{
			// Hmmm...
			e.printStackTrace();
		}
	}

	public void clientDisconected(FileClientHandler client)
	{
		synchronized (fileClientHandlers)
		{
			fileClientHandlers.remove(client);
		}
	}

	public File[] getListOfFiles()
	{
		return shareDirectory.listFiles(fileFilter);
	}

	public String getShareDirectoryLocation()
	{
		return shareDirectoryLocation;
	}

	public void stop()
	{
		run = false;

		synchronized (fileClientHandlers)
		{
			for (FileClientHandler clientHandler : fileClientHandlers)
				clientHandler.stop();
		}
	}

	public String getUserName()
	{
		return userName;
	}
}
