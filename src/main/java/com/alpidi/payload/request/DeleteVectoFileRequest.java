package com.alpidi.payload.request;

import javax.validation.constraints.NotBlank;

public class DeleteVectoFileRequest {
	@NotBlank
	private String listingid;

	@NotBlank
	private String filename;

	public String getlistingid() {
		return listingid;
	}

	public void setlistingid(String listingid) {
		this.listingid = listingid;
	}

	public String getfilename() {
		return filename;
	}

	public void setfilename(String filename) {
		this.filename = filename;
	}
}
