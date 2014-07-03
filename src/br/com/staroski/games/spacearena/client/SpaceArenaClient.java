package br.com.staroski.games.spacearena.client;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.rmi.*;

import javax.swing.*;

import br.com.staroski.games.*;
import br.com.staroski.games.Renderer;

public final class SpaceArenaClient implements GameClient {

	private static final class Holder {

		private static final SpaceArenaClient INSTANCE;

		static {
			try {
				INSTANCE = new SpaceArenaClient();
			} catch (RemoteException e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		}
	}

	public static SpaceArenaClient get() {
		return Holder.INSTANCE;
	}

	private GameSettings settings;
	private GameEngine engine;
	private GameFrame gameFrame;
	private Player player;
	private Screen screen;

	private final KeyListener keyListener = new KeyAdapter() {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				checkClose();
			}
		}
	};

	private final WindowListener windowListener = new WindowAdapter() {

		@Override
		public void windowClosing(WindowEvent we) {
			checkClose();
		}

	};

	static void setProxy(final String host, final String port, final String user, final String password) {
		try {
			Authenticator.setDefault(new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password.toCharArray());
				}
			});

			System.setProperty("http.proxyHost", host);
			System.setProperty("http.proxyPort", port);
			System.setProperty("http.proxyUser", user);
			System.setProperty("http.proxyPassword", password);
			System.out.println("proxy configurado com sucesso!");
		} catch (Exception e) {
			System.out.println("erro ao configurar proxy:");
			e.printStackTrace();
		}
	}

	private final LoginListener loginListener = new LoginListener() {

		@Override
		public void onCancel() {
			System.exit(0);// faz nada
		}

		public boolean onLogin(String host, String instance, String username, String password) {
			try {
				final GameClient client = SpaceArenaClient.this;
				ObjectBinder locator = ObjectBinder.get();
				engine = locator.lookupEngine(host, instance);

				// o client é 'bindeado' nesta maquina
				settings = engine.getSettings();
				locator.bindClient(ObjectBinder.getLocalHost(), settings, client);

				engine.addClient(client, username, password); // vai disparar o setPlayer
				screen = engine.getScreen();

				String playerName = getPlayer().getName().trim();
				gameFrame = new GameFrame(playerName, settings);
				gameFrame.addWindowListener(windowListener);
				gameFrame.addKeyListener(keyListener);
				gameFrame.setVisible(true);
				return true;
			} catch (RemoteException e) {
				e.printStackTrace();
				return false;
			}
		}
	};

	private final long id;

	private SpaceArenaClient() throws RemoteException {
		id = IdGenerator.newId();
	}

	@Override
	public long getId() throws RemoteException {
		return id;
	}

	@Override
	public void setPlayer(Player player) throws RemoteException {
		this.player = player;
	}

	@Override
	public Player getPlayer() throws RemoteException {
		return player;
	}

	@Override
	public Screen getScreen() throws RemoteException {
		return screen;
	}

	@Override
	public GameSettings getSettings() {
		return settings;
	}

	@Override
	public boolean isAccessible() {
		return true;
	}

	@Override
	public void onDraw() throws RemoteException {
		if (gameFrame == null) {
			return;
		}
		Graphics2D g = gameFrame.getDrawBuffer();

		// limpar a tela
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, settings.getWidth(), settings.getHeigth());

		Renderizable renderizable = getScreen();
		Renderer renderer = RendererMapper.get().get(renderizable.getRendererId());
		renderer.render(renderizable, g);

		// apresentar 
		gameFrame.showDrawBuffer();
	}

	@Override
	public void onInput() throws RemoteException {
		getPlayer().onInput(GameInput.get());
	}

	private void checkClose() {
		int opcao = JOptionPane.showConfirmDialog(gameFrame, "Deseja realmente sair?", "Confirmação", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (opcao == JOptionPane.OK_OPTION) {
			try {
				engine.removeClient(SpaceArenaClient.this);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.exit(0);
			}
		}
	}

	void execute() throws Exception {
		LoginFrame loginFrame = new LoginFrame(loginListener);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setVisible(true);
	}
}
