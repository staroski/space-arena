package br.com.staroski.games;

import java.rmi.*;

public interface Player extends ServerObject {

	public String getName() throws RemoteException;

	public double getLife() throws RemoteException;

	public void setLife(double life) throws RemoteException;

	public double getMaxLife() throws RemoteException;

	public void setMaxLife(double maxLife) throws RemoteException;
}
