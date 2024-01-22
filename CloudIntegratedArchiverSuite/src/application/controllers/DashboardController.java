package application.controllers;

import application.models.Dashboard;
import application.services.DashboardService;


public class DashboardController {
	Dashboard dashboard = new Dashboard();
	private DashboardService dashboardService;

	
	public void startDashboardService() {
		dashboardService = new DashboardService();
		dashboardService.start();
	}

}
