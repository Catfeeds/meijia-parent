package com.meijia.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {

	public static byte[] getBytes(String filePath) throws IOException {
		FileInputStream in = new FileInputStream(filePath);

		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

		System.out.println("bytes available:" + in.available());

		byte[] temp = new byte[1024];

		int size = 0;

		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}

		in.close();

		byte[] bytes = out.toByteArray();
		System.out.println("bytes size got is:" + bytes.length);

		return bytes;
	}

	public static void main(String[] args) throws Exception {

	}
}