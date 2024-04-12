package application.threads;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.util.Pair;

public class FolderComparerThread extends Thread {
	
	private static final Logger logger = LogManager.getLogger(FolderComparerThread.class.getName());

	private final String folder1Path;
	private final String folder2Path;

	private List<String> uniqueFilesInFolder1;
	private List<String> uniqueFilesInFolder2;
	private List<Pair<String, String>> commonFiles;

	public FolderComparerThread(String folder1Path, String folder2Path) {
		this.folder1Path = folder1Path;
		this.folder2Path = folder2Path;
	}
}
