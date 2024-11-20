package org.example.urlShortener;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Map<String, String> handleRequest(Map<String, Object> stringObjectMap, Context context) {
		String body = stringObjectMap.get("body").toString();

		Map<String, String> bodyMap;
		try {
			bodyMap = this.objectMapper.readValue(body, Map.class);
		} catch (Exception exception) {
			throw new RuntimeException("Error parsing JSON body: " + exception.getMessage(), exception);
		}

		String originalUrl = bodyMap.get("originalUrl");
		String expirationTime = bodyMap.get("expirationTime");

		String uuid = UUID.randomUUID().toString();

		Map<String, String> response = new HashMap<>();

		response.put("shortenedUrlUUID", uuid);
		return null;
	}
}