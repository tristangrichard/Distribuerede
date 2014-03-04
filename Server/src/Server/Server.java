package Server;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
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
