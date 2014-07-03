package br.com.staroski.games;

public class Clock {

	private double time;

	public void reset() {
		time = 0;
	}

	public void tick(double seconds) {
		this.time += seconds;
	}

	public boolean hasElapsed(double seconds) {
		return time >= seconds;
	}
}
