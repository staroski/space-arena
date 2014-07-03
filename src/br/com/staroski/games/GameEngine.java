package br.com.staroski.games;

import java.rmi.*;

public interface GameEngine extends RemoteAccessible {

	Screen getScreen() throws RemoteException;

	void addClient(GameClient client, String username, String password) throws RemoteException;

	GameSettings getSettings() throws RemoteException;

	void removeClient(GameClient client) throws RemoteException;

	void shutdown() throws RemoteException;

	void startup() throws RemoteException;

}