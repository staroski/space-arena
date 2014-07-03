package br.com.staroski.games.spacearena.server.rmi;

import java.rmi.*;
import java.util.*;

import br.com.staroski.games.*;

abstract class AbstractScreen implements Screen {

	private final List<ServerObject> objectsToInsert;
	private final List<Long> objectsToRemove;
	private final Map<Long, ServerObject> objects;

	public AbstractScreen() {
		objectsToInsert = new ArrayList<ServerObject>();
		objectsToRemove = new ArrayList<Long>();
		objects = new HashMap<Long, ServerObject>();
	}

	@Override
	public void addObject(ServerObject object) throws RemoteException {
		GameServer server = SpaceArenaServer.get();
		GameSettings settings = server.getSettings();
		ObjectBinder locator = ObjectBinder.get();
		locator.bindObject(ObjectBinder.getLocalHost(), settings, object);
		objectsToInsert.add(object);
	}

	@Override
	public ServerObject[] getObjects() throws RemoteException {
		Collection<ServerObject> objects = this.objects.values();
		int size = objects.size();
		ServerObject[] array = new ServerObject[size];
		objects.toArray(array);
		return array;
	}

	@Override
	public void onInput(GameInput input) throws RemoteException {
		for (ServerObject object : getObjects()) {
			try {
				if (object.isAccessible()) {
					object.onInput(input);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				try {
					removeObject(object);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onUpdate(GameEvent event) throws RemoteException {
		checkObjects();
		for (ServerObject object : getObjects()) {
			try {
				if (object.isAccessible()) {
					object.onUpdate(event);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				try {
					removeObject(object);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	@Override
	public void removeObject(ServerObject object) throws RemoteException {
		objectsToRemove.add(Long.valueOf(object.getId()));
		ObjectBinder locator = ObjectBinder.get();
		//TODO unbind do objeto
	}

	private void checkObjects() {
		Long key = null;
		ServerObject object;
		List<Long> objectsToRemove = this.objectsToRemove;
		while (!objectsToRemove.isEmpty()) {
			try {
				key = objectsToRemove.remove(0);
				objects.remove(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<ServerObject> objectsToInsert = this.objectsToInsert;
		if (!objectsToInsert.isEmpty()) {
			try {
				object = objectsToInsert.remove(0);
				key = Long.valueOf(object.getId());
				objects.put(key, object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
