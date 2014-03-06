package server;
import java.io.File;
import java.rmi.*;
public class RmiServer {
	public RmiServer() {
		TCP con = new TCP();
		try{
		File f1 = new File("./bin/");
		String codeBase=f1.getAbsoluteFile().toURI().toURL().toString();
		System.setProperty("java.rmi.server.codebase", codeBase);		
		
		RmiIntf r = new AverageTemp(con);
		Naming.rebind("rmi://localhost:1099/RMIServer", r);
		
		}
		catch (Exception e){
			System.out.println("Errors: "+ e);
		}
		con.startTCP();
	
	}


	public static void main (String[] argv) {
	RmiServer s = new RmiServer();

}
}