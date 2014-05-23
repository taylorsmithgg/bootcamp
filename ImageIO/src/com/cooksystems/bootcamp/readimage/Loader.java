package com.cooksystems.bootcamp.readimage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.plaf.basic.BasicOptionPaneUI;

public class Loader {
	public static final String SOURCE_DIR = ".";
	public static final String DEST_DIR = "C:/Users/Bondstone70/Documents/workspace-sts-3.2.0.RELEASE/ImageIO/new";

	public Loader() {
	}

	public void copyFile(String fileName) {
		long startTime = System.currentTimeMillis();

		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		InputStream fileInputStream = null;
		OutputStream fileOutputStream = null;
		byte[] chunk = new byte[1024 * 8];
		File sourceFile = new File(SOURCE_DIR, fileName);
		File destFile = new File(DEST_DIR, fileName);

		if (sourceFile.isFile()) {
			try {
				int byteCount = -1;
				fileInputStream = new FileInputStream(sourceFile);
				fileOutputStream = new FileOutputStream(destFile);
				bufferedInputStream = new BufferedInputStream(fileInputStream);
				bufferedOutputStream = new BufferedOutputStream(
						fileOutputStream);

				while ((byteCount = bufferedInputStream.read(chunk)) != -1) {
					bufferedOutputStream.write(chunk, 0, byteCount);
					bufferedOutputStream.flush();
				}

				while ((byteCount = fileInputStream.read(chunk)) != -1) {
					fileOutputStream.write(chunk, 0, byteCount);
				}

				System.out.println("Successfully copied file to: "
						+ destFile.getAbsolutePath());
			} catch (IOException e) {
				System.out.println("Specified file not found: "
						+ sourceFile.getAbsolutePath());
				e.printStackTrace();
			} finally {
				closeStream(fileInputStream);
				closeStream(fileOutputStream);
				closeStream(bufferedInputStream);
				closeStream(bufferedOutputStream);
			}
		} else {
			System.out.println("Invalid source file specified: "
					+ sourceFile.getAbsolutePath());
		}

		long endTime = System.currentTimeMillis();

		System.out.println(endTime - startTime + "ms");
	}

	private void closeStream(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
				System.out.println("Resource closed: "
						+ closeable.getClass().getName());
				System.out.println("Done");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end closeStream

	public static void main(String[] args) {
		Loader loader = new Loader();
		loader.copyFile("testImage.jpg");
	}

}// end class
