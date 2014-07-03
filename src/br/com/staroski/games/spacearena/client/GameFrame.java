package br.com.staroski.games.spacearena.client;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import br.com.staroski.games.*;
import br.com.staroski.games.spacearena.*;

final class GameFrame extends JFrame {

	private static final long serialVersionUID = 1;

	private final GameSettings settings;

	// cada monitor tem sua propria configuração grafica
	private final GraphicsDevice[] devices;
	private final VolatileImage[] offscreens;

	private final KeyListener keyListener = new KeyAdapter() {

		@Override
		public void keyPressed(final KeyEvent e) {
			GameInput.get().pressKey(e.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			GameInput.get().releaseKey(e.getKeyCode());
		}
	};

	private static String merge(String gameName, String playerName) {
		playerName = playerName.trim();
		if (!playerName.isEmpty()) {
			return gameName + " - " + playerName;
		}
		return gameName;
	}

	public GameFrame(String playerName, GameSettings settings) {
		super(merge(settings.getName(), playerName));
		this.settings = settings;
		final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		devices = environment.getScreenDevices();
		this.offscreens = createOffscreenImages(devices);

		new Presets().execute();

		initGUI();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		createBufferStrategy(2);
	}

	public Graphics2D getDrawBuffer() {
		return offscreens[getCurrentDevice()].createGraphics();
	}

	@Override
	public Dimension getPreferredSize() {
		Insets insets = getInsets();
		return new Dimension(insets.left + settings.getWidth() + insets.right, insets.top + settings.getHeigth() + insets.bottom);
	}

	public void showDrawBuffer() {
		BufferStrategy drawBuffer = getBufferStrategy();
		if (drawBuffer == null) {
			return;
		}
		Graphics2D graphics = (Graphics2D) drawBuffer.getDrawGraphics();
		Insets insets = getInsets();
		graphics.drawImage(offscreens[getCurrentDevice()], insets.left, insets.top, null);
		graphics.dispose();
		drawBuffer.show();
	}

	private VolatileImage[] createOffscreenImages(GraphicsDevice[] devices) {
		VolatileImage[] offScreens = new VolatileImage[devices.length];
		for (int i = 0; i < devices.length; i++) {
			GraphicsConfiguration config = devices[i].getDefaultConfiguration();
			offScreens[i] = config.createCompatibleVolatileImage(settings.getWidth(), settings.getHeigth());
		}
		return offScreens;
	}

	private int getCurrentDevice() {
		GraphicsDevice device = getGraphicsConfiguration().getDevice();
		for (int i = 0; i < devices.length; i++) {
			if (device == devices[i]) {
				return i;
			}
		}
		return 0;
	}

	private void initGUI() {
		this.setIconImage(SpaceArena.PLAYER_01.getImage(0));
		this.setLayout(new BorderLayout());
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addKeyListener(keyListener);
	}
}
