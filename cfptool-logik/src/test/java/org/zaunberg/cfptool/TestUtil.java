package org.zaunberg.cfptool;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class TestUtil {

	public String loadFileContent(String filePathAndName) throws IOException {
		File file = new File(Thread.currentThread().getContextClassLoader().getResource(filePathAndName).getPath());
		
		return fileContentToString(file);
	}
	
	public String fileContentToString(File file) throws IOException {
		
		char[] chr = new char[4096];
		final StringBuffer sb = new StringBuffer();
		final FileReader reader = new FileReader(file);
		
		try {
			int len;
			while ((len = reader.read(chr)) > 0) {
				sb.append(chr, 0, len);
			}
		} finally {
			reader.close();
		}
		
		return sb.toString();
	}
}
