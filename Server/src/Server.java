import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {

	public static void main(String[] args) {
		try {
			System.out.println("Server IP address: "+ InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// Instance needed
		TCP con = new TCP();
		RMI remote = new RMI(con);
		// Create Threads
		Thread sensor = new Thread(con);
		Thread client = new Thread(remote);
		System.out.println("Starting...");

		System.out.println("Sensor thread starting.");
		sensor.start();

		System.out.println("Client thread starting.");
		client.start();


	}	
}
