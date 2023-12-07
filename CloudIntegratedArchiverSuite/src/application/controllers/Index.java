package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Index extends BaseController{
	
	private static final String LINK_ITEM_PATH = "../views/link_item.fxml";
	
	@FXML
	private VBox indexLinkList = null;
	private URL url = getClass().getResource(LINK_ITEM_PATH);
	@Override   
	public void initialize() {
	}


}
