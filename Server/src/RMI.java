import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class RMI implements Runnable{

	TCP con;
	private int port;
	private ServerSocket connect = null;
	private Socket client = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	public boolean receiving = true;

	public RMI(TCP con){
		this.con = con;
		this.port = 6005;
	}
	public void run() {
		while(receiving){
			System.out.println("Opening remote port "+port+"...");
			this.connect = createSocket();
			if (connect == null){return;}
			System.out.println("Waiting for remote request...");
			this.client = getClient(connect);
			if (client == null){return;}
			System.out.println("Creating remote inputstream...");
			this.input = createInputStream();
			if (input == null){return;}
			System.out.println("Gathering remote data...");
			getInput(input);
			close();
		}
	}
	private void sendAverage() {
		System.out.println("Creating remote outputstream...");
		output = createOutputStream();
		try{
			System.out.println("Sending temperature to remote...");
			byte temp = (byte) (con.average*10-140);
			output.write(temp);
			output.flush();
			System.out.println("Temperature sent to remote");
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
		if (temp == 'q'){
			receiving = false;
		}
	}
	// Creates a Socket
	private ServerSocket createSocket(){
		ServerSocket connect = null;
		try {
			connect = new ServerSocket(port);
			System.out.println("Remote port "+port+" opened!");
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
			System.out.println("Remote ("+ clientSocket.getRemoteSocketAddress() +") is now connected");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return clientSocket;
	}
	// Creates input stream
	private DataOutputStream createOutputStream(){
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(client.getOutputStream());
			System.out.println("Done!");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return out;
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
			this.output.close();
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
