package app;
import app.model.*;
import app.util.WsClient;

public class App {
	public static void main(String[] args) {
		WsClient c = new WsClient("http://localhost/scientology_market/smapi.php");

		try {
			System.out.println(c.getPopolaritaSedi());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
