package br.com.staroski.games.spacearena.client;

import java.io.*;
import java.util.*;

final class PropertiesFile {

	public static PropertiesFile load() throws IOException {
		return new PropertiesFile();
	}

	private static File getFile() throws IOException {
		String path = System.getProperty("user.home");
		File file = new File(path, "space-arena.properties");
		file.createNewFile();
		return file;
	}

	private String proxyHost;
	private String proxyPort;
	private String proxyUsername;
	private String proxyPassword;

	private String host;
	private String instance;
	private String username;
	private String password;

	private PropertiesFile() throws IOException {
		InputStream input = new FileInputStream(getFile());
		Properties prop = new Properties();
		prop.load(input);
		proxyHost = prop.getProperty("proxyHost");
		proxyPort = prop.getProperty("proxyPort");
		proxyUsername = prop.getProperty("proxyUsername");
		proxyPassword = prop.getProperty("proxyPassword");
		host = prop.getProperty("host");
		instance = prop.getProperty("instance");
		username = prop.getProperty("username");
		password = prop.getProperty("password");
		input.close();
	}

	public String getHost() {
		return host;
	}

	public String getInstance() {
		return instance;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void save() throws IOException {
		OutputStream output = new FileOutputStream(getFile());
		Properties prop = new Properties();

		prop.setProperty("proxyHost", proxyHost);
		prop.setProperty("proxyPort", proxyPort);
		prop.setProperty("proxyUsername", proxyUsername);
		prop.setProperty("proxyPassword", proxyPassword);

		prop.setProperty("host", host);
		prop.setProperty("instance", instance);
		prop.setProperty("username", username);
		prop.setProperty("password", password);

		prop.store(output, "Space Arena Properties File");
		output.flush();
		output.close();
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUsername() {
		return proxyUsername;
	}

	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

}
