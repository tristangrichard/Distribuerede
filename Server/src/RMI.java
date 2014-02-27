import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class RMI implements Runnable{

	TCP con;
	private int port = 5005;
	private ServerSocket connect = null;
	private Socket client = null;
	private DataInputStream input = null;
	private DataOutputStream ouput = null;
	public boolean receiving = true;

	public RMI(TCP con){
		this.con = con;
	}
	public void run() {
		System.out.print("Opening remote port "+port+"...");
		this.connect = createSocket();
		if (connect == null){return;}
		System.out.print("Waiting for remote request...");
		this.client = getClient(connect);
		if (client == null){return;}
		System.out.print("Creating remote inputstream...");
		this.input = createInputStream();
		if (input == null){return;}
		System.out.println("Gathering remote data...");
		getInput(input);
		close();
	}
	private void sendAverage() {
		System.out.println("Creating remote outputstream...");
		DataOutputStream out = createOutputStream();
		try{
			System.out.print("Sending temperature to remote...");
		byte temp = (byte) (con.average*10-140);
		out.write(temp);
		out.flush();
		System.out.println("Done!");
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	private void getInput(DataInputStream input){

		char temp = 'a';
		byte in = 0;
		try {
			in = (byte)input.readUnsignedByte();
			temp = (char) in;
		} catch (IOException e) {
			receiving = false;
		}
		if (temp == 'g'){
			sendAverage();
		}
	}
	// Creates a Socket
	private ServerSocket createSocket(){
		ServerSocket connect = null;
		try {
			connect = new ServerSocket(port);
			System.out.println("Done!");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return connect;
	}
	// Listens for incoming connections
	private Socket getClient(ServerSocket connect){
		Socket clientSocket = null;
		try {
			clientSocket = connect.accept();
			System.out.println("Client ("+ clientSocket.getRemoteSocketAddress() +") is now connected");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return clientSocket;
	}
	// Creates input stream
	private DataOutputStream createOutputStream(){
		DataOutputStream output = null;
		try {
			output = new DataOutputStream(client.getOutputStream());
			System.out.println("Done!");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return output;
	}
	// Creates input stream
	private DataInputStream createInputStream(){
		DataInputStream input = null;
		try {
			input = new DataInputStream(client.getInputStream());
			System.out.println("Done!");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return input;
	}
	// Closes connection
	private void close(){
		try {
			System.out.print("Closing remote connection...");
			this.input.close();
			this.ouput.close();
			this.client.close();
			this.connect.close();
			System.out.println("Done!");
		} 
		catch (IOException e) {
			System.out.print("failed");
			System.out.println(e);
		}
	}

}
