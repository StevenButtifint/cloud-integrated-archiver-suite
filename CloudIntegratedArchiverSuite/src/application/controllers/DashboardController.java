package application.controllers;

import application.models.Dashboard;
import application.services.DashboardService;


public class DashboardController extends BaseController {
	Dashboard dashboard = new Dashboard();
	private DashboardService dashboardService;

	
    public void initialize() {
    }
    
	public void startDashboardService() {
		dashboardService = new DashboardService();
		dashboardService.start();
	}

}
