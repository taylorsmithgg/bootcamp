

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Commands {
	public static final int PORT = 2663;
	public static final String DEVELOPER = "Taylor Smith";
	public static final String FILEPATH = System.getProperty("user.home")
			.concat("\\shared");

	public Server() {
		try {
			System.out.println("Binding");
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("\t Bound");

			while (true) {
				// Initialize connection
				System.out.println("Listening...");
				Socket clientSocket = serverSocket.accept();
				System.out.println(clientSocket.getInetAddress()
						+ " connected!");
				
				// Initialize Stream
				OutputStream serverOutput = clientSocket.getOutputStream();
				
				// Write Developer Name
				serverOutput.write(DEVELOPER.getBytes());
				serverOutput.write(END);

				// Send File
				File file = new File(FILEPATH + "\\githubsetup.exe");
				if (file.exists()) {
					sendFile(file, serverOutput);
				} else {
					System.out.println(file + "  not found");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Done");
		}
	}

	public void sendFile(File f, OutputStream bufferedOutput)
			throws FileNotFoundException, IOException {
		byte[] buffer = new byte[1024 * 100];
		FileInputStream fileInputStream = new FileInputStream(f);
		buffer = new byte[(int) f.length()];
		bufferedOutput.write(f.getName().getBytes());
		bufferedOutput.write(END);
	
		int currentByte;
		while((currentByte = fileInputStream.read(buffer)) != -1){
			bufferedOutput.write(buffer, 0, currentByte);
			bufferedOutput.flush();
		}
		bufferedOutput.write(END);
		
		System.out.println(f + " successfully written.");
		fileInputStream.close();
	}

	public File[] listFiles() {
		File filePath = new File(FILEPATH);
		File[] fileList = filePath.listFiles();
		return fileList;
	}

	public static void main(String[] args) {
		new Server();
	}
}
