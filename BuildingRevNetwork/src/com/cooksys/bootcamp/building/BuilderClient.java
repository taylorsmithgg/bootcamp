package com.cooksys.bootcamp.building;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class BuilderClient
{
	private int				budget		= 200;
	private final int		cookCost	= 20;
	private final int		empireCost	= 50;
	private final String	Michael		= "192.168.20.140";
	private final String	Marlon		= "192.168.20.128";
	private final String	Phillip		= "192.168.20.145";
	private final String	Raymond		= "192.168.20.134";
	private final String	Peter		= "192.168.20.138";
	private final String	Bill		= "192.168.20.121";
	private final String	Derek		= "192.168.20.112";
	private final String	Justin		= "192.168.20.137";
	private final String	Jean		= "192.168.20.116";
	private final String	Madison		= "192.168.20.102";
	private final String	localhost	= "localhost";

	public BuilderClient()
	{
		try
		{
			Socket socket = new Socket(Raymond, 2663);

			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(budget);
			outputStream.write(cookCost);
			outputStream.write(empireCost);

			Random random = new Random();

			InputStream inputStream = socket.getInputStream();

			int cost = 0;

			int total = 10;
			int correct = 0;

			for (int i = 0; i < total; i++)
			{
				switch (random.nextInt(2))
				{
					case 0:
						System.out.println("Build a Cook!");
						outputStream.write('C');
						cost = cookCost;
						break;

					case 1:
						System.out.println("Build an Empire!");
						outputStream.write('E');
						cost = empireCost;
						break;

					default:
						System.out.println("WTF!?");
						break;
				}

				char correctResponse;

				if (budget >= cost)
					correctResponse = 'Y';
				else
					correctResponse = 'N';

				int response = inputStream.read();

				if ((char) response == 'Y')
					budget -= cost;

				System.out.print("Response should be '" + correctResponse
						+ "' it is '" + (char) response + ", ");

				if ((char) response != correctResponse)
					System.out.println("WRONG!");
				else
				{
					System.out.println("Correct!");
					correct++;
				}
			}

			System.out.println(((double) correct / total * 100) + "%");
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new BuilderClient();
	}
}
