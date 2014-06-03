package com.cooksys.bootcamp.building;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClient
{
	public SimpleClient()
	{
		try
		{
			Socket socket = new Socket("localhost", Builder.PORT);

			if (socket.isConnected())
			{
				InputStreamReader reader = new InputStreamReader(System.in);

				int character;

				while ((character = reader.read()) != -1)
					socket.getOutputStream().write(character);
			}
		}
		catch (UnknownHostException e)
		{
			// This is weird...
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new SimpleClient();
	}
}
