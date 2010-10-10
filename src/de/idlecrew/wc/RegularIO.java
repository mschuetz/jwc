package de.idlecrew.wc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import de.idlecrew.util.Profile;

public class RegularIO {

	public static void wc(String fn, int bufsize) throws IOException {
		InputStream is = new FileInputStream(fn);

		boolean inword = false;
		int lines = 0;
		int words = 0;

		byte[] buf = new byte[bufsize];
		int len;
		while ((len = is.read(buf)) != -1) {
			int i = 0;
			while (i < len) {
				byte c = buf[i];
				switch (c) {
				case '\n':
					lines++;
				case ' ':
				case '\t':
				case '\f':
				case '\r':
					if (inword) {
						inword = false;
						words++;
					}
					break;
				default:
					inword = true;

				}
				i++;
			}
		}

		if (inword)
			words++;

		System.out.printf("%d %d %d\n", lines, words, new File(fn).length());

	}
	
	public static void main(String[] args) throws Exception {
		Profile.report("wc", TimeUnit.MILLISECONDS, 10, new Runnable() {
			@Override
			public void run() {
				try {
					wc("/img", 8192);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
