package br.com.staroski.games.spacearena;

import java.awt.*;

public final class Fonts {

	public static final Font COURIER;

	static {
		try {
			COURIER = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/cour.ttf")).deriveFont(Font.BOLD, 20F);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}

	private Fonts() {
	}
}