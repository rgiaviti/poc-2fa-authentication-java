package io.rgiaviti.tsae.api.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiMessage {

	private MessageType type;
	private String code;
	private String message;
	
	public enum MessageType {
		INFO, WARN, ERROR;
	}
}
