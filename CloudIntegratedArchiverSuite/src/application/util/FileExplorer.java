package application.util;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FileExplorer {

	public static String selectFolder() {

		// Create a new stage for the directory chooser
		Stage directoryChooserStage = new Stage();

		// Create the directory chooser
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Folder");

		// Show the directory chooser dialog
		File selectedDirectory = directoryChooser.showDialog(directoryChooserStage);

		if (selectedDirectory != null) {
			return selectedDirectory.getAbsolutePath();
		} else {
			return null;

		}
	}

}
