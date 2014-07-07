space-arena
===========

A multiplayer game I developed as academic work

To start the game server run the following classes in the order they appear:

	CORBA server:
		br.com.staroski.games.spacearena.server.corba.ItemProviderServer

	RMI server:
		br.com.staroski.games.spacearena.server.rmi.StartupServer

To star the game client run the following class:

	br.com.staroski.games.spacearena.client.StartupClient

		On field "instancia", enter the value of the "instance" field showed by the RMI server


Tips:
	The game uses port 8008, if you have trouble accessing that port,
	change the value of constant PORT in class br.com.staroski.games.ObjectBinder
