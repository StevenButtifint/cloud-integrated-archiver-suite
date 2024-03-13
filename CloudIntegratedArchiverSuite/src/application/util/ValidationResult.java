package application.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationResult {
	private final Map<String, Boolean> validationStatus;

	public ValidationResult(Map<String, Boolean> validationStatus) {
		this.validationStatus = validationStatus;
	}

	public boolean isValid() {
		return !validationStatus.containsValue(false);
	}

	public Map<String, Boolean> getValidationStatus() {
		return new HashMap<>(validationStatus);
	}

	public Set<String> getInvalidStrings() {
		return validationStatus.entrySet().stream().filter(entry -> !entry.getValue()).map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}
}
