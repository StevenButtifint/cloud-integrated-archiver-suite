package application.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.config.Config;
import application.database.DatabaseConnectionPool;
import application.interfaces.IDashboardService;
import application.interfaces.IDatabaseService;
import application.interfaces.IMonitorService;
import application.interfaces.ISchedulerService;
import application.services.DashboardService;
import application.services.DatabaseService;
import application.services.MonitorService;
import application.services.SchedulerService;
import application.util.LinkOperationManager;

public class ControllerFactory {
	private Config appConfig;
	private Config dbConfig;
	private ExecutorService executorService;
	private IDatabaseService databaseService;
	private IDashboardService dashboardService;
	private ISchedulerService schedulerService;
	private IMonitorService monitorService;
	private IndexController indexController;
	private DashboardController dashboardController;
	private ManageHomeController manageHomeController;
	private ManageController manageController;
	private ComparerController comparerController;
	private CreateLinkController createLinkController;
	private ViewLinkController viewLinkController;
	private EditLinkController editLinkController;
	private DeleteLinkController deleteLinkController;
	private MonitorController monitorController;
	private CloudController cloudController;
	private LinkOperationManager operationManager;

	public ControllerFactory(Config appConfig, Config dbConfig) {
		this.appConfig = appConfig;
		this.dbConfig = dbConfig;
	}
	
	public void initializeServices() {
		initializeDatabaseService();
		initializeDashboardService();
		initializeSchedulerService();
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
	
	private void initializeDatabaseService() {
		if (databaseService == null) {
			databaseService = new DatabaseService(newDatabaseConnectionPool(), dbConfig);
		}
	}
	
	private void initializeDashboardService() {
		if (dashboardService == null) {
			dashboardService = new DashboardService(getDatabaseService(), getExecutorService());
		}
	}
	
	private void initializeSchedulerService() {
		if (schedulerService == null) {
			schedulerService = new SchedulerService();
			schedulerService.scheduleTask(getDashboardController()::orderLinksListAvailability, 15);
		}
	}
	
	private void initializeMonitorService() {
		if (monitorService == null) {
			monitorService = new MonitorService(getMonitorController(), getOperationManager());
			monitorService.initializeControllerManager();
		}
	}
	
	private DatabaseConnectionPool newDatabaseConnectionPool() {
		return new DatabaseConnectionPool(
				dbConfig.getProperty("db.driver"),
				dbConfig.getProperty("db.url"), 
				dbConfig.getProperty("db.username"),
				dbConfig.getProperty("db.password"));
	}

	private IDatabaseService getDatabaseService() {
		if (databaseService == null) {
			initializeDatabaseService();
		}
		return databaseService;
	}
	
	private IDashboardService getDashboardService() {
		if (dashboardService == null) {
			initializeDashboardService();
		}
		return dashboardService;
	}

	private ExecutorService getExecutorService() {
		if (executorService == null) {
			executorService = Executors.newCachedThreadPool();
		}
		return executorService;
	}

	public synchronized DashboardController getDashboardController() {
		if (dashboardController == null) {
			dashboardController = new DashboardController(getDashboardService(), getMonitorService(), appConfig, dbConfig);
		}
		return dashboardController;
	}

	public ManageHomeController getManageHomeController() {
		if (manageHomeController == null) {
			manageHomeController = new ManageHomeController(appConfig, getDatabaseService());
		}
		return manageHomeController;
	}

	public CreateLinkController getCreateLinkController() {
		if (createLinkController == null) {
			createLinkController = new CreateLinkController(getDatabaseService());
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
			editLinkController = new EditLinkController(getDatabaseService());
		}
		return editLinkController;
	}

	public DeleteLinkController getDeleteLinkController() {
		if (deleteLinkController == null) {
			deleteLinkController = new DeleteLinkController(getDatabaseService());
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
					getManageController(), getMonitorController(), getCloudController());
		}
		return indexController;
	}

	public MonitorController getMonitorController() {
		if (monitorController == null) {
			monitorController = new MonitorController(appConfig);
		}
		return monitorController;
	}
	
	public CloudController getCloudController() {
		if (cloudController == null) {
			cloudController = new CloudController();
		}
		return cloudController;
	}

	public LinkOperationManager getOperationManager() {
		if (operationManager == null) {
			operationManager = new LinkOperationManager();
		}
		return operationManager;
	}
	
	private IMonitorService getMonitorService() {
		if (monitorService == null) {
			initializeMonitorService();
		}
		return monitorService;
	}
}
