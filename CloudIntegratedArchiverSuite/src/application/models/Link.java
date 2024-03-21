package application.models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Link {

	int id;
	String name;
	String description;
	String source;
	String destination;
	Date createdDate;
	Date lastSynced;
	boolean accessible;

	public Link(int id, String name, String description, String source, String destination, Date createdDate,
			Date lastSynced, boolean accessible) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.source = source;
		this.destination = destination;
		this.createdDate = createdDate;
		this.lastSynced = lastSynced;
		this.accessible = accessible;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getLastSynced() {
		return lastSynced;
	}

	public boolean getAccessible() {
		return accessible;
	}

	public int daysSinceSynced() {
		LocalDate lastSyncedDate = LocalDate.parse(getLastSynced().toString());
		LocalDate currentDate = LocalDate.now();

		// calculate the difference between the two dates
		long daysBetween = ChronoUnit.DAYS.between(lastSyncedDate, currentDate);
		return Math.toIntExact(daysBetween);
	}

	public String sinceSyncedString() {
		int daysSinceSynced = daysSinceSynced();
		if (daysSinceSynced == 0) {
			return "Last Synced Today";
		} else if (daysSinceSynced == 1) {
			return "Last Synced Yesterday";
		} else {
			return "Last Synced " + daysSinceSynced + " days ago";
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setLastSynced(Date lastSynced) {
		this.lastSynced = lastSynced;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
}
