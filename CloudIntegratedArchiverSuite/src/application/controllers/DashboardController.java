package application.controllers;

import java.io.IOException;
import java.util.List;

import application.config.Config;
import application.models.Dashboard;
import application.models.Link;
import application.threads.AvailableLinksThread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class DashboardController {


	@FXML
	private VBox dashboardLinkList;// = null;

	AvailableLinksThread availableLinksThread;
	Dashboard dashboard = new Dashboard();

	private Config config;

	public void initialize() {

		// load application properties
		config = new Config("app.properties");

		availableLinksThread = new AvailableLinksThread(this::updateLinkItemsUI);
		availableLinksThread.start();
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
					controller.setSyncedLabel(link.getLastSynced().toString());

					if (link.getAccessible()) {
						controller.setStateAccessible();
					} else {
						controller.setStateInaccessible();
					}

					dashboardLinkList.getChildren().add(newView);

				}
				System.out.println(dashboardLinkList.getChildren().size());

			} catch (IOException e) {
				e.printStackTrace();
			}
        });
    }

}
