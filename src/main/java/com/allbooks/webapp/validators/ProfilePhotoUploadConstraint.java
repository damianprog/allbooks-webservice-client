package com.allbooks.webapp.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PhotoUploadValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProfilePhotoUploadConstraint {

	String message() default "Wrong file extension.Choose JPG";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
	
}
