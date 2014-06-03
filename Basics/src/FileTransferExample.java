import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.transform.TransformerFactoryConfigurationError;

@SuppressWarnings("unused")
public class FileTransferExample
{
	private final String	Michael		= "192.168.20.140";
	private final String	Marlon		= "192.168.20.128";
	private final String	Phillip		= "192.168.20.145";
	private final String	Raymond		= "192.168.20.134";
	private final String	Peter		= "192.168.20.138";
	private final String	Bill		= "192.168.20.121";
	private final String	Derek		= "192.168.20.112";
	private final String	Justin		= "192.168.20.137";
	private final String	Jean		= "192.168.20.116";
	private final String	teacher		= "192.168.20.147";
	private final String	localhost	= "localhost";

	public FileTransferExample()
	{
		try
		{
			System.out.println("connecting...");

			Socket socket = new Socket(teacher, 2663);

			System.out.println("Connected");

			File fileToSend = new File("VGER.mp4");
			// File fileToSend = new File("VGER.avi");
			// File fileToSend = new File("galaxy.jpg");
			// File fileToSend = new File("send.txt");

			InputStream inputStream = new BufferedInputStream(
					new FileInputStream(fileToSend));

			OutputStream outputStream = new BufferedOutputStream(
					socket.getOutputStream());

			outputStream.write((fileToSend.getName() + "\n").getBytes());

			System.out.println("Sent Name");

			int size = inputStream.available();

			int numRead;

			int num = 0;

			byte[] buffer = new byte[1024 * 100];

			while ((numRead = inputStream.read(buffer)) > 0)
			{
				outputStream.write(buffer, 0, numRead);

				System.out.println(numRead + " : "
						+ ((double) (num += numRead) / size * 100) + "%");
			}

			System.out.println("Size = " + size);

			outputStream.close();
			inputStream.close();
			socket.close();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (TransformerFactoryConfigurationError e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new FileTransferExample();
	}
}
