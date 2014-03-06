package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInterface extends Remote {
	   public double getAverageTemp() throws RemoteException;
}
