package br.com.staroski.games.spacearena.server.rmi;

import java.rmi.*;

import br.com.staroski.games.*;

abstract class AbstractPlayer extends AbstractServerObject implements Player {

	private final String name;
	private double life;
	private double maxLife;

	AbstractPlayer(String name, long id) {
		super(id);
		this.name = name;
	}

	@Override
	public double getLife() throws RemoteException {
		return life;
	}

	@Override
	public double getMaxLife() throws RemoteException {
		return maxLife;
	}

	@Override
	public final String getName() throws RemoteException {
		return name;
	}

	@Override
	public void setLife(double life) throws RemoteException {
		this.life = life < 0 ? 0 : life > maxLife ? maxLife : life;
	}

	@Override
	public void setMaxLife(double maxLife) throws RemoteException {
		this.maxLife = maxLife;
	}

}
