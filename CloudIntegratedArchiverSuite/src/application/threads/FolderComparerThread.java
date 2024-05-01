package application.threads;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.interfaces.ComputationalTask;
import application.models.ComparedFolderResults;
import application.models.DuplicatePair;
import application.util.FileOperations;

public class FolderComparerThread implements ComputationalTask<ComparedFolderResults> {

	private static final Logger logger = LogManager.getLogger(FolderComparerThread.class.getName());
	private static final String FILE_INFO_FORMAT = "%s:%d:%d";

	private final String folder1Path;
	private final String folder2Path;

	public FolderComparerThread(String folder1Path, String folder2Path) {
		this.folder1Path = folder1Path;
		this.folder2Path = folder2Path;
	}

	@Override
	public ComparedFolderResults performTask() {
		// Get list of files in each folder
		List<File> folder1Files = FileOperations.listAllFiles(folder1Path);
		List<File> folder2Files = FileOperations.listAllFiles(folder2Path);

		// Map to store file sizes and modified dates for folders
		Map<String, String> folder1InfoMap = getInfoMap(folder1Files);
		Map<String, String> folder2InfoMap = getInfoMap(folder2Files);

		// Find common files
		List<DuplicatePair> commonFiles = getCommonFiles(folder2Files, folder1InfoMap);

		// Identify unique files in folders
		List<String> uniqueFilesFolder1 = getUniqueFiles(folder1Files, folder2InfoMap);
		List<String> uniqueFilesFolder2 = getUniqueFiles(folder2Files, folder1InfoMap);

		logger.info("Folder comparison completed");
		return new ComparedFolderResults(uniqueFilesFolder1, uniqueFilesFolder2, commonFiles);
	}

	private Map<String, String> getInfoMap(List<File> filesList) {
		Map<String, String> infoMap = new HashMap<>();
		for (File file : filesList) {
			String fileInfo = String.format(FILE_INFO_FORMAT, file.getName(), file.length(), file.lastModified());
			infoMap.put(FileOperations.getRelativePath(file, folder1Path), fileInfo);
		}
		return infoMap;
	}

	private List<String> getUniqueFiles(List<File> filesList, Map<String, String> folderInfoMap) {
		List<String> uniqueFiles = new ArrayList<>();
		for (File file : filesList) {
			String relativePath = FileOperations.getRelativePath(file, folder2Path);
			if (!folderInfoMap.containsKey(relativePath)) {
				uniqueFiles.add(file.getAbsolutePath());
			}
		}
		return uniqueFiles;
	}

	private List<DuplicatePair> getCommonFiles(List<File> filesList, Map<String, String> folderInfoMap) {
		List<DuplicatePair> commonFiles = new ArrayList<>();
		for (File file : filesList) {
			String fileInfo = String.format(FILE_INFO_FORMAT, file.getName(), file.length(), file.lastModified());
			String relativePath = FileOperations.getRelativePath(file, folder2Path);
			if (folderInfoMap.containsKey(relativePath)) {
				if (folderInfoMap.get(relativePath).equals(fileInfo)) {
					commonFiles.add(new DuplicatePair(new File(folder1Path, relativePath).getAbsolutePath(), file.getAbsolutePath()));
				}
			}
		}
		return commonFiles;
	}

}
