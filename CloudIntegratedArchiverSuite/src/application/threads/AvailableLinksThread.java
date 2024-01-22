package application.threads;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AvailableLinksThread extends Task<Void> {
	private static final String LINK_ITEM_PATH = "../views/link_item.fxml";
	
	@Override
	protected Void call() throws Exception {
	}
}
