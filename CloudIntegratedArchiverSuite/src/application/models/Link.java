package application.models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Link {

	private int id;

	private String name;

	private String description;

	private String source;

	private String destination;

	private Date createdDate;

	private Date lastSynced;

	private boolean accessible;

	private boolean syncModified;

	private boolean syncDeleted;

	private boolean syncAsArchive;

	public Link(int id, String name, String description, String source, String destination, Date createdDate,
			Date lastSynced, boolean syncModified, boolean syncDeleted, boolean syncAsArchive) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.source = source;
		this.destination = destination;
		this.createdDate = createdDate;
		this.lastSynced = lastSynced;
		this.syncModified = syncModified;
		this.syncDeleted = syncDeleted;
		this.syncAsArchive = syncAsArchive;
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

	public boolean getSyncModified() {
		return syncModified;
	}

	public boolean getSyncDeleted() {
		return syncDeleted;
	}

	public boolean getSyncAsArchive() {
		return syncAsArchive;
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
		} else if (daysSinceSynced > 0) {
			return "Last Synced " + daysSinceSynced + " days ago";
		} else {
			return "Never Synced";
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

	public void setSyncModified(boolean syncModified) {
		this.syncModified = syncModified;
	}

	public void setSyncDeleted(boolean syncDeleted) {
		this.syncDeleted = syncDeleted;
	}

	public void setSyncAsArchive(boolean syncAsArchive) {
		this.syncAsArchive = syncAsArchive;
	}
}
