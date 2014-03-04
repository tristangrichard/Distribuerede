package clients;

import java.rmi.Naming;

public class Remoteclient {
	public static void main(String[] args) throws Exception{
			RmiServerIntf RMISI = (RmiServerIntf)Naming.lookup("//localhost/RMI");
			System.out.println(RMISI.getMessage());
		
	}
}

