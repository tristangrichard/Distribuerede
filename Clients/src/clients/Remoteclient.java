package clients;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Remoteclient {
	public static void main(String[] args) throws IOException {
		float avg;
		byte b;
		DataOutputStream out = null;
		DataInputStream in = null;
		Socket s = null;
		try {
			s = new Socket("10.16.163.98", 6005);
			out = new DataOutputStream(s.getOutputStream());
			in = new DataInputStream(s.getInputStream());
			try {
				b = (byte)'g';
				out.write(b);
				out.flush();
				avg = in.read();
				avg = ((avg +140) /10);
				System.out.println("Average temperatur: " + avg);
			} catch(IOException e){
				e.printStackTrace();
			}
		} finally {
			out.close();
			in.close();
			s.close();
		}
	}
}

