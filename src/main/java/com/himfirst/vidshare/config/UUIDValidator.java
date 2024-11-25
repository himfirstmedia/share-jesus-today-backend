package com.himfirst.vidshare.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<ValidUuid, UUID> {
	private final String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

	@Override
	public void initialize(ValidUuid validUuid) {
	}

	@Override
	public boolean isValid(UUID uuid, ConstraintValidatorContext cxt) {
		return (uuid != null ? uuid.toString().matches(this.regex) : true);
	}
}
