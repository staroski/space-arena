package br.com.staroski.games.spacearena.server.rmi;

import java.rmi.*;

import br.com.staroski.games.*;

public final class SpaceArenaServer implements GameServer {

	private static class Holder {

		private static final SpaceArenaServer INSTANCE;
		static {
			try {
				INSTANCE = new SpaceArenaServer();
			} catch (RemoteException e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		}
	}

	public static SpaceArenaServer get() {
		return Holder.INSTANCE;
	}

	private Screen screen;
	private final GameSettings settings;

	private SpaceArenaServer() throws RemoteException {
		settings = new GameSettings("Space Arena", 1024, 768, 32);
		screen = new Stage_01();
//		ObjectBinder locator = ObjectBinder.get();
//		locator.bindScreen(settings, screen);
	}

	@Override
	public Screen getScreen() throws RemoteException {
		return screen;
	}

	@Override
	public GameSettings getSettings() throws RemoteException {
		return settings;
	}

	@Override
	public Player login(String username, String password) throws RemoteException {
		// TODO fazer login a partir de outro servidor
		return new Player_01(username, IdGenerator.newId());
	}

	@Override
	public void logout(Player player) throws RemoteException {
		//		
	}
}
