package org.zaunberg.cfptool;

import javax.naming.AuthenticationException;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

public class RestClient {

	private String authString;
	private Client client;

	private String type = MediaType.APPLICATION_JSON;
	private String accept = MediaType.APPLICATION_JSON;

	public static final String HEADER_AUTH = "Authorization";

	public RestClient(String username, String password) {
		auth(username, password);
		client = Client.create();
	}

	public String postRequest(String url, String data)
			throws AuthenticationException {
		
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header(HEADER_AUTH, authString)
				.type(type).accept(accept).post(ClientResponse.class, data);

		checkStatusCode(response);

		return response.getEntity(String.class);
	}

	public String putRequest(String url, String data)
			throws AuthenticationException {
		
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header(HEADER_AUTH, authString)
				.type(type).accept(accept).put(ClientResponse.class, data);

		checkStatusCode(response);

		return response.getEntity(String.class);
	}

	public String getRequest(String url) throws AuthenticationException {
		
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header(HEADER_AUTH, authString)
				.type(type).accept(accept).get(ClientResponse.class);

		checkStatusCode(response);

		return response.getEntity(String.class);
	}

	private void auth(String username, String password) {
		authString = "Basic "+ new String(Base64.encode(username + ":" + password));
	}

	private void checkStatusCode(ClientResponse response)
			throws AuthenticationException {
		
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
	}

}
