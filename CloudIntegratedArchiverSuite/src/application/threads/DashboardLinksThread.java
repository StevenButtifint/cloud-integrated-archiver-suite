package application.threads;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DashboardLinksThread extends LoadLinksThread {

	private static final Logger logger = LogManager.getLogger(DashboardLinksThread.class.getName());

	private volatile boolean running = true;

	private Consumer<Boolean> uiUpdater;

	private boolean reload = true;

	public DashboardLinksThread(Consumer<Boolean> uiUpdater) {
		this.uiUpdater = uiUpdater;
	}

	@Override
	public void run() {
		logger.info("Running Dashboard links thread");
		while (running) {
			if (reload) {
				fetchDatabaseLinks();
				setReload(false);
				uiUpdater.accept(true);
			} else {
				uiUpdater.accept(false);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error("dashboard thread did not resume.", e.getMessage());
			}
		}
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public void stopThread() {
		running = false;
	}

}
