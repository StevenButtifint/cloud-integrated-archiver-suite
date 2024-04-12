package application.controllers;

import application.threads.FolderComparerThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ComparerController {
	
	private FolderComparerThread folderComparerThread;
	
	@FXML
	private Button compareButton;
	
	
    public void initialize() {
    	
    	compareButton.setOnAction(event -> compareFolders());
        System.out.println("Compaerer Controller");
    }
    
    
    //when calling FolderComparerThread have wait code that once its done it uses get methods in the thread class to get the values assuming this doesnt lock up the main UI when waiting for the thread to finihs so dont have to pass the consumer methods in as wasnt that bad to do

    //if this works then change the other threads to follow this if better practice

    private void compareFolders() {
    	compareButton.setDisable(true);
    	folderComparerThread = new FolderComparerThread("A", "B");
    	folderComparerThread.start();
    	
    	//wait for thread to complete or OOP correct way to then use get methods for the results of the thread???
    	// dont lock up UI main 
    	compareButton.setDisable(false);
    }
    
}
