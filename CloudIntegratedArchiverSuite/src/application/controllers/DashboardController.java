package application.controllers;

import application.models.Dashboard;
import javafx.concurrent.Task;


public class DashboardController {
	Dashboard dashboard = new Dashboard();

	
	public void startDashboardTask() {
        Task<Void> updateTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                }
            }
        };
        new Thread(updateTask).start();
    }

}
