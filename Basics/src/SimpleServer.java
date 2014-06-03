import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer
{
	public static final int	PORT	= 2665;

	public SimpleServer()
	{
		try
		{
			System.out.println("Binding...");

			// Bind to PORT
			ServerSocket serverSocket = new ServerSocket(PORT);

			System.out.println("\tBound.");

			System.out.println("Waiting for Client...");

			// Wait for a client to connect to our port and store the Socket so
			// we can communicate
			Socket clientSocket = serverSocket.accept();

			System.out.println("\tClient Connected.");

			int character;

			// Print out all characters that come across the network
			while ((character = clientSocket.getInputStream().read()) != -1)
				System.out.print((char) character);

			System.out.println("End of Stream, Exiting...");
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		new SimpleServer();
	}
}
