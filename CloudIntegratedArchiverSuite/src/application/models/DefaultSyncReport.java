package application.models;

import application.interfaces.ISyncReport;

public class DefaultSyncReport extends BaseSyncReport implements ISyncReport {

	private int modifiedFilesCount;

	private int deletedFilesCount;

	private void incrementModifiedCount() {
		modifiedFilesCount += 1;
	}

	private void incrementDeletedCount() {
		deletedFilesCount += 1;
	}

	public void updatedModifiedFile() {
		incrementCompletedCount();
		incrementModifiedCount();
	}

	public void updatedDeletedFile() {
		incrementCompletedCount();
		incrementDeletedCount();
	}

	public int getModifiedCount() {
		return modifiedFilesCount;
	}

	public int getDeletedCount() {
		return deletedFilesCount;
	}

	public String getReportSummary() {
		if (reportSummary == null) {
			if (linkUpToDate()) {
				return "Link up to date";
			} else {
				reportSummary = getReportOutcome().toString();

				if (getAddedCount() > 0) {
					reportSummary += " " + getAddedCount() + " New Files,";
				}

				if (getModifiedCount() > 0) {
					reportSummary += " " + getModifiedCount() + " Modified Files,";
				}

				if (getDeletedCount() > 0) {
					reportSummary += " " + getDeletedCount() + " Cleared Files,";
				}
			}
		}
		return reportSummary;
	}
}
