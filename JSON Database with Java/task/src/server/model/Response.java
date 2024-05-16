package server.model;

import com.google.gson.JsonElement;

public class Response {

	private final ReturnEnum response;

	private final JsonElement value;
	private final String reason;

	public Response(ReturnEnum response, JsonElement value, String errorReason) {
		this.response = response;
		this.value = value;
		this.reason = errorReason;
	}

	public Response(ReturnEnum response, JsonElement value) {
		this(response, value, null);
	}

	public Response(ReturnEnum response, String errorReason) {
		this(response, null, errorReason);
	}

	public Response(ReturnEnum response) {
		this(response, null, null);
	}
	public ReturnEnum getResponse() {
		return response;
	}

	public String getReason() {
		return reason;
	}

	public JsonElement getValue() {
		return value;
	}
}
