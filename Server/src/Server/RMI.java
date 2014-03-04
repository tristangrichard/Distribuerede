package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



public class RMI extends UnicastRemoteObject implements RmiServerIntf {

	private static final long serialVersionUID = 1L;
	private TCP con;
	
	public RMI(TCP con) throws RemoteException{
		super(0);
		this.con = con;
	}
	@Override
	public String getMessage() throws RemoteException {
		return Float.toString(con.getAverage());
	}
//	public void run() {
//
//	}

}
