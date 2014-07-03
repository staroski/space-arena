package br.com.staroski.games.spacearena.server.rmi;

import java.rmi.*;
import java.util.*;

import br.com.staroski.games.*;
import br.com.staroski.games.spacearena.*;
import br.com.staroski.games.spacearena.server.corba.*;

class Stage_01 extends AbstractScreen {

	private final long id;

	private final Clock itemSpawnClock;

	@Override
	public void onUpdate(GameEvent event) throws RemoteException {
		super.onUpdate(event);

		itemSpawnClock.tick(event.time);
		if (itemSpawnClock.hasElapsed(10)) {
			itemSpawnClock.reset();
			spawnItem();
		}
	}

	private void spawnItem() {
		try {
			EnergyItemProvider provider = CorbaFactory.getEnergyItemProvider();
			provider.createNewItem();
			long id = provider.getId();
			int energy = provider.getEnergy();
			EnergyItem_01 item = new EnergyItem_01(id, energy);

			// definir posição aleatoria pra jogar o item
			GameSettings settings = SpaceArenaServer.get().getSettings();
			int w = settings.getWidth();
			int h = settings.getHeigth();
			Bounds r = item.getBounds();
			double x = new Random().nextInt((int) (w - r.width)) + r.width;
			double y = new Random().nextInt((int) (h - r.height)) + r.height;
			item.setLocation(new Point(x, y));

			// jogar o item
			addObject(item);
		} catch (Throwable t) {
			System.err.println("não foi possivel inserir um item no mapa: " + t.getMessage());
		}
	}

	@Override
	public long getId() throws RemoteException {
		return id;
	}

	@Override
	public int getRendererId() {
		return SpaceArena.STAGE_01.getId();
	}

	Stage_01() {
		id = IdGenerator.newId();

		itemSpawnClock = new Clock();
	}

	public boolean isAccessible() throws RemoteException {
		return true;
	}

}
