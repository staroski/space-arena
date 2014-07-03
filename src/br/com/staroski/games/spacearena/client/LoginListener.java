package br.com.staroski.games.spacearena.client;

public interface LoginListener {

	boolean onLogin(String host, String instance, String username, String password);

	void onCancel();
}
