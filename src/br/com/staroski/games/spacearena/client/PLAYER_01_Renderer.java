package br.com.staroski.games.spacearena.client;

import static br.com.staroski.games.CGUtils.*;

import java.awt.*;
import java.awt.image.*;
import java.rmi.*;

import br.com.staroski.games.*;
import br.com.staroski.games.Point;
import br.com.staroski.games.Shape;
import br.com.staroski.games.spacearena.*;

final class PLAYER_01_Renderer implements Renderer {

	@Override
	public void render(Renderizable renderizable, Graphics2D g) throws RemoteException {
		Player player = (Player) renderizable;
		double direction = player.getDirection();
		BufferedImage image = SpaceArena.PLAYER_01.getImage(direction);
		Shape shape = player.getShape();

		Point p = getDrawPoint(image, shape);
		int x = (int) (p.x + 0.5);
		int y = (int) (p.y + 0.5);

		// image
		if (Debugger.showImages) {
			g.drawImage(image, x, y, null);
		}

		// image area
		if (Debugger.showImagesBox) {
			g.setColor(Color.GREEN);
			g.drawRect(x, y, image.getWidth(), image.getHeight());
		}

		// shape
		if (Debugger.showShapes) {
			g.setColor(Color.YELLOW);
			shape.draw(g);
			Point c = shape.getCenter();
			g.drawRect((int) (c.x + 0.5), (int) (c.y + 0.5), 1, 1);
		}

		// bound box
		if (Debugger.showShapesBox) {
			Rectangle r = shape.getBounds().toAwtRectangle();
			g.setColor(Color.RED);
			g.drawRect(r.x, r.y, r.width, r.height);
		}
	}
}
