package application.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.config.Config;
import application.database.DatabaseConnection;
import application.services.DashboardService;
import application.services.MonitorService;
import application.services.SchedulerService;
import application.util.OperationManager;

public class ControllerFactory {
	private Config appConfig;
	private Config dbConfig;
	private DashboardController dashboardController;
	private ExecutorService executorService;
	private DashboardService dashboardService;
	private SchedulerService schedulerService;
	private MonitorService monitorService;
	private IndexController indexController;
	private ManageHomeController manageHomeController;
	private ManageController manageController;
	private ComparerController comparerController;
	private CreateLinkController createLinkController;
	private ViewLinkController viewLinkController;
	private EditLinkController editLinkController;
	private DeleteLinkController deleteLinkController;
	private MonitorController monitorController;
	private OperationManager operationManager;

	public ControllerFactory(Config appConfig, Config dbConfig) {
		this.appConfig = appConfig;
		this.dbConfig = dbConfig;
	}

	public void stopServices() {
		if (schedulerService != null) {
			schedulerService.shutdown();
		}
		if (dashboardService != null) {
			dashboardService.shutdown();
		}
		if (comparerController != null) {
			comparerController.stopComparingFolders();
		}
	}
	
	private ExecutorService getExecutorService() {
		if (executorService == null) {
			 executorService = Executors.newCachedThreadPool();
		}
		return executorService;
	}

	private void initializeDashboardService() {
		if (dashboardService == null) {
			DatabaseConnection databaseConnection = new DatabaseConnection(dbConfig);
			databaseConnection.connectToDatabase();
			executorService = getExecutorService();
			dashboardService = new DashboardService(databaseConnection, executorService);
		}
	}

	private void initializeSchedulerService() {
		if (schedulerService == null) {
			schedulerService = new SchedulerService();
			schedulerService.scheduleTask(dashboardController::orderLinksListAvailability, 15);
		}
	}

	public synchronized DashboardController getDashboardController() {
		if (dashboardController == null) {
			initializeDashboardService();
			dashboardController = new DashboardController(dashboardService, appConfig, dbConfig);
			initializeSchedulerService();
		}
		return dashboardController;
	}

	public ManageHomeController getManageHomeController() {
		if (manageHomeController == null) {
			DatabaseConnection databaseConnection = new DatabaseConnection(dbConfig);
			manageHomeController = new ManageHomeController(appConfig, databaseConnection);
		}
		return manageHomeController;
	}

	public CreateLinkController getCreateLinkController() {
		if (createLinkController == null) {
			createLinkController = new CreateLinkController(dbConfig);
		}
		return createLinkController;
	}

	public ViewLinkController getViewLinkController() {
		if (viewLinkController == null) {
			viewLinkController = new ViewLinkController();
		}
		return viewLinkController;
	}

	public EditLinkController getEditLinkController() {
		if (editLinkController == null) {
			editLinkController = new EditLinkController(dbConfig);
		}
		return editLinkController;
	}

	public DeleteLinkController getDeleteLinkController() {
		if (deleteLinkController == null) {
			deleteLinkController = new DeleteLinkController(dbConfig);
		}
		return deleteLinkController;
	}

	public ManageController getManageController() {
		if (manageController == null) {
			manageController = new ManageController(appConfig, getDashboardController(), getManageHomeController(),
					getCreateLinkController(), getViewLinkController(), getEditLinkController(),
					getDeleteLinkController());
		}
		return manageController;
	}

	public ComparerController getComparerController() {
		if (comparerController == null) {
			comparerController = new ComparerController();
		}
		return comparerController;
	}

	public IndexController getIndexController() {
		if (indexController == null) {
			indexController = new IndexController(appConfig, getDashboardController(), getComparerController(),
					getManageController(), getMonitorController());
		}
		return indexController;
	}

	public MonitorController getMonitorController() {
		if (monitorController == null) {
			monitorController = new MonitorController(appConfig);
			initializeMonitorService(monitorController, getOperationManager());
		}
		return monitorController;
	}

	public OperationManager getOperationManager() {
		if (operationManager == null) {
			operationManager = new OperationManager();
		}
		return operationManager;
	}

	private void initializeMonitorService(MonitorController monitorController, OperationManager operationManager) {
		if (monitorService == null) {
			monitorService = new MonitorService(monitorController, operationManager);
		}
	}
}
