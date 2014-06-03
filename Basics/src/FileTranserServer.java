import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTranserServer {
	public FileTranserServer() {
		OutputStream outputStream = null;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(2663);

			System.out.println("Listening...");
			Socket socket = serverSocket.accept();
			System.out.println("Connected");

			InputStream inputStream = new BufferedInputStream(
					socket.getInputStream());

			int character;
			String fileName = "";

			byte[] buffer = new byte[1024 * 100];
			int numRead;

			while ((character = inputStream.read()) != '\n')
				fileName += (char) character;

			System.out.println("Name: " + fileName);

			File file = new File(fileName);

			outputStream = new BufferedOutputStream(new FileOutputStream(file));

			int size = 0;

			while ((numRead = inputStream.read(buffer)) > 0) {
				size += numRead;
				outputStream.write(buffer, 0, numRead);
			}

			System.out.println("Size = " + size);

			outputStream.close();
			inputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e1) {
					System.out.println("Oh well...");
				}
		}

		System.out.println("Done!");
	}

	public static void main(String[] args) {
		new FileTranserServer();
	}

	/**
	 * Returns a new, bigger array with all of the same contents as the old
	 * array.
	 * 
	 * @throws IllegalArgumentException
	 *             if the new size is smaller than the old size
	 * @param old
	 * @param newSize
	 * @return the new array
	 */
	public static byte[] increaseSize(byte[] old, int newSize) {
		if (newSize < old.length)
			throw new IllegalArgumentException(
					"Decreasing the size of the array is not a good idea!");

		byte[] newArray = new byte[newSize];

		for (int i = 0; i < old.length; i++)
			newArray[i] = old[i];

		return newArray;
	}
}
