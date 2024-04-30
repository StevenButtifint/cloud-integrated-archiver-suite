package application.models;

import java.util.List;


public class ComparedFolderResults {

	private List<String> uniqueFilesFolder1;
	private List<String> uniqueFilesFolder2;
	private List<DuplicatePair> commonFiles;

	public ComparedFolderResults(List<String> uniqueFilesFolder1, List<String> uniqueFilesFolder2, List<DuplicatePair> commonFiles) {
		this.uniqueFilesFolder1 = uniqueFilesFolder1;
		this.uniqueFilesFolder2 = uniqueFilesFolder2;
		this.commonFiles = commonFiles;
	}

	public List<String> getUniqueFilesInFolder1() {
		return uniqueFilesFolder1;
	}

	public List<String> getUniqueFilesInFolder2() {
		return uniqueFilesFolder2;
	}

	public List<DuplicatePair> getCommonFiles() {
		return commonFiles;
	}

}
