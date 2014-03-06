package server;

import java.rmi.Naming;
import java.util.Scanner;

public class RmiClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Skriv serverens IP adresse (brug localhost hvis det er lokalt): ");
		Scanner scan = new Scanner(System.in);
		String ip;
		ip = scan.next();
		String rmiName = ("rmi://"+ip+"/RMIServer");
		System.out.println("Connecting to: " + rmiName);
		scan.close();
		try {
			RmiIntf r = (RmiIntf)Naming.lookup(rmiName);
			System.out.println(r.getAverageTemp());
		}
		catch (Exception e){
			System.out.println("Error: " + e);
		}

	}

}
