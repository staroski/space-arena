package br.com.staroski.games;

import java.rmi.*;

public interface ClientListener extends Remote {

	void onInput() throws RemoteException;

	void onDraw() throws RemoteException;
}
