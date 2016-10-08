import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private static String hostName = "127.0.0.1";
	static Socket socketClient;
	private static int port = 9991;

	public static void connect() throws UnknownHostException, IOException{ 
		System.out.println("Connecting to " + hostName + ": " + port);
		socketClient = new Socket(hostName, port);
		System.out.println("Connection Established");
	}
	
	public static String readResponse() throws IOException {
		String result = "Error:An error has occured, please contact an FBLA Officer.";// title:message
		BufferedReader br = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		
		while ((result = br.readLine()) != null) {
			if (result.length() > 0) {
				//stops the loop, so that the result = message received
				break;
			}
		}
		System.out.println(result);// for debugging purposes
		
		return result;
		
	}
	
	public static void askForResult(String name) throws IOException { 
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
		System.out.println("Asking for a result");
		writer.write(name);
	    writer.newLine();
		writer.flush();
	}
	

}
