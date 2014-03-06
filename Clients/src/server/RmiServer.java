package server;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

/**
 * Server program.
 **/
public class RmiServer {
	public RmiServer() {
		TCP con = new TCP();
		// Setup RMI
		try {
			System.out.println("Server ip = "+InetAddress.getLocalHost().getHostAddress());
			LocateRegistry.createRegistry(1099);	
			RmiInterface r = new RmiMethods(con);
			Naming.rebind("//localhost:1099/RMIServer", r);
		} catch (UnknownHostException e) {
			System.out.println("Unable to retrieve server ip...");
		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		// Start thread for sensor
		con.startTCP();
	}

	public static void main (String[] argv) {
		System.out.println("Server starting...");
		RmiServer s = new RmiServer();
	}
}