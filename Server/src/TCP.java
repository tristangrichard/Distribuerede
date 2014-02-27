import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP implements Runnable {

	private int port = 5000;
	public float average;
	private ServerSocket connect = null;
	private Socket client = null;
	private DataInputStream input = null;
	public boolean receiving = true;

	@Override
	public void run() {
		startTCP();
	}
	// Initializing
	public void startTCP(){
		System.out.print("Opening sensor port "+port+"...");
		this.connect = createSocket();
		if (connect == null){return;}
		System.out.print("Waiting for sensor connection...");
		this.client = getClient(connect);
		if (client == null){return;}
		System.out.print("Creating sensor inputstream...");
		this.input = createInputStream();
		if (input == null){return;}
		System.out.println("Gathering sensor data...");
		getInput(input);
		close();
		System.out.println("End of Program");
	}
	// Get input from socket
	private void getInput(DataInputStream input){
		int data = 1;
		float total = 0;
		float temp = 0;
		byte in = 0;
		while(receiving){
			try {
				in = (byte)input.readUnsignedByte();
				temp = (float) in;
				temp = (temp+140)/10;
			} catch (IOException e) {
				receiving = false;
			}

			if (temp >= 14 && temp <=24){
				total = total + temp;
				average = total/data;
				System.out.println("Average temperature = "+ average);
				data++;
			}
		}
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
	// Closes connection
	private void close(){
		try {
			System.out.print("Closing sensor connection...");
			this.input.close();
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
