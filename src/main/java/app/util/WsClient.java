package app.util;

import java.net.URI;
import java.net.URLEncoder;
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

	Scanner in = new Scanner(System.in);


	public WsClient(String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = HttpClient.newHttpClient();
	}

	public Sedi getSedi(String nome, String indirizzo) throws Exception{

		String url = this.baseUrl + "/read?content=sedi";
		if (nome != null && !nome.isEmpty()) 
			url += "&nome=" + nome;
		
		if (indirizzo != null && !indirizzo.isEmpty()) 
			url += "&indirizzo=" + indirizzo;
		

		URI uri = new URI(url);
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
		throw new WsException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(Sedi.class, body);
	}

	public Tessere getTessere(String nomeCliente, String cognomeCliente) throws Exception{

		String url = this.baseUrl + "/read?content=tessere";
		if (nomeCliente != null && !nomeCliente.isEmpty()) 
			url += "&nome=" + nomeCliente;
		
		if (cognomeCliente != null && !cognomeCliente.isEmpty()) 
			url += "&cognome=" + cognomeCliente;
		

		URI uri = new URI(url);
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
	public PopolaritaSedi getPopolaritaSedi(String nomeSede, String indirizzoSede, String startDate, String endDate) throws Exception{

		String url = this.baseUrl + "/read?content=popolarita_sedi";

		if (nomeSede != null && !nomeSede.isEmpty()) {
			url += "&nome=" + nomeSede;
		}
		if (indirizzoSede != null && !indirizzoSede.isEmpty()) {
			url += "&indirizzo=" + indirizzoSede;
		}
		if (startDate != null && !startDate.isEmpty()) {
			url += "&start_date=" + startDate;
		}
		if (endDate != null && !endDate.isEmpty()) {
			url += "&end_date=" + endDate;
		}

		URI uri = new URI(url);
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
		throw new WsException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(PopolaritaSedi.class, body);
	}
	public void postClienteETessera(String nome, String cognome, String indirizzoMail, int sedeId) throws Exception {

		String url = this.baseUrl + "/crea_clientetessera";
		
		String formData = "nome=" + URLEncoder.encode(nome, "UTF-8") +
		"&cognome=" + URLEncoder.encode(cognome, "UTF-8") +
		"&mail=" + URLEncoder.encode(indirizzoMail, "UTF-8") +
		"&sede_id=" + sedeId;
		
		URI uri = new URI(url);
		HttpRequest req = HttpRequest.newBuilder()
		.uri(uri)
		.POST(HttpRequest.BodyPublishers.ofString(formData))
		.header("Content-Type", "application/x-www-form-urlencoded")
		.build();
		
		HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
		if(res.statusCode() != 200) {
			throw new WsException("Errore: " + res.body());
		}
	}
	public void deleteClienteById(int id) throws Exception {
		
		String url = this.baseUrl + "/elimina_cliente?id=" + id;

		URI uri = new URI(url);
		HttpRequest req = HttpRequest.newBuilder()
		.uri(uri)
		.DELETE()
		.build();
		HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
		if (res.statusCode() == 404) {
			throw new WsException("Cliente non trovato con ID: " + id);
		} else if (res.statusCode() != 200) {
			throw new WsException("Errore: " + res.body());
		}
	}
			
	// Metodo per validare mese e anno
	public static boolean isValidMonthYear(int mese, int anno) {
		// Controlla che il mese sia tra 1 e 12
		if (mese < 1 || mese > 12) {
			return false;
		}
	
	// Controlla che l'anno sia ragionevole (tra 1900 e 2100)
		if (anno < 1900 || anno > 2100) {
				return false;
		}
	
		return true;
	}
			
	// Metodo per combinare mese e anno in una stringa YYYY-MM
	public static String combineYearMonth(int mese, int anno) {
		return String.format("%04d-%02d", anno, mese); // Formato YYYY-MM
	}
	
	public int getscelta(){
		int scelta=404;
		try {
			scelta= Integer.parseInt(in.nextLine());			
		} catch (Exception e) {
			scelta=404;
		}
		return scelta;
	}

	public void menuScelta(){
		
		boolean nExit = true; 
		String nome;
		String cognome;
		String indirizzo;
		
		int id;
		int startMese = 0;
		int startAnno = 0;
		int endMese = 0;
		int endAnno = 0;

		while(nExit){
			System.out.print(
				"1) Per Inserire una nuova Tessera insieme Cliente \n"+
				"2) Per stampare le Tessere \n"+
				"3) Per stampare le Sedi \n"+
				"4) Per stampare i Clienti Registrati \n"+
				"5) Per elmiminare il Cliente e la Tessera \n"+
				"6) Per Uscire \n>"
			);
			int scelta= getscelta();
			switch (scelta) {
				case 1:

					System.out.println("-- Inserimento nuovo cliente con tessera --");
		
					System.out.print("Nome: ");
					nome = in.nextLine();
					
					System.out.print("Cognome: ");
					cognome = in.nextLine();
					
					System.out.print("Email: ");
					indirizzo = in.nextLine();
					
					System.out.print("ID Sede di creazione: ");
					id = in.nextInt();
					in.nextLine();
					
					try {
						postClienteETessera(nome, cognome, indirizzo, id);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					break;

				case 2:

					System.out.print(
						"1) Per stampare tutte le tessere \n"+
						"2) Per cercare le tessere in base al Nome e Cognome di un cliente\n>"
					);
					scelta= getscelta();
					switch (scelta) {
						case 1:

							try {
								System.out.println(getTessere(null,null));
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;

						case 2:

							System.out.print("Inserisci il nome del cliente:");
							nome = in.nextLine();
							System.out.print("Inserisci il cognome del cliente:");
							cognome = in.nextLine();
							try {
								System.out.println(getTessere(nome, cognome));
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;

						default:
							break;
					}
					break;

				case 3:

					System.out.print(
						"1) Per stampare tutte le sedi \n"+
						"2) Per cercare una sede in base al Nome e Indirizzo \n"+
						"3) Per stampare il ragguaglio della creazione delle tessere \n>"
					);
					scelta= getscelta();

					switch (scelta) {
						case 1:

							try {
								System.out.println(getSedi(null,null));
							} catch (Exception e) {
								e.printStackTrace();
							}

							break;

						case 2:

							System.out.print("Inserisci il nome della sede:");
							nome = in.nextLine();
							System.out.print("Inserisci l'indirizzo della sede:");
							indirizzo = in.nextLine();
							try {
								System.out.println(getSedi(nome, indirizzo));
							} catch (Exception e) {
								e.printStackTrace();
							}
                        
							
							break;

						case 3:
						
							System.out.print(
								"1) Per stampare il ragguaglio di tutte le sedi \n"+
								"2) Per stampare il ragguaglio a partire dal Nome e l'Indirizzo \n"+
								"3) Per stampare il ragguaglio dato un Range di Tempo \n"+
								"4) Per stampare il ragguaglio a partire dal Nome e l'Indirizzo con l'aggiunta di un Range di Tempo \n>"
							);

							scelta= getscelta();

							switch (scelta) {
								case 1:

									try {
										System.out.println(getPopolaritaSedi(null,null,null,null));
									} catch (Exception e) {
										e.printStackTrace();
									}
									break;

								case 2:

									System.out.print("Inserisci il nome della sede:");
									nome = in.nextLine().trim();
									System.out.print("Inserisci l'indirizzo della sede:");
									indirizzo = in.nextLine().trim();
									try {
										System.out.println(getPopolaritaSedi(nome, indirizzo, null, null));
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									break;
								
								case 3:

									// Input e validazione del mese e anno di inizio
									while (true) {
										System.out.print("Inserisci il mese di inizio (1-12):");
										startMese = in.nextInt();
										in.nextLine();
										System.out.print("Inserisci l'anno di inizio (es. 2023):");
										startAnno =  in.nextInt();
										in.nextLine();

										if (isValidMonthYear(startMese, startAnno)) {
											break;
										} else {
											System.out.println("Mese o anno non validi. Riprova.");
										}
									}

									// Input e validazione del mese e anno di fine
									while (true) {
										System.out.print("Inserisci il mese di fine (1-12):");
										endMese = in.nextInt();
										in.nextLine();
										System.out.print("Inserisci l'anno di fine (es. 2023):");
										endAnno = in.nextInt();
										in.nextLine();

										if (isValidMonthYear(endMese, endAnno)) {
											break;
										} else {
											System.out.println("Mese o anno non validi. Riprova.");
										}
									}

									try {
										System.out.println(getPopolaritaSedi(null, null, combineYearMonth(startMese, startAnno), combineYearMonth(endMese, endAnno)));
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									break;

								case 4:

									System.out.print("Inserisci il nome della sede:");
									nome = in.nextLine();
									System.out.print("Inserisci l'indirizzo della sede:");
									indirizzo = in.nextLine();

									// Input e validazione del mese e anno di inizio
									while (true) {
										System.out.print("Inserisci il mese di inizio (1-12):");
										startMese =  in.nextInt();
										in.nextLine();
										System.out.print("Inserisci l'anno di inizio (es. 2023):");
										startAnno = in.nextInt();
										in.nextLine();

										if (isValidMonthYear(startMese, startAnno)) {
											break;
										} else {
											System.out.println("Mese o anno non validi. Riprova.");
										}
									}

									// Input e validazione del mese e anno di fine
									while (true) {
										System.out.print("Inserisci il mese di fine (1-12):");
										endMese =  in.nextInt();
										in.nextLine();
										System.out.print("Inserisci l'anno di fine (es. 2023):");
										endAnno =  in.nextInt();
										in.nextLine();

										if (isValidMonthYear(endMese, endAnno) && ((endAnno>startAnno) || (endAnno==startAnno && endMese>=startMese) )) {
											break;
										} else {
											System.out.println("Mese o anno non validi. Riprova.");
										}
									}

									try {
										System.out.println(getPopolaritaSedi(nome, indirizzo, combineYearMonth(startMese, startAnno), combineYearMonth(endMese, endAnno)));
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									break;

								default:	
									break;
							}							

							break;

						default:
							break;
					}
					break;

				case 4:
				
					try {
						System.out.println(getClienti());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case 5:

					System.out.print(
						"1) Per prima ricevevre la lista degli utenti e solo poi eliminarlo \n"+
						"2) Per eliminarlo dal id \n>"
						
					);
					scelta= getscelta();

					switch (scelta) {
						case 1:
							
							try {
								System.out.println(getClienti());
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						case 2:

							System.out.print("Inserisci l'ID del cliente da eliminare:");
							int idCliente =  in.nextInt();
							in.nextLine();
							try {
								deleteClienteById(idCliente);
								System.out.println("Cliente e tessera eliminati con successo.");
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

							break;
					
						default:
							break;
					}
				
					
					break;
				case 6:
					System.out.println("stai per Uscire dal programma");
					nExit = false; 
					in.close();
					break;

				default:
					System.out.println("input non valido, riprova");
					break;
			}
		}
	}


}
