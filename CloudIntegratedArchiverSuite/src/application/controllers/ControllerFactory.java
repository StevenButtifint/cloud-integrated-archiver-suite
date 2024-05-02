package application.controllers;

import application.config.Config;

public class ControllerFactory {
	private Config appConfig;
	private Config dbConfig;
	private DashboardController dashboardController;
	private IndexController indexController;
	private ManageHomeController manageHomeController;
	private ManageController manageController;
	private ComparerController comparerController;
	private CreateLinkController createLinkController;
	private ViewLinkController viewLinkController;
	private EditLinkController editLinkController;
	private DeleteLinkController deleteLinkController;

	public ControllerFactory(Config appConfig, Config dbConfig) {
		this.appConfig = appConfig;
		this.dbConfig = dbConfig;
	}

	public DashboardController getDashboardController() {
		if (dashboardController == null) {
			dashboardController = new DashboardController(appConfig, dbConfig);
		}
		return dashboardController;
	}

	public ManageHomeController getManageHomeController() {
		if (manageHomeController == null) {
			manageHomeController = new ManageHomeController(appConfig, dbConfig);
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
					getManageController());
		}
		return indexController;
	}
}
