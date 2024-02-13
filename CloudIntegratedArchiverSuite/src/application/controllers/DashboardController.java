package application.controllers;

import java.io.IOException;
import java.util.List;

import application.models.Dashboard;
import application.models.Link;
import application.threads.AvailableLinksThread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;


public class DashboardController extends BaseController {
	
	private static final String LINK_ITEM_PATH = "../views/link_item.fxml";

	@FXML
	private VBox dashboardLinkList;// = null;
	
	AvailableLinksThread availableLinksThread;
	Dashboard dashboard = new Dashboard();
	
    public void initialize() {
    	availableLinksThread = new AvailableLinksThread(this::updateLinkItemUI);
    	availableLinksThread.start();
    }
    

}
