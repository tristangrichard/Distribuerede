package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class RMI implements Runnable{

	private TCP con;
	private int port;
	private ServerSocket connect = null;
	private Socket client = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private boolean receiving = true;

	public RMI(TCP con){
		this.con = con;
		this.port = 6005;
	}
	public void run() {
		System.out.println("Opening remote port "+port+"...");
		this.connect = createSocket();
		if (connect == null){return;}
		while(receiving){
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
		closeSocket();
	}
	// Sends average temperature
	private void sendAverage() {
		System.out.println("Creating remote outputstream...");
		output = createOutputStream();
		try{
			System.out.println("Sending temperature to remote...");
			byte temp = (byte) (con.getAverage()*10-140);
			output.write(temp);
			output.flush();
			System.out.println("Temperature sent to remote");
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	// Get input from socket
	private void getInput(DataInputStream input){

		char temp = 'a';
		byte in = 0;
		while(temp != 's'){
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
				break;
			}
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
			System.out.print("Closing remote client connection...");
			this.input.close();
			this.output.close();
			this.client.close();
			System.out.println("Done!");
		}catch (IOException e) {
			System.out.println("failed");
			System.out.println(e);
		}

	}
	// Close socket
	private void closeSocket(){
		try{
			System.out.print("Closing remote socket...");
			this.connect.close();
			System.out.println("Done!");
		}catch (IOException e) {
			System.out.println("failed");
			System.out.println(e);
		}
	}
	// Set boolean
	public void setReceiving(){
		this.receiving = false;
	}

}
