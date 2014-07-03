package br.com.staroski.games.spacearena.server.rmi;

import static br.com.staroski.games.CGUtils.*;

import java.rmi.*;

import br.com.staroski.games.*;
import br.com.staroski.games.spacearena.*;

final class Bullet_01 extends AbstractServerObject implements Bullet {

	private final ServerObject owner;
	private Point origin;
	private double range;
	private double power;
	private double distanceMoved;

	protected Bullet_01(long id, ServerObject owner) throws RemoteException {
		super(id);
		this.owner = owner;

		setDirection(owner.getDirection());
		Point p = owner.getLocation();
		origin = new Point(p);
		setLocation(new Point(p));
	}

	@Override
	public ServerObject getOwner() throws RemoteException {
		return owner;
	}

	@Override
	public double getPower() throws RemoteException {
		return power;
	}

	@Override
	public double getRange() throws RemoteException {
		return range;
	}

	@Override
	public int getRendererId() throws RemoteException {
		return SpaceArena.BULLET_01.getId();
	}

	@Override
	public void onCollision(ServerObject other) throws RemoteException {
		if (other == owner) {
			return;
		}
		if (other instanceof Player) {
			removeMyself();
		}
	}

	@Override
	public void onInput(GameInput input) throws RemoteException {
		//faz nada
	}

	@Override
	public void onUpdate(GameEvent evt) throws RemoteException {
		Point location = getLocation();
		distanceMoved = getDistance(origin, location);
		if (distanceMoved < range) {
			double distance = getSpeed() * evt.time;
			setLocation(movePoint(location, distance, getDirection()));
		} else {
			removeMyself();
		}
	}

	@Override
	public void setOwner(ServerObject object) throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPower(double power) throws RemoteException {
		this.power = power;
	}

	@Override
	public void setRange(double range) throws RemoteException {
		this.range = range;
	}

	@Override
	protected Shape checkBounds(Shape shape) throws RemoteException {
		return shape;
	}

	@Override
	protected Shape getShape(double direction) {
		return SpaceArena.BULLET_01.getShape(direction);
	}
}
