package br.com.staroski.games;

import java.net.*;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public final class ObjectBinder {

	private static final class Holder {

		static final ObjectBinder INSTANCE;
		static {
			try {
				INSTANCE = new ObjectBinder();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		}
	}

	private static final int PORT = 8008;

	public static ObjectBinder get() {
		return Holder.INSTANCE;
	}

	private static void checkSettings(final GameSettings settings) {
		if (settings == null) {
			throw new IllegalArgumentException("settings are null");
		}
		String name = settings.getName();
		if (name == null) {
			throw new IllegalStateException("game name is null in settings");
		}
		name = name.trim();
		if ("".equals(name)) {
			throw new IllegalStateException("game name is empty in settings");
		}
	}

	private ObjectBinder() throws RemoteException {
		try {
			System.out.println("tentando criar rmi registry...");
			LocateRegistry.createRegistry(PORT);
			System.out.println("rmi registry criado com sucesso!");
		} catch (Exception e) {
			System.out.println("rmi registry possivelmente ja foi criado, tentando obte-lo...");
			LocateRegistry.getRegistry(PORT);
			System.out.println("rmi registry obtido com sucesso!");
		}
	}

	public String bindClient(String host, GameSettings settings, final RemoteAccessible client) throws RemoteException {
		if (client == null) {
			throw new IllegalArgumentException("client is null");
		}
		checkSettings(settings);
		try {
			//			Registry registry = LocateRegistry.getRegistry(host, PORT);
			final String name = getInstanceName(settings, client.getClass().getSimpleName(), client.getId());
			RemoteAccessible stub = (RemoteAccessible) UnicastRemoteObject.exportObject(client, 0);

			String url = "//" + host + ":" + PORT + "/" + name;
			Naming.bind(url, stub);
			//			registry.rebind(name, stub);
			System.out.println("host:     " + getLocalHost());
			System.out.println("instance: " + name);
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String bindScreen(String host, GameSettings settings, final RemoteAccessible screen) throws RemoteException {
		if (screen == null) {
			throw new IllegalArgumentException("screen is null");
		}
		checkSettings(settings);
		try {
			//			Registry registry = LocateRegistry.getRegistry(host, PORT);
			final String name = getInstanceName(settings, screen.getClass().getSimpleName(), screen.getId());
			RemoteAccessible stub = (RemoteAccessible) UnicastRemoteObject.exportObject(screen, 0);

			String url = "//" + host + ":" + PORT + "/" + name;
			Naming.bind(url, stub);
			//			registry.rebind(name, stub);
			System.out.println("host:     " + getLocalHost());
			System.out.println("instance: " + name);
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String bindObject(String host, GameSettings settings, final RemoteAccessible object) throws RemoteException {
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}
		checkSettings(settings);
		try {
			//			Registry registry = LocateRegistry.getRegistry(host, PORT);

			final String name = getInstanceName(settings, object.getClass().getSimpleName(), object.getId());
			RemoteAccessible stub = (RemoteAccessible) UnicastRemoteObject.exportObject(object, 0);

			String url = "//" + host + ":" + PORT + "/" + name;
			Naming.bind(url, stub);
			//			registry.bind(name, stub);
			System.out.println("host:     " + getLocalHost());
			System.out.println("instance: " + name);
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String bindEngine(GameSettings settings, final GameEngine engine) throws RemoteException {
		if (engine == null) {
			throw new IllegalArgumentException("engine is null");
		}
		checkSettings(settings);
		try {
			//			Registry registry = LocateRegistry.createRegistry(PORT);
			final String name = getInstanceName(settings, engine.getClass().getSimpleName(), engine.getId());
			GameEngine stub = (GameEngine) UnicastRemoteObject.exportObject(engine, 0);

			String url = "//" + getLocalHost() + ":" + PORT + "/" + name;
			Naming.bind(url, stub);
			//			registry.rebind(name, stub);
			System.out.println("host:     " + getLocalHost());
			System.out.println("instance: " + name);
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public GameEngine lookupEngine(final String host, final String name) throws RemoteException {
		try {
			String url = "//" + host + ":" + PORT + "/" + name;
			final GameEngine engine = (GameEngine) Naming.lookup(url);
			return engine;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public static String getLocalHost() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (final UnknownHostException e) {
			e.printStackTrace();
			return "localhost";
		}
	}

	private String getInstanceName(final GameSettings settings, String objectName, long id) {
		return (System.getProperty("user.name") + "." + settings.getName() + "." + objectName + "." + Long.toHexString(id)).replaceAll("\\s", ".")
				.toLowerCase();
	}
}
