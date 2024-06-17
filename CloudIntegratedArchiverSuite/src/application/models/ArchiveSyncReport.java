package application.models;

import application.interfaces.ISyncReport;

public class ArchiveSyncReport extends BaseSyncReport implements ISyncReport {

	public String getReportSummary() {
		if (reportSummary == null) {
			if (linkUpToDate()) {
				return "Archive up to date";
			} else {
				reportSummary = getReportOutcome().toString();

				if (getAddedCount() > 0) {
					reportSummary += " " + getAddedCount() + " archived Files";
				}
			}
		}
		return reportSummary;
	}
}
