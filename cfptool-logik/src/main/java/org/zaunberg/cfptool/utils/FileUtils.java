package org.zaunberg.cfptool.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	public static void writeToFile(String content, String fileName) {
		try {
			FileWriter writer = new FileWriter(new File(fileName), true);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean fileExists(String fileName) {
		return new File(fileName).exists();
	}

}
