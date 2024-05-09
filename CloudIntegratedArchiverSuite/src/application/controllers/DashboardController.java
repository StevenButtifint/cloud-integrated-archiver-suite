package application.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.models.Link;
import application.services.DashboardService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class DashboardController {

	private static final Logger logger = LogManager.getLogger(DashboardController.class.getName());

	private DashboardService dashboardService;

	private List<Pair<LinkItemController, Node>> linkControllerNodePairs = new ArrayList<>();

	@FXML
	private VBox dashboardLinkList;

	private Config appConfig;

	private Config dbConfig;

	public DashboardController(DashboardService dashboardService, Config appConfig, Config dbConfig) {
		this.dashboardService = dashboardService;
		this.appConfig = appConfig;
		this.dbConfig = dbConfig;
	}

	public void initialize() {
		reloadDashboardLinks();
	}

	public void reloadDashboardLinks() {
		dashboardService.getLinksAsync().thenAccept(links -> {
			// This code runs asynchronously once the links are fetched
			Platform.runLater(() -> {
				populateLinksList(links);
				orderLinksListAvailability();
			});
		}).exceptionally(e -> {
			logger.error("failed to get link list." + e.getMessage());
			Platform.runLater(() -> {
				showLinkListError(e.getMessage());
			});
			return null;
		});
	}

	}

	private void populateLinkList() {
		try {
			linkControllerNodePairs.clear();
			dashboardLinkList.getChildren().clear();

			for (Link link : links) {
				LinkItemController linkItemController = new LinkItemController(link, appConfig, dbConfig);
				FXMLLoader loader = new FXMLLoader(linkItemController.getClass().getResource(appConfig.getProperty("view.path.linkitem")));
				loader.setController(linkItemController);
				Node newView = loader.load();
				LinkItemController controller = loader.getController();
				dashboardLinkList.getChildren().add(newView);
				linkControllerNodePairs.add(new Pair<>(controller, newView));
			}
		} catch (IOException e) {
			logger.error("Failed to get links for database. " + e.getMessage(), e);
		}
	}

			}
	}
}
