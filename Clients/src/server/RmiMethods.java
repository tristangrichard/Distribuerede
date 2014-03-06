package server;
import java.rmi.*;
import java.rmi.server.*;
public class RmiMethods extends UnicastRemoteObject implements RmiInterface {

	private static final long serialVersionUID = 1L;
	private TCP con;
	
	protected RmiMethods(TCP con) throws RemoteException {
		super();
		this.con = con;
	}
	@Override
	public double getAverageTemp() throws RemoteException {
		return con.getAverage();
	}


}
