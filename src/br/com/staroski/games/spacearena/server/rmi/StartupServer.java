package br.com.staroski.games.spacearena.server.rmi;

import java.rmi.*;

import br.com.staroski.games.*;

public final class StartupServer {

	public static void main(String[] args) {
		try {
			new StartupServer().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private StartupServer() {
	}

	private void execute() throws RemoteException {
		GameServer server = SpaceArenaServer.get();
		GameEngine engine = new DefaultEngine(server);
		engine.startup();
	}
}
