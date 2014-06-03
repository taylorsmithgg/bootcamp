
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Commands {
	public static final int PORT = 2663;
	public static final String FILEPATH = System.getProperty("user.home")
			.concat("\\shared");

	public String localhost = "localhost";

	byte[] buffer;
	BufferedOutputStream bufferedOutput;

	public Client() {
		try {
			Socket clientSocket = new Socket(localhost, PORT);

			// Initialize Streams
			InputStream clientInput = clientSocket.getInputStream();
			OutputStream clientOutput = clientSocket.getOutputStream();
			BufferedInputStream bufferedInput = new BufferedInputStream(
					clientInput);
			bufferedOutput = new BufferedOutputStream(clientOutput);

			// Print Developer Name
			int character;
			while ((character = clientInput.read()) != END) {
				System.out.print((char) character);
			}
			System.out.println();

			// Read Incoming File
			readFile(bufferedInput);

			System.out.println("Stream closed");

			clientSocket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendFile(File f, OutputStream bufferedOutput){
	System.out.println("Writing file");
	try{
	if (f.exists()) {
		byte[] buffer = new byte[1024 * 100];
		FileInputStream fileInputStream = new FileInputStream(f);
		buffer = new byte[(int) f.length()];
		bufferedOutput.write(f.getName().getBytes());
		bufferedOutput.write(Commands.END);

		int currentByte;

		while ((currentByte = fileInputStream.read(buffer)) > 0) {
			bufferedOutput.write(buffer, 0, currentByte);
			System.out.println("currentByte");
			bufferedOutput.flush();
		}

		System.out.println(f + " successfully written.");
		bufferedOutput.close();
		fileInputStream.close();
	}else{
	System.out.println("File does not exist");
	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
	}
	}

	public void readFile(InputStream bufferedInput) {
		try {
			buffer = new byte[(int) 2.14958e6];
			// Read File Name
			int fileNameByte;
			String fileName = "";
			while ((fileNameByte = bufferedInput.read()) != END) {
				fileName += (char) fileNameByte;
			}
			System.out.println(fileName);

			FileOutputStream fileOutput = new FileOutputStream(fileName);

			int currentByte;
			while ((currentByte = bufferedInput.read(buffer)) != END) {
				fileOutput.write(buffer, 0, currentByte);
				System.out.println(currentByte);
				fileOutput.flush();
			}

			fileOutput.close();
		} catch (IOException e) {
			System.out.println("Failed to read file");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Client();
	}

	public File[] listFiles() {
		File filePath = new File(FILEPATH);
		File[] fileList = filePath.listFiles();
		return fileList;
	}
}
