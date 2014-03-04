package Server;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		try {
			System.out.println("Server IP address: "+ InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// Instance needed
		TCP con = new TCP();
		RMI remote = null;
		while (remote == null){
			try {
				remote = new RMI(con);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 try { //special exception handler for registry creation
	            LocateRegistry.createRegistry(1099); 
	            System.out.println("java RMI registry created.");
	        } catch (RemoteException e) {
	            //do nothing, error means registry already exists
	            System.out.println("java RMI registry already exists.");
	        }
	 
	        // Bind this object instance to the name "RmiServer"
	        try {
				Naming.rebind("//localhost/RMI", remote);
			} catch (RemoteException | MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("PeerServer bound in registry");

		// Create Threads
		Thread sensor = new Thread(con);
//		Thread client = new Thread(remote);
		System.out.println("Starting...");
		// Starting Threads
		System.out.println("Sensor thread starting.");
		sensor.start();
//		System.out.println("Client thread starting.");
//		client.start();
		// Waiting for input
		char command = 'r';
		Scanner scan = new Scanner(System.in);
		while (command != 'q'){
			System.out.println("Type 'q' to end program:");
			String temp = scan.next();
			command = temp.charAt(0);
		}
		// Set loop variable to false
		con.setReceiving();
		scan.close();
		// Waiting for Threads to terminate
		System.out.println("Waiting for threads to teminate...");
//		while(!sensor.getState().equals(Thread.State.TERMINATED) ||
//				!client.getState().equals(Thread.State.TERMINATED));


		System.out.println("Program terminated!");

	}	
}
