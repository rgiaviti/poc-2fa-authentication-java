package io.rgiaviti.tsae.api.res;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecretKeyResp implements Serializable {

	@Serial
	private static final long serialVersionUID = -9160622733716393903L;

	@JsonProperty("secretKey")
	private String secretKey;

}
