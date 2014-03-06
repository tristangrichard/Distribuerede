package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


/**
 * Client. Sending temperature data every sec. (between 14-24 degrees)
 */
public class TemperatureSensor {


	public static void main(String[] args) throws IOException {
		try {
			System.out.println("Skriv serverens IP adresse (brug localhost hvis det er lokalt): ");
			Scanner scan = new Scanner(System.in);
			String ip;
			ip = scan.next();
			Socket s = new Socket(ip, 5000);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			scan.close();
			try {
				while (true) {
					Thread.currentThread();
					// do what you want to do before sleeping
					float random = (float) (Math.random() * 10)+14;
					System.out.println("Sending: " + random+"...");
					random = random * 10 - 140;
					byte b = (byte) random;
					out.write(b);
					out.flush();
					Thread.sleep(1000); // sleep for 1000 ms //do what you want to do after sleeptig
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			System.exit(0);

		}

	}
}