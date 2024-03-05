package application.util;

import java.io.File;

public class FileOperations {
	
    public static boolean validDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() && directory.isDirectory();
    }

}
