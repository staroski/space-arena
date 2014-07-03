package br.com.staroski.games.spacearena.client;

import javax.swing.*;

public final class StartupClient {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			SpaceArenaClient spaceArena = SpaceArenaClient.get();
			spaceArena.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private StartupClient() {
	}
}
