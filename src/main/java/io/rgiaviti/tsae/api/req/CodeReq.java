package io.rgiaviti.tsae.api.req;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeReq implements Serializable {

	@Serial
	private static final long serialVersionUID = 9019832561105042386L;

	@JsonProperty("code")
	private String code;
}
