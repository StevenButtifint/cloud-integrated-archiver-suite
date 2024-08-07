package application.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.interfaces.IDashboardService;
import application.interfaces.IMonitorService;
import application.models.Link;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class DashboardController {

	private static final Logger logger = LogManager.getLogger(DashboardController.class.getName());

	private IDashboardService dashboardService;
	
	private IMonitorService monitorService;

	private List<Pair<LinkItemController, Node>> linkControllerNodePairs = new ArrayList<>();

	@FXML
	private VBox dashboardLinkList;

	private Config appConfig;

	private Config dbConfig;

	public DashboardController(IDashboardService dashboardService, IMonitorService monitorService, Config appConfig, Config dbConfig) {
		this.dashboardService = dashboardService;
		this.monitorService = monitorService;
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

	public void orderLinksListAvailability() {
		List<Node> available = new ArrayList<>();
		List<Node> unavailable = new ArrayList<>();

		for (Pair<LinkItemController, Node> controllerNodePair : linkControllerNodePairs) {
			if (controllerNodePair.getKey().isAccessible()) {
				available.add(controllerNodePair.getValue());
				controllerNodePair.getKey().setStateAccessible();
			} else {
				unavailable.add(controllerNodePair.getValue());
				controllerNodePair.getKey().setStateInaccessible();
			}
		}

		List<Node> reorderedList = new ArrayList<>(available);
		reorderedList.addAll(unavailable);
		Platform.runLater(() -> {
			dashboardLinkList.getChildren().setAll(reorderedList);
		});
	}

	private void populateLinksList(List<Link> links) {
		try {
			linkControllerNodePairs.clear();
			dashboardLinkList.getChildren().clear();

			for (Link link : links) {
				LinkItemController linkItemController = new LinkItemController(monitorService, link, appConfig, dbConfig);
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
