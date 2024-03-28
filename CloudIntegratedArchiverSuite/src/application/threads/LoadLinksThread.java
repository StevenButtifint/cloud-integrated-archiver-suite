package application.threads;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.database.DatabaseConnection;
import application.models.Link;

public class LoadLinksThread extends Thread {
	
	private static final Logger logger = LogManager.getLogger(LoadLinksThread.class.getName());

	private DatabaseConnection databaseConnection;
	
	protected List<Link> links;
	
    @Override
    public void run() {
        fetchDatabaseLinks();
    }
    
    protected void fetchDatabaseLinks() {
		databaseConnection = new DatabaseConnection();
		if (databaseConnection.connectToDatabase()) {
			links = databaseConnection.getAllLinks();			
		} else {
			logger.error("Could not connect to links database.");
		}
    }
    
    public List<Link> getLinks() {
    	return links;
    }
}
