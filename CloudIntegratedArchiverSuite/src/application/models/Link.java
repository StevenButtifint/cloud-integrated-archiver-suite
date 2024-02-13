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

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getLastSynced() {
		return lastSynced;
	}

	public void setLastSynced(String lastSynced) {
		this.lastSynced = lastSynced;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public String getCreatedDate() {
		return createdDate;
	}
}
