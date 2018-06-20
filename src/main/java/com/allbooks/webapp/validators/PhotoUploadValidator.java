package com.allbooks.webapp.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhotoUploadValidator implements
ConstraintValidator<ProfilePhotoUploadConstraint, String>{

	@Override
	public void initialize(ProfilePhotoUploadConstraint constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value.equals("jpg"))
			return true;
		else
			return false;
		
	}

}
