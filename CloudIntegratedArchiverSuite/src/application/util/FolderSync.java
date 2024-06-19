package application.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.enums.SyncEventType;
import application.models.ArchiveSyncReport;
import application.models.BaseSyncReport;
import application.models.DefaultSyncReport;

public class FolderSync {

	private static final Logger logger = LogManager.getLogger(FolderSync.class.getName());

	public static ArchiveSyncReport syncAsArchive(ArchiveSyncReport archiveSyncReport, Path source, Path destination) {
		final Path finalDestination = createArchiveSubfolderPath(destination);
		createArchiveSubfolder(archiveSyncReport, finalDestination);
		syncNewFiles(archiveSyncReport, source, finalDestination);
		return archiveSyncReport;
	}

	private static Path createArchiveSubfolderPath(Path archiveParentPath) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
		String timestamp = LocalDateTime.now().format(formatter);
		return archiveParentPath.resolve(timestamp);
	}

	private static void createArchiveSubfolder(ArchiveSyncReport archiveSyncReport, Path finalDestination) {
		try {
			Files.createDirectories(finalDestination);
		} catch (IOException e) {
			logger.error("Failed to create directories." + e.getMessage());
			archiveSyncReport.logIncompleteEvent(SyncEventType.INTERRUPTED,
					"Archive destination could not be created.");
		}
	}


	private static void syncNewFiles(BaseSyncReport defaultSyncReport, Path source, Path finalDestination) {
		try {
			Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					Path correspondingDir = finalDestination.resolve(source.relativize(dir));
					if (!Files.exists(correspondingDir)) {
						Files.createDirectories(correspondingDir);
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Path correspondingFile = finalDestination.resolve(source.relativize(file));
					if (!Files.exists(correspondingFile) || Files.getLastModifiedTime(file)
							.compareTo(Files.getLastModifiedTime(correspondingFile)) > 0) {
						// File exists in source but not in target, or source is newer
						Files.copy(file, correspondingFile, StandardCopyOption.REPLACE_EXISTING);
						defaultSyncReport.addedNewFile();
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Path correspondingDir = finalDestination.resolve(source.relativize(dir));
					if (!Files.exists(correspondingDir) || (Files.isDirectory(correspondingDir)
							&& !Files.list(correspondingDir).findAny().isPresent())) {
						// Directory exists in source but not in target, or it exists but is empty
						Files.createDirectories(correspondingDir);
					}
					return FileVisitResult.CONTINUE;
				}
			});

		} catch (IOException e) {
			defaultSyncReport.logIncompleteEvent(SyncEventType.INTERRUPTED, "Unable to sync new files.");
			logger.error("Unable to sync new files." + e.getMessage());
		}
	}

}