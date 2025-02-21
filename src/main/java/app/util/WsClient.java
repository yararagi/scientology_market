package app.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import app.errors.WsException;
import app.model.Clienti;
import app.model.PopolaritaSedi;
import app.model.Sedi;
import app.model.Tessere;



public class WsClient {
	private String baseUrl;
	private HttpClient client;

	public WsClient(String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = HttpClient.newHttpClient();
	}

	public Sedi getSedi() throws Exception{
		URI uri = new URI(this.baseUrl + "/read?content=sedi");
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
		throw new WsException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(Sedi.class, body);
	}

	public Tessere getTessere() throws Exception{
		URI uri = new URI(this.baseUrl + "/read?content=tessere");
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
		throw new WsException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(Tessere.class, body);
	}
	public Clienti getClienti() throws Exception{
		URI uri = new URI(this.baseUrl + "/read?content=clienti");
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
		throw new WsException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(Clienti.class, body);
	}
	public PopolaritaSedi getPopolaritaSedi() throws Exception{
		URI uri = new URI(this.baseUrl + "/read?content=popolarita_sedi");
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
		throw new WsException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(PopolaritaSedi.class, body);
	}

}
