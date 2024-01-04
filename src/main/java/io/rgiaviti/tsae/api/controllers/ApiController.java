package io.rgiaviti.tsae.api.controllers;

import java.net.URI;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.rgiaviti.tsae.api.req.CodeReq;
import io.rgiaviti.tsae.api.res.ApiMessage;
import io.rgiaviti.tsae.api.res.ApiMessage.MessageType;
import io.rgiaviti.tsae.api.res.MatchResp;
import io.rgiaviti.tsae.api.res.SecretKeyResp;
import io.rgiaviti.tsae.services.TSEService;

@RestController
@RequestMapping(value = "/2fa")
public class ApiController {

	private final TSEService tseService;

	@Autowired
	public ApiController(final TSEService tseService) {
		this.tseService = tseService;
	}

	@GetMapping(value = "/secretKey")
	public ResponseEntity<SecretKeyResp> generateSecretKey(
			@RequestParam(value = "byteLength", required = false) final Integer byteLength,
			final UriComponentsBuilder uriBuilder) {

		final String secretKey = this.tseService.getRandomSecretKey(byteLength);
		final SecretKeyResp body = new SecretKeyResp(secretKey);
		final URI location = uriBuilder.path("/2fa/secretKey/{secretKey}").buildAndExpand(secretKey).toUri();
		return ResponseEntity.created(location).body(body);
	}

	@PostMapping(value = "/match/{secretKey}")
	public ResponseEntity<Object> matchCodes(@PathVariable("secretKey") final String secretKey,
			@RequestBody final CodeReq code) {
		final ResponseEntity<Object> response;

		if (code == null || Strings.isEmpty(code.getCode())) {
			final ApiMessage message = new ApiMessage(MessageType.ERROR, "412.001", "O campo code é obrigatório");
			response = ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(message);
		} else {
			final Boolean match = this.tseService.matchTotpCode(secretKey, code.getCode());
			final MatchResp matchResp = new MatchResp(match);
			response = ResponseEntity.ok(matchResp);
		}

		return response;

	}
}
