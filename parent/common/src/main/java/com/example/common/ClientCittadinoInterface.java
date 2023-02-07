package com.example.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCittadinoInterface extends Remote {
    ClientCittadinoInterface registraApplicazioneCittadino() throws RemoteException;
}
