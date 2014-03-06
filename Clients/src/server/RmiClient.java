package server;

import java.rmi.Naming;

public class RmiClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String rmiName = ("rmi://192.168.1.22/RMIServer");
		System.out.println("Connecting to: " + rmiName);
		
		try {
			RmiIntf r = (RmiIntf)Naming.lookup(rmiName);
			System.out.println(r.getAverageTemp());
		}
		catch (Exception e){
			System.out.println("Error: " + e);
		}

	}

}
