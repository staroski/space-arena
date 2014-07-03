package br.com.staroski.games.spacearena.server.rmi;

import static br.com.staroski.games.CGUtils.*;

import java.awt.event.*;
import java.rmi.*;
import java.util.*;

import br.com.staroski.games.*;
import br.com.staroski.games.spacearena.*;

class Player_01 extends AbstractPlayer {

	private static final double SHOOT_INTERVAL = 1 / 10.0; // 5 tiros por segundo

	private final Clock bulletTimer;

	private boolean accelerate;

	private boolean brake;
	private boolean turnRight;
	private boolean turnLeft;
	private boolean fire;

	Player_01(String name, long id) throws RemoteException {
		super(name, id);
		bulletTimer = new Clock();

		setMaxLife(100);
		setLife(100);

		// definir direção aleatoria
		setDirection(new Random().nextInt(360));

		// definir posição aleatoria pra jogar o item
		GameSettings settings = SpaceArenaServer.get().getSettings();
		int w = settings.getWidth();
		int h = settings.getHeigth();
		Bounds r = getBounds();
		double x = new Random().nextInt((int) (w - r.width)) + r.width;
		double y = new Random().nextInt((int) (h - r.height)) + r.height;
		setLocation(new Point(x, y));

		setTurnSpeed(180);

		setAcceleration(100);
		setMaxSpeed(500);
	}

	@Override
	public int getRendererId() {
		return SpaceArena.PLAYER_01.getId();
	}

	@Override
	public void onCollision(ServerObject other) throws RemoteException {
		if (other instanceof Bullet) {
			Bullet bullet = (Bullet) other;
			if (bullet.getOwner() == this) {
				return;
			}
			setLife(getLife() - bullet.getPower());
			if (getLife() == 0) {
				removeMyself();
			}
		}
		if (other instanceof EnergyItem) {
			EnergyItem item = (EnergyItem) other;
			setLife(getLife() + item.getEnergy());
		}
	}

	@Override
	public void onInput(GameInput keys) throws RemoteException {
		accelerate = keys.isKeyDown(KeyEvent.VK_UP) || keys.isKeyDown(KeyEvent.VK_W);
		brake = keys.isKeyDown(KeyEvent.VK_DOWN) || keys.isKeyDown(KeyEvent.VK_S);

		turnLeft = keys.isKeyDown(KeyEvent.VK_LEFT) || keys.isKeyDown(KeyEvent.VK_A);
		turnRight = keys.isKeyDown(KeyEvent.VK_RIGHT) || keys.isKeyDown(KeyEvent.VK_D);

		fire = keys.isKeyDown(KeyEvent.VK_CONTROL) || keys.isKeyDown(KeyEvent.VK_J);
	}

	@Override
	public void onUpdate(GameEvent evt) throws RemoteException {
		bulletTimer.tick(evt.time);

		checkTurn(evt);
		checkMove(evt);
		checkFire(evt);
	}

	private void checkFire(GameEvent evt) throws RemoteException {
		if (fire) {
			if (bulletTimer.hasElapsed(SHOOT_INTERVAL)) {
				bulletTimer.reset();
				Screen screen = SpaceArenaServer.get().getScreen();
				Bullet_01 bullet = new Bullet_01(IdGenerator.newId(), this);
				bullet.setPower(1);
				bullet.setRange(500);
				bullet.setSpeed(700);
				screen.addObject(bullet);
			}
		}
	}

	private void checkMove(GameEvent evt) throws RemoteException {
		double speed = getSpeed();
		double acceleration = getAcceleration();
		if (accelerate) {
			speed += acceleration * evt.time;
			if (speed > getMaxSpeed()) {
				speed = getMaxSpeed();
			}
		}
		if (brake) {
			speed -= acceleration * evt.time;
			if (speed < 0) {
				speed = 0;
			}
		}
		setSpeed(speed);
		if (speed > 0) {
			double distance = speed * evt.time;
			setLocation(movePoint(getLocation(), distance, getDirection()));
		}
	}

	private void checkTurn(GameEvent evt) throws RemoteException {
		if (turnLeft || turnRight) {
			double degrees = (getTurnSpeed() * evt.time);
			double direction = getDirection();
			// pra esquerda
			if (turnLeft) {
				direction -= degrees;
				if (direction < 0) {
					direction = 359;
				}
			} else if (turnRight) {
				direction += degrees;
				if (direction >= 360) {
					direction = 0;
				}
			}
			setDirection(direction);
		}
	}

	@Override
	protected Shape getShape(double direction) {
		return SpaceArena.PLAYER_01.getShape(direction);
	}
}
