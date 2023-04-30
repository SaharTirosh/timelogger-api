package com.timelogger.timeloggerapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TimeLoggerApiApplicationTests {

	private static final String BASE_URL = "http://localhost:8080/api/v1/timelogger/";

	@Test
	void testGoodCases() throws IOException {
		String id = "1";
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(BASE_URL + "enter?id=" + id);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(200, statusCode);

		post = new HttpPost(BASE_URL + "exit?id=" + id);
		response = client.execute(post);
		statusCode = response.getStatusLine().getStatusCode();
		assertEquals(200, statusCode);

		HttpGet get = new HttpGet(BASE_URL + "info?id=" + id);
		response = client.execute(get);
		statusCode = response.getStatusLine().getStatusCode();
		assertEquals(200, statusCode);

		get = new HttpGet(BASE_URL + "info");
		response = client.execute(get);
		statusCode = response.getStatusLine().getStatusCode();
		assertEquals(200, statusCode);
	}

	@Test
	void testJustExit() throws IOException {
		String id = "2";
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(BASE_URL + "exit?id=" + id);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(500, statusCode);
	}

	@Test
	void testMultipleEnter() throws IOException {
		String id = "3";
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(BASE_URL + "enter?id=" + id);
		client.execute(post);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(500, statusCode);
	}

}
