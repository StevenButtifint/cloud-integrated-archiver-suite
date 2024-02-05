package application.models;

public class Link {
	
	String name;
	String description;	
	String source;
	String destination;
	String createdDate;
	String lastSynced;
	boolean accessible;
	
	public Link(String name, String description, String source, String destination, String createdDate,
			String lastSynced, boolean accessible) {
		super();
		this.name = name;
		this.description = description;
		this.source = source;
		this.destination = destination;
		this.createdDate = createdDate;
		this.lastSynced = lastSynced;
		this.accessible = accessible;
	}

}
