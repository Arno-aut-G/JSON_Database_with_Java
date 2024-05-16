package client.model;

import com.beust.jcommander.Parameter;

import java.io.Serializable;

public class ClientParameters implements Serializable {
	@Parameter(names = "-t", description = "Type of request")
	private String type;

	@Parameter(names = "-k", description = "Database key")
	private String key;

	@Parameter(names = "-v", description = "Blank-space separated String-value to be stored")
	private String value;

	@Parameter(names = "-in", description = "Json filename to be read from")
	private String filename;

	public String getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getFilename() { return filename; }
}
