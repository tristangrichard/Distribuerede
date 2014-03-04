package Server;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP implements Runnable {

	private int port;
	private float average;
	private ServerSocket connect = null;
	private Socket client = null;
	private DataInputStream input = null;
	private boolean receiving = true;

	public TCP(){
		this.port = 6000;
		this.average = 14;
	}
	@Override
	public void run() {
		startTCP();
	}
	// Initializing
	public void startTCP(){
		System.out.println("Opening sensor port "+port+"...");
		this.connect = createSocket();
		if (connect == null){return;}
		while(receiving){
			System.out.println("Waiting for sensor connection...");
			this.client = getClient(connect);
			if (client == null){return;}
			System.out.println("Creating sensor inputstream...");
			this.input = createInputStream();
			if (input == null){return;}
			System.out.println("Gathering sensor data...");
			getInput(input);
			close();
		}
		closeSocket();
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
			connect = new ServerSocket(this.port);
			System.out.println("Sensor port "+port+" opened!");
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
			System.out.println("Sensor ("+ clientSocket.getRemoteSocketAddress() +") is now connected");
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
			System.out.println("Done!");
		} 
		catch (IOException e) {
			System.out.println("failed");
			System.out.println(e);
		}
	}
	// Close socket
	private void closeSocket(){
		try{
			System.out.print("Closing TCP socket...");
			this.connect.close();
			System.out.println("Done!");
		}catch (IOException e) {
			System.out.println("failed");
			System.out.println(e);
		}
	}
	// Get average
	public float getAverage(){
		return average;
	}
	// Set boolean
	public void setReceiving(){
		this.receiving = false;
	}
}
