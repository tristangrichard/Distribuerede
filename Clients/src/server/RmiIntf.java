package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiIntf extends Remote {
	   public double getAverageTemp() throws RemoteException;
}
