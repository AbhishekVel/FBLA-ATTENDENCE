import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private static ServerSocket serverSocket;
	private static int port = 9991;
	static Socket client;

	
	public static void start() throws IOException, InterruptedException {
		System.out.println("Starting server at port " + port);
		serverSocket = new ServerSocket(port);
		
		while(true) {
			System.out.println("Waiting for a client to connect....");
			// .accept() waits until a connection can be accepted, then it continues.
			client = serverSocket.accept();
			System.out.println("A client from the following address has connected: " + client.getInetAddress().getCanonicalHostName());
			readData();
		}
		
	}
	
	private static void readData() throws IOException, InterruptedException{
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String name;
		
		while ((name = br.readLine()) != null) {
			if (name.length() > 0) {
			Core.handleEntry(name);
			break;
			}
		}
	}
	
	public static void sendResult(String title, String message) throws IOException, InterruptedException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write(title+":" + message);
		writer.flush();
		writer.close();
	}
}
