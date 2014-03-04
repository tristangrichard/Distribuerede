package clients;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Remoteclient {
	public static void main(String[] args) throws IOException {
		float avg;
		byte b;
		String temp;
		char msg = 'a';
		DataOutputStream out = null;
		DataInputStream in = null;
		Socket s = null;
		Scanner scan = null;
		try {
			System.out.println("Skriv serverens IP adresse.");
			scan = new Scanner(System.in);
			temp = scan.next();
			s = new Socket(temp, 6005);
			out = new DataOutputStream(s.getOutputStream());
			in = new DataInputStream(s.getInputStream());
			while(msg != 's' && msg != 'q'){
				System.out.println("Klar til at sende kommando til server...");
				temp = scan.next();
				msg = temp.charAt(0);
				try {
					b = (byte)msg;
					out.write(b);
					out.flush();
					if (msg == 'g'){
						avg = in.read();
						avg = ((avg +140) /10);
						System.out.println("Average temperatur: " + avg);
					}
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		} finally {
			out.close();
			in.close();
			s.close();
			scan.close();
		}

	}
}

