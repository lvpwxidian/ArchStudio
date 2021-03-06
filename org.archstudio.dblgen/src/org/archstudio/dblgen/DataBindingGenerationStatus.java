package org.archstudio.dblgen;

public class DataBindingGenerationStatus {

	public enum Status {
		SUCCESS, WARNING, FAILURE
	};

	protected final String schemaURIString;
	protected final Status status;
	protected final String message;
	protected Throwable throwable;

	public DataBindingGenerationStatus(String schemaURIString, Status status, String message, Throwable throwable) {
		super();
		this.schemaURIString = schemaURIString;
		this.status = status;
		this.message = message;
		this.throwable = throwable;
	}

	public String getSchemaURIString() {
		return schemaURIString;
	}

	public Status getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
