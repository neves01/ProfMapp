package br.com.processador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TesteDiretorios {
	public static void main(String[] args) {
		List<String> lines = new ArrayList<String>();
		String rootPath = "apps/pslab-android-development/app/src/main";
		String path = rootPath + File.separator + "res" + File.separator + "layout";
		String resPath = rootPath + File.separator + "res";
		int startCol = 0;
		int endCol = 0;
		int lineNum = 0;

		File dirRes = new File(resPath);
		File[] directoryListingRes = dirRes.listFiles();
		ArrayList<String> dirNames = new ArrayList<String>();

		for (File f : directoryListingRes) {
			if (f.isDirectory() && f.getName().contains("layout")) {
				dirNames.add(f.getAbsolutePath());
				System.out.println("DIR: " + f.getAbsolutePath());
			}
		}

		System.out.println("-------------------------------------------------------");

		for (String filePath : dirNames) {
			System.out.println("filePath: " + filePath);
			File dir = new File(filePath);
			File[] directoryListing = dir.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					String xmlPath = filePath + File.separator + child.getName();
					// lines = FileHelper.readLines(xmlPath);
					System.out.println("XML_InputType: checando arquivo: " + xmlPath);
					String line = "";
					for (int i = 0; i < lines.size(); i++) {
						line = lines.get(i);
						if (line.contains("android:inputType=")) {
							startCol = line.indexOf("\"") + 1;
							endCol = line.indexOf("\"", startCol);
							lineNum = i;
							// locations.add(buildLocation(xmlPath, startCol, endCol, lineNum));
						}
					}
				}
			}
		}
		// return locations;
	}

}
