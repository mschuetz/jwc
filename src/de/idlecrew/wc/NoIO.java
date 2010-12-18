package de.idlecrew.wc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import de.idlecrew.util.Profile;

public class NoIO {
	public static void wc(byte[] buf) throws IOException {
		boolean inword = false;
		int lines = 0;
		int words = 0;

		int i = 0;
		while (i < buf.length) {
			switch (buf[i]) {
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

		if (inword)
			words++;

//		System.out.printf("%d %d %d\n", lines, words, buf.length);

	}

	public static void main(String[] args) throws Exception {
		File f = new File("/dev/urandom");
		FileInputStream is = new FileInputStream(f);
		final byte[] buf = new byte[512];
		if (is.read(buf) != 512)
			throw new RuntimeException("didn't get 512 bytes...");
		is.close();

		Profile.report("wc", TimeUnit.MICROSECONDS, Integer.parseInt(args[0]), new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 1; i < 10; i++)
						wc(buf);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
