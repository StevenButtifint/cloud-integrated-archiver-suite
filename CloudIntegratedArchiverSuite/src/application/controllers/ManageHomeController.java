package application.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.interfaces.IDatabaseService;
import application.models.Link;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ManageHomeController {

	private static final Logger logger = LogManager.getLogger(ManageHomeController.class.getName());

	private IDatabaseService databaseService;

	private Config appConfig;

	private ManageController manageController;

	@FXML
	private Button newLink;

	@FXML
	private VBox manageLinkList;

	public ManageHomeController(Config appConfig, IDatabaseService databaseService) {
		this.appConfig = appConfig;
		this.databaseService = databaseService;
	}

	public void initialize() {
		newLink.setOnAction(event -> manageController.goToCreateLink());
		refreshLinksList();
	}

	public void setManageController(ManageController manageController) {
		this.manageController = manageController;
	}

	public void refreshLinksList() {
		manageLinkList.getChildren().clear();
		List<Link> allLinks = databaseService.getAllLinks();

		for (Link link : allLinks) {
			try {
				Node newView = createLinkNode(link);
				manageLinkList.getChildren().add(newView);
			} catch (IOException e) {
				logger.error("Failed to load link item view", e);
			}
		}
	}

	private Node createLinkNode(Link link) throws IOException {
		LinkManageController controller = new LinkManageController(manageController, link);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(appConfig.getProperty("view.path.linkmanage")));
		loader.setController(controller);
		Node view = loader.load();
		return view;
	}
}
