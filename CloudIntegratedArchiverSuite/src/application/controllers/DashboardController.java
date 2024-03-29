package application.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.models.Link;
import application.threads.AvailableLinksThread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class DashboardController {

	private static final Logger logger = LogManager.getLogger(DashboardController.class.getName());

	@FXML
	private VBox dashboardLinkList;

	private AvailableLinksThread availableLinksThread;

	private Config config;

	public void initialize() {

		// load application properties
		config = new Config("app.properties");

		availableLinksThread = new AvailableLinksThread(this::updateLinkItemsUI);
		availableLinksThread.start();
		logger.info("Running Dashboard Service");
	}

	private void updateLinkItemsUI(List<Link> accessibleLinks) {
		Platform.runLater(() -> {

			dashboardLinkList.getChildren().clear();

			try {

				for (Link link : accessibleLinks) {

					LinkItemController linkItemController = new LinkItemController(link);

					FXMLLoader loader = new FXMLLoader(linkItemController.getClass().getResource(config.getProperty("view.path.linkitem")));
					loader.setController(linkItemController);

					Node newView = loader.load();
					LinkItemController controller = loader.getController();
					controller.setLinkName(link.getName());
					controller.setLinkDescription(link.getDescription());
					controller.setSyncedLabel(link.sinceSyncedString());

					if (link.getAccessible()) {
						controller.setStateAccessible();
					} else {
						controller.setStateInaccessible();
					}

					dashboardLinkList.getChildren().add(newView);

				}

			} catch (IOException e) {
				logger.error("Failed to get links for database. " + e.getMessage(), e);
			}
		});
	}
}
