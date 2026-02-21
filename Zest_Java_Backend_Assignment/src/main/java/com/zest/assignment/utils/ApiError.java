package com.zest.assignment.utils;


import java.util.List;

public class ApiError {
	private int statusCode;
	private String message;
	private List<String> errors;
	private boolean success;
	private Object data;

	public ApiError(int statusCode, String message, List<String> errors) {
		this.statusCode = statusCode;
		this.message = message != null ? message : "Something went wrong";
		this.errors = errors;
		this.success = false;
		this.data = null;
	}

	// Getters and setters

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public Object getData() {
		return data;
	}
}
