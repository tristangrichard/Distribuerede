package clients;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Client. Sending temperature data every sec. (between 14-24 degrees)
 */
public class Client {


	public static void main(String[] args) throws IOException {
		try {
			Socket s = new Socket("10.16.163.98", 5000);
			//PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			try {
				while (true) {
					Thread.currentThread();
					// do what you want to do before sleeping
					float random = (float) (Math.random() * 10)+14;
					System.out.println("Random: " + random);
					random = random * 10 - 140;
					byte b = (byte) random;
					System.out.println("Random: " + random + "      byte: " + b);
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