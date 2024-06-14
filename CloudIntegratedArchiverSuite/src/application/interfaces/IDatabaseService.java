package application.interfaces;

import java.util.List;

import application.models.Link;

public interface IDatabaseService {

	public boolean initialiseLinkTable();

	public boolean insertLink(String name, String description, String source_location, String destination_location,
			boolean sync_modified, boolean sync_deleted, boolean sync_as_archive);

	public boolean updateLink(int id, String name, String description, String source_location,
			String destination_location, boolean syncModifed, boolean syncDeleted, boolean syncAsArchive);

	public Link getLinkById(int id);

	public boolean deleteLinkById(int id);

	public void updateLastSynced(int linkID);

	public List<Link> getAllLinks();

}
