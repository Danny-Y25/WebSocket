package ec.edu.ups;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainApp {
	public static void main(String[] args) {
        runServer();
    }

    private static void runServer() {
        Server server = new Server("localhost", 8080, null, null, FileServerEndpoint.class);
        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
