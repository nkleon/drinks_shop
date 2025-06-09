package com.DrinksShop.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static void main(String[] args){
        try{
            int portNumber = 10800;
            DBFunctionsImplementation dbFunctionsImplementation = new DBFunctionsImplementation();
            Registry registry = LocateRegistry.createRegistry(portNumber);
            registry.rebind("DBFunctions", dbFunctionsImplementation);
            System.out.println("Server running on port " + portNumber);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}
