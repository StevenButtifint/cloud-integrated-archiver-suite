package application.models;

import java.util.ArrayList;
import java.util.List;

import application.enums.LinkReportOutcome;
import application.enums.SyncEventType;

public class BaseSyncReport {

	private int completedCount;

	private int incompleteCount;

	private int newFilesCount;

	private LinkReportOutcome reportOutcome;

	protected String reportSummary;

	private List<String> incompleteEventsLog = new ArrayList<>();

	protected synchronized void incrementCompletedCount() {
		completedCount += 1;
	}

	private synchronized void incrementIncompleteCount() {
		incompleteCount += 1;
	}

	private synchronized void incrementAddedCount() {
		newFilesCount += 1;
	}

	public void addedNewFile() {
		incrementCompletedCount();
		incrementAddedCount();
	}

	public void setReportOutcome(LinkReportOutcome syncOutcome) {
		this.reportOutcome = syncOutcome;
	}

	private void logEventDetails(SyncEventType eventType, String notice) {
		incompleteEventsLog.add("[ " + eventType.toString() + " ] " + notice);
	}

	public void logIncompleteEvent(SyncEventType eventType, String notice) {
		logEventDetails(eventType, notice);
		incrementIncompleteCount();
	}

	public int getCompletedCount() {
		return completedCount;
	}

	public int getIncompleteCount() {
		return incompleteCount;
	}

	public int getAddedCount() {
		return newFilesCount;
	}

	public LinkReportOutcome getReportOutcome() {
		if (reportOutcome == null) {
			if (incompleteEventsLog.size() > 0) {
				reportOutcome = LinkReportOutcome.INCOMPLETE;
			} else {
				reportOutcome = LinkReportOutcome.COMPLETED;
			}
		}
		return reportOutcome;
	}

	public List<String> getIncompleteEventsLog() {
		return incompleteEventsLog;
	}

	public boolean linkUpToDate() {
		return (completedCount == 0) && (incompleteEventsLog.size() == 0);
	}

}
