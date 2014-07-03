package br.com.staroski.games;

import java.rmi.*;

public interface GameClient extends ClientListener, RemoteAccessible {

	GameSettings getSettings() throws RemoteException;

	void setPlayer(Player player) throws RemoteException;
	
	Player getPlayer() throws RemoteException;

	Screen getScreen() throws RemoteException;
}