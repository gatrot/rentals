package com.rentals.object;

import org.springframework.http.HttpStatus;

public class WebResponse {
	private Boolean success;
	private HttpStatus status;
	private ResponseBodyBase responseBody;
	private String message;

	public WebResponse() {
		// TODO Auto-generated constructor stub
	}

	public WebResponse(Boolean success, HttpStatus status, ResponseBodyBase responseBody) {
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

	public WebResponse(Boolean success, HttpStatus status, ResponseBodyBase responseBody, String message) {
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

	public ResponseBodyBase getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(ResponseBodyBase responseBody) {
		this.responseBody = responseBody;
	}

	public String getDescription() {
		return message;
	}

	public void setDescription(String description) {
		this.message = description;
	}

}
