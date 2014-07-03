package br.com.staroski.games.spacearena.server.rmi;

import java.rmi.*;
import java.util.*;

import br.com.staroski.games.*;
import br.com.staroski.games.spacearena.*;

final class EnergyItem_01 extends AbstractServerObject implements EnergyItem {

	private final Clock lifeTime;

	private boolean turnLeft;
	private final int energy;

	EnergyItem_01(long id, int energy) throws RemoteException {
		super(id);

		setDirection(new Random().nextInt(360));

		this.energy = energy;
		lifeTime = new Clock();

		setTurnSpeed(3.6 * energy); // quanto mais energia, mais rapido gira, com 100 de energia, da 1 volta por segundo

		turnLeft = new Random().nextBoolean();
	}

	@Override
	public int getRendererId() throws RemoteException {
		return SpaceArena.ENERGY_ITEM_01.getId();
	}

	@Override
	public int getEnergy() throws RemoteException {
		return energy;
	}

	@Override
	protected Shape getShape(double direction) {
		return SpaceArena.ENERGY_ITEM_01.getShape(direction);
	}

	@Override
	public void onCollision(ServerObject other) throws RemoteException {
		removeMyself();
	}

	@Override
	public void onInput(GameInput input) throws RemoteException {
		// faz nada
	}

	@Override
	public void onUpdate(GameEvent event) throws RemoteException {
		lifeTime.tick(event.time);
		if (lifeTime.hasElapsed(30)) {
			lifeTime.reset();
			removeMyself();
		}
		turn(event);
	}

	private void turn(GameEvent evt) throws RemoteException {
		double degrees = (getTurnSpeed() * evt.time);
		double direction = getDirection();
		// pra esquerda
		if (turnLeft) {
			direction -= degrees;
			if (direction < 0) {
				direction = 359;
			}
		} else {
			direction += degrees;
			if (direction >= 360) {
				direction = 0;
			}
		}
		setDirection(direction);
		
	}
}
