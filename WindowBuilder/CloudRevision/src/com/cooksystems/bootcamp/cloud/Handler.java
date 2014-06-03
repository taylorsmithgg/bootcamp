package com.cooksystems.bootcamp.cloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Handler implements Commands {
	public String localhost = "localhost";
	public String Tyler = "192.168.20.109";
	public static final String FILEPATH = System.getProperty("user.home")
			.concat("\\shared");
	byte[] buffer = new byte[1024];

	public void sendFile(File f, OutputStream bufferedOutput)
			throws FileNotFoundException, IOException {
		if (f.exists()) {
			bufferedOutput.write(FILE_EXISTS);
			byte[] buffer = new byte[1024 * 100];
			FileInputStream fileInputStream = new FileInputStream(f);
			buffer = new byte[(int) f.length()];
			bufferedOutput.write(f.getName().getBytes());
			bufferedOutput.write(END);

			int currentByte;

			while ((currentByte = fileInputStream.read(buffer)) > 0) {
				bufferedOutput.write(buffer, 0, currentByte);
				bufferedOutput.flush();
			}

			bufferedOutput.write(END);

			System.out.println(f + " successfully written.");
			fileInputStream.close();
		}
	}

	public void readFile(InputStream bufferedInput) {
		try {
			buffer = new byte[1024 * 100];
			// Read File Name
			int fileNameByte;
			String fileName = "";
			while ((fileNameByte = bufferedInput.read()) != END) {
				fileName += (char) fileNameByte;
			}
			System.out.println(fileName);

			FileOutputStream fileOutput = new FileOutputStream(fileName);

			int currentByte;
			int size = 0;
			int fileSize = bufferedInput.available();
			while ((currentByte = bufferedInput.read(buffer)) != END) {
				fileOutput.write(buffer, 0, currentByte);
				System.out.println((size += currentByte) / fileSize + "%");
				fileOutput.flush();
			}

			fileOutput.close();
		} catch (IOException e) {
			System.out.println("Failed to read file");
			e.printStackTrace();
		}
	}

	public File[] listFiles() {
		File filePath = new File(FILEPATH);
		File[] fileList = filePath.listFiles();
		return fileList;
	}

	public String[] listServers() {
		String[] result = { localhost, Tyler };
		return result;
	}
}
