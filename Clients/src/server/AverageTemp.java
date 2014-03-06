package server;
import java.rmi.*;
import java.rmi.server.*;
public class AverageTemp extends UnicastRemoteObject implements RmiIntf {

	private TCP con;
	protected AverageTemp(TCP con) throws RemoteException {
		super();
		this.con = con;
	}
	@Override
	public double getAverageTemp() throws RemoteException {
		return con.getAverage();
	}


}
