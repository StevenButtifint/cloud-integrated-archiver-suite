package application.util;

import java.util.HashMap;
import java.util.Map;

public class LinkValidator {

	public static ValidationResult validate(String linkName, String linkDescription, String linkSource, String linkDestination) {
		Map<String, Boolean> validationStatus = new HashMap<>();

		validationStatus.put("name", validateName(linkName));
		validationStatus.put("description", validateDescription(linkDescription));
		validationStatus.put("source", validateSource(linkSource));
		validationStatus.put("destination", validateDestination(linkDestination));

		return new ValidationResult(validationStatus);
	}

	private static boolean validateName(String linkName) {
		return ((linkName != null) && (linkName.length() > 0));
	}

	private static boolean validateDescription(String linkDescription) {
		return ((linkDescription != null) && (linkDescription.length() > 0));
	}

	private static boolean validateSource(String linkSource) {
		return FileOperations.validDirectory(linkSource);
	}

	private static boolean validateDestination(String linkDestination) {
		return FileOperations.validDirectory(linkDestination);
	}
}
