package application.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

	public static boolean validDirectory(String directoryPath) {
		File directory = new File(directoryPath);
		return directory.exists() && directory.isDirectory();
	}

	public static List<File> listAllFiles(String folderPath) {
		List<File> fileList = new ArrayList<>();
		File folder = new File(folderPath);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					fileList.addAll(listAllFiles(file.getPath()));
				} else {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}

	public static String getRelativePath(File file, String baseFolder) {
		String absolutePath = file.getAbsolutePath();
		return absolutePath.substring(baseFolder.length() + 1);
	}

}
