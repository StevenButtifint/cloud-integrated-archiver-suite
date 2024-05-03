package application.controllers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.interfaces.ComputationalTask;
import application.interfaces.TaskCompleteListener;
import application.models.ComparedFolderResults;
import application.models.DuplicatePair;
import application.threads.FolderComparerThread;
import application.threads.WorkerThread;
import application.util.FileExplorer;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ComparerController implements TaskCompleteListener<ComparedFolderResults> {

	private static final Logger logger = LogManager.getLogger(ComparerController.class.getName());

	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	private ComputationalTask<ComparedFolderResults> folderComparerThread;

	private WorkerThread<ComparedFolderResults> workerThread;

	private Future<ComparedFolderResults> future;

	@FXML
	private Button compareButton;

	@FXML
	private Button select1Button;

	@FXML
	private Button select2Button;

	@FXML
	private TextField path1Field;

	@FXML
	private TextField path2Field;

	@FXML
	private Label noticeLabel;

	@FXML
	private ListView<String> unique1List;

	@FXML
	private ListView<String> unique2List;

	@FXML
	private TableView<DuplicatePair> duplicateTable;

	@FXML
	private TableColumn<DuplicatePair, String> column1;

	@FXML
	private TableColumn<DuplicatePair, String> column2;

	public void initialize() {
		select1Button.setOnAction(event -> setLocalLocation(path1Field));
		select2Button.setOnAction(event -> setLocalLocation(path2Field));
		compareButton.setOnAction(event -> startTask());
		setupDuplicateTable();
	}

	private void setupDuplicateTable() {
		column1.setCellValueFactory(param -> {
			DuplicatePair pair = (DuplicatePair) param.getValue();
			return new ReadOnlyStringWrapper(pair.getPath1());
		});

		column2.setCellValueFactory(param -> {
			DuplicatePair pair = (DuplicatePair) param.getValue();
			return new ReadOnlyStringWrapper(pair.getPath2());
		});
	}

	public void startTask() {
		compareButton.setDisable(true);
		folderComparerThread = new FolderComparerThread(path1Field.getText(), path2Field.getText());
		workerThread = new WorkerThread<>(folderComparerThread, this);
		future = executorService.submit(workerThread);
		// Optionally, store the future for potential cancellation or further processing
	}

	public void stopComparingFolders() {
		// Attempt to cancel the future
		if (future != null) {
			future.cancel(true);
		}

		// Stop the worker thread
		if (workerThread != null) {
			workerThread.stopExecution();
		}
	}

	@Override
	public void onTaskComplete(ComparedFolderResults result) {
		Platform.runLater(() -> {
			updateUniqueFiles(unique1List, result.getUniqueFilesInFolder1());
			updateUniqueFiles(unique2List, result.getUniqueFilesInFolder2());
			updateDuplicateTable(result.getCommonFiles());
			compareButton.setDisable(false);
			clearNotice();
		});
	}

	@Override
	public void onTaskFailed(Exception e) {
		logger.error("Comparer thread failed.", e.getMessage());
		Platform.runLater(() -> {
			setNoticeMessage("Comparison failed " + e.getMessage());
			compareButton.setDisable(false);
		});
	}

	public void shutdown() {
		stopComparingFolders();
		executorService.shutdown();
	}

	private void setLocalLocation(TextField outputField) {
		String location = FileExplorer.selectFolder();
		if (location != null) {
			outputField.setText(location);
		}
	}

	private void updateUniqueFiles(ListView<String> listview, List<String> uniqueItems) {
		listview.getItems().clear();
		ObservableList<String> observableList = FXCollections.observableArrayList(uniqueItems);
		listview.setItems(observableList);
	}

	private void updateDuplicateTable(List<DuplicatePair> duplicates) {
		duplicateTable.getItems().clear();
		ObservableList<DuplicatePair> results = FXCollections.observableArrayList(duplicates);
		duplicateTable.setItems(results);
	}

	private void setNoticeMessage(String message) {
		noticeLabel.setText(message);
	}

	private void clearNotice() {
		noticeLabel.setText("");
	}

}
