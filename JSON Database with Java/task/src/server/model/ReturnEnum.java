package server.model;

public enum ReturnEnum {
	OK("OK"), ERROR("ERROR"), NOTFOUND("No such key"), KEYERROR("Invalid key format");

	private final String label;

	ReturnEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
