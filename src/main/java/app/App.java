package app;

import app.util.WsClient;

public class App {

	public static void main(String[] args) {
		WsClient c = new WsClient("http://localhost/scientology_market/smapi.php");
		c.menuScelta();
	}
}
