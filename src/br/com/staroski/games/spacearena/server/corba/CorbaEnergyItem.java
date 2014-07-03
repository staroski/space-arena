package br.com.staroski.games.spacearena.server.corba;

import java.util.*;

import br.com.staroski.games.*;

public class CorbaEnergyItem {

	public final int energy;
	public final long id;

	public CorbaEnergyItem() {
		energy = new Random().nextInt(91) + 10;
		id = IdGenerator.newId();
	}
}
