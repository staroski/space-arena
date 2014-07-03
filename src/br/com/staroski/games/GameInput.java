package br.com.staroski.games;

import java.io.*;

public final class GameInput implements Serializable {

	private static final class Holder {

		static final GameInput INSTANCE = new GameInput();
	}

	private static final long serialVersionUID = 1;

	public static GameInput get() {
		return Holder.INSTANCE;
	}

	private final boolean[] keyUp = new boolean[256];
	private final boolean[] keyDown = new boolean[256];

	private boolean keyPressed = false;
	private boolean keyReleased = false;

	private GameInput() {
	}

	public synchronized boolean isAnyKeyDown() {
		return keyPressed;
	}

	public synchronized boolean isAnyKeyUp() {
		return keyReleased;
	}

	public synchronized boolean isKeyDown(final int key) {
		return keyDown[key];
	}

	public synchronized boolean isKeyUp(final int key) {
		return keyUp[key];
	}

	public synchronized void pressKey(final int code) {
		if (code > -1 && code < 256) {
			keyDown[code] = true;
			keyUp[code] = false;
			keyPressed = true;
			keyReleased = false;
		}
	}

	public synchronized void releaseKey(final int code) {
		if (code > -1 && code < 256) {
			keyUp[code] = true;
			keyDown[code] = false;
			keyPressed = false;
			keyReleased = true;
		}
	}
}