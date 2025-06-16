package com.drinksshop.client;

import com.drinksshop.shared.DBFunctions;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerConnection {
    private static String ipAddress = "localhost";
    private static int portNumber = 10800;
    public static DBFunctions dbFunctions() throws RemoteException, NotBoundException {
        return (DBFunctions) LocateRegistry.getRegistry(ipAddress, portNumber).lookup("DBFunctions");
    }
}
