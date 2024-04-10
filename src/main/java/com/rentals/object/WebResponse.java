package com.rentals.object;

import java.util.List;

import org.springframework.http.HttpStatus;

public class WebResponse {
	private Boolean success;
	private HttpStatus status;
	private List<? extends ResponseBodyBase> responseBody;
	private String message;

	public WebResponse() {
	}

	public WebResponse(Boolean success, HttpStatus status, List<? extends ResponseBodyBase> responseBody) {
		super();
		this.success = success;
		this.status = status;
		this.responseBody = responseBody;
	}

	public WebResponse(Boolean success, HttpStatus status, String message) {
		super();
		this.success = success;
		this.status = status;
		this.message = message;
	}

	public WebResponse(Boolean success, HttpStatus status, List<? extends ResponseBodyBase> responseBody,
			String message) {
		super();
		this.success = success;
		this.status = status;
		this.responseBody = responseBody;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<? extends ResponseBodyBase> getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(List<? extends ResponseBodyBase> responseBody) {
		this.responseBody = responseBody;
	}

	public String getDescription() {
		return message;
	}

	public void setDescription(String description) {
		this.message = description;
	}

}
