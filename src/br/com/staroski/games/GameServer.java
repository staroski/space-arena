package br.com.staroski.games;

import java.rmi.*;

public interface GameServer {

	GameSettings getSettings() throws RemoteException;

	Screen getScreen() throws RemoteException;

	Player login(String username, String password) throws RemoteException;

	void logout(Player player) throws RemoteException;
}
