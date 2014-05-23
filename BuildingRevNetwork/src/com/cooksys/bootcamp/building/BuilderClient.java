package com.cooksys.bootcamp.building;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class BuilderClient {
	private int budget = 200;
	private final int cookCost = 20;
	private final int empireCost = 50;
	private final String localhost = "localhost";

	public BuilderClient() {
		try {
			Socket socket = new Socket(localhost, Builder.PORT);

			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();
			
			outputStream.write(budget);
			outputStream.write(cookCost);
			outputStream.write(empireCost);

			Random random = new Random();



			int cost = 0;

			int total = 10;
			int correct = 0;

				for (int i = 0; i < total; i++) {
					switch (random.nextInt(2)) {
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
					else {
						System.out.println("Correct!");
						correct++;
					}
				}

				System.out.println(((double) correct / total * 100) + "%");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new BuilderClient();
	}
}
