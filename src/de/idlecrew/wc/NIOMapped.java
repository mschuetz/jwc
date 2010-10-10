package de.idlecrew.wc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.TimeUnit;

import de.idlecrew.util.Profile;

public class NIOMapped {

	public static void wc(String fn) throws IOException {
		long fileSize = new File(fn).length();
		FileInputStream is = new FileInputStream(fn);
		MappedByteBuffer buf = is.getChannel().map(MapMode.READ_ONLY, 0, fileSize);
		boolean inword = false;
		int lines = 0;
		int words = 0;

		int i = 0;
		while (i < fileSize) {
			switch (buf.get()) {
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

		System.out.printf("%d %d %d\n", lines, words, fileSize);

	}

	public static void main(String[] args) throws Exception {
		Profile.report("wc", TimeUnit.MILLISECONDS, 10, new Runnable() {
			@Override
			public void run() {
				try {
					wc("/img");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
