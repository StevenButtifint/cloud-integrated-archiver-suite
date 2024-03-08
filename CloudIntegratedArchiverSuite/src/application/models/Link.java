package application.models;

public class Link {

	int id;
	String name;
	String description;
	String source;
	String destination;
	String createdDate;
	String lastSynced;
	boolean accessible;
	
	public Link(int id, String name, String description, String source, String destination, String createdDate,
			String lastSynced, boolean accessible) {
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

	}

	public String getLastSynced() {
		return lastSynced;
	}

	public boolean getAccessible() {
		return accessible;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedDate() {
		return createdDate;
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
