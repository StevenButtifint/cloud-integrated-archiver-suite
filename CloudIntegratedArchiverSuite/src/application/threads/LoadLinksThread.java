package application.threads;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.database.DatabaseConnection;
import application.models.Link;

public class LoadLinksThread extends Thread {
	
	private static final Logger logger = LogManager.getLogger(LoadLinksThread.class.getName());

	private DatabaseConnection databaseConnection;
	
	private Config dbConfig;
	
	protected List<Link> links;
	
	public LoadLinksThread(Config dbConfig) {
		this.dbConfig = dbConfig;
	}
	
    @Override
    public void run() {
        fetchDatabaseLinks();
    }
    
    protected void fetchDatabaseLinks() {
		databaseConnection = new DatabaseConnection(dbConfig);
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
