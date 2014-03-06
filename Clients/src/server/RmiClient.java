package server;

import java.rmi.Naming;
import java.util.Scanner;

public class RmiClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char com = 'a';
		System.out.println("Skriv serverens IP adresse (brug localhost hvis det er lokalt): ");
		Scanner scan = new Scanner(System.in);
		String ip = scan.next();
		String rmiName = ("//"+ip+"/RMIServer");
		System.out.println("Connecting to: " + rmiName);
		while(com != 'q'){
			System.out.println("Press 'g' to request temperatur or 'q' to quit program.");
			String temp = scan.next();
			com = temp.charAt(0);
			if (com == 'g'){
				try {
					RmiInterface r = (RmiInterface)Naming.lookup(rmiName);
					System.out.println(r.getAverageTemp());
				}
				catch (Exception e){
					System.out.println("Error: " + e);
				}
			}
		}
		scan.close();
	}

}
