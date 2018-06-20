package com.allbooks.webapp.validators;

public class PhotoUploadValidationFields {

	@ProfilePhotoUploadConstraint
	private String extension;

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
