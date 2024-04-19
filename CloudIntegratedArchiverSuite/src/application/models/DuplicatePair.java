package application.models;

public class DuplicatePair {

	private String path1;
	private String path2;

	public DuplicatePair(String path1, String path2) {
		this.path1 = path1;
		this.path2 = path2;
	}

	public String getPath1() {
		return path1;
	}

	public String getPath2() {
		return path2;
	}

}
