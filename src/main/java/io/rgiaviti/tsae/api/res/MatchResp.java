package io.rgiaviti.tsae.api.res;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchResp implements Serializable {

	@Serial
	private static final long serialVersionUID = 877958599437653782L;

	private Boolean result;
}
