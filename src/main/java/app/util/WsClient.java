package app.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

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

		

	public void menuScelta(){

		Scanner in = new Scanner(System.in);
		boolean nExit = true; 

		while(nExit){
			System.out.println(
				"1) Per inserire una nuova tessera insieme a cliente \n"+
				"2) Per stampare le tessere \n"+
				"3) Per stampare le sedi \n"+
				"4) Per stampare i clienti registrati \n"+
				"5) Per Uscire \n"
			);
			int scelta= in.nextInt();
			switch (scelta) {
				case 1:
					
					break;
				case 2:
					System.out.println(
						"1) Per stampare tutte le tessere \n"+
						"2) Per cercare le tessere in base al Nome e al Cognome \n"
					);
					scelta= in.nextInt();
					switch (scelta) {
						case 1:
							try {
								System.out.println(getTessere());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case 2:
							
							break;

						default:
							break;
					}
					break;
				case 3:
					System.out.println(
						"1) Per stampare tutte le sedi \n"+
						"2) Per cercare una sede in base al nome e indirizzo \n"+
						"3) Per stampare il ragguaglio della creazione delle tessere \n"
					);
					scelta= in.nextInt();
					switch (scelta) {
						case 1:
							
							break;
						case 2:
							
							break;
						case 3:
							
							break;
						default:
							break;
					}
					break;
				case 4:
					scelta= in.nextInt();
					switch (scelta) {
						case 1:
							
							break;
					
						default:
							break;
					}
					break;
				case 5:
					System.out.println("stai pere uscire dal programma");
					nExit = false; 
					in.close();
					break;
				default:
					break;
			}
		}
	}


}
