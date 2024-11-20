package org.example.urlShortener;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final S3Client s3Client = S3Client.builder().build();

	@Override
	public Map<String, String> handleRequest(Map<String, Object> stringObjectMap, Context context) {
		String body = stringObjectMap.get("body").toString();

		Map<String, String> bodyMap;
		try {
			bodyMap = this.objectMapper.readValue(body, Map.class);
		} catch (Exception e) {
			throw new RuntimeException("Error parsing JSON body: " + e.getMessage(), e);
		}

		String originalUrl = bodyMap.get("originalUrl");
		String expirationTime = bodyMap.get("expirationTime");
		long expirationTimeInSeconds = Long.parseLong(expirationTime);


		String shortenedUrlUUID = UUID.randomUUID().toString();

		UrlData urlData = new UrlData(originalUrl, expirationTimeInSeconds);

		try {
			String urlDataJson = this.objectMapper.writeValueAsString(urlData);

			PutObjectRequest request = PutObjectRequest.builder()
					                           .bucket("url-shortener-aws-bucket")
					                           .key(shortenedUrlUUID)
					                           .build();

			this.s3Client.putObject(request, RequestBody.fromString(urlDataJson));
		} catch (Exception e) {
			throw new RuntimeException("Error saving data to S3: " + e.getMessage(), e);
		}

		Map<String, String> response = new HashMap<>();
		response.put("shortenedUrlUUID", shortenedUrlUUID);

		return response;
	}
}