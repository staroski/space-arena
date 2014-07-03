package br.com.staroski.games.spacearena;

import static br.com.staroski.games.CGUtils.*;

import java.awt.image.*;

import br.com.staroski.games.*;

public abstract class SpaceArena implements Resource {

	public static final String GAME_NAME = "Space Arena";

	private static final long serialVersionUID = 1;

	public static final SpaceArena PLAYER_01 = new SpaceArena(1) {

		private static final long serialVersionUID = 1;

		@Override
		BufferedImage[] createImages() {
			BufferedImage image = loadBufferedImage("/player-01.png");
			return createRotatedImages(image, TURN_ANGLE);
		}

		@Override
		Shape[] createShapes() {
			Shape shape = new Shape();
			shape.addPoint(30, 21);
			shape.addPoint(26, 32);
			shape.addPoint(16, 38);
			shape.addPoint(2, 38);
			shape.addPoint(6, 30);
			shape.addPoint(4, 26);
			shape.addPoint(0, 24);
			shape.addPoint(0, 15);
			shape.addPoint(4, 13);
			shape.addPoint(6, 9);
			shape.addPoint(2, 1);
			shape.addPoint(16, 1);
			shape.addPoint(26, 7);
			shape.addPoint(30, 18);
			return createRotatedShapes(shape, TURN_ANGLE);
		}
	};

	public static final SpaceArena STAGE_01 = new SpaceArena(2) {

		private static final long serialVersionUID = 1;

		@Override
		BufferedImage[] createImages() {
			return new BufferedImage[] { loadBufferedImage("/stage-01.png") };
		}

		@Override
		Shape[] createShapes() {
			return new Shape[0];
		}
	};

	public static final SpaceArena BULLET_01 = new SpaceArena(3) {

		private static final long serialVersionUID = 1;

		@Override
		BufferedImage[] createImages() {
			BufferedImage image = loadBufferedImage("/bullet-01.png");
			return createRotatedImages(image, TURN_ANGLE);
		}

		@Override
		Shape[] createShapes() {
			Shape shape = new Shape();
			shape.addPoint(25, 4);
			shape.addPoint(25, 6);
			shape.addPoint(20, 9);
			shape.addPoint(10, 9);
			shape.addPoint(1, 5);
			shape.addPoint(10, 1);
			shape.addPoint(20, 1);
			return createRotatedShapes(shape, TURN_ANGLE);
		}
	};

	public static final SpaceArena ENERGY_ITEM_01 = new SpaceArena(4) {

		private static final long serialVersionUID = 1;

		@Override
		BufferedImage[] createImages() {
			BufferedImage image = loadBufferedImage("/energy-item-01.png");
			return createRotatedImages(image, TURN_ANGLE);
		}

		@Override
		Shape[] createShapes() {
			Shape shape = new Shape();
			shape.addPoint(16, 0);
			shape.addPoint(13, 9);
			shape.addPoint(18, 14);
			shape.addPoint(1, 28);
			shape.addPoint(9, 16);
			shape.addPoint(3, 11);
			return createRotatedShapes(shape, TURN_ANGLE);
		}
	};

	private static final int TURN_ANGLE = 1;

	private static int getFrame(double direction) {
		return (int) direction / TURN_ANGLE;
	}

	private transient BufferedImage[] images;
	private transient Shape[] shapes;

	private final int id;

	private SpaceArena(int id) {
		this.id = id;
		images = createImages();
		shapes = createShapes();
	}

	@Override
	public int getId() {
		return id;
	}

	public BufferedImage getImage(double direction) {
		if (images == null) {
			images = createImages();
		}
		return images[getFrame(direction)];
	}

	public Shape getShape(double direction) {
		if (shapes == null) {
			shapes = createShapes();
		}
		return shapes[getFrame(direction)];
	}

	abstract BufferedImage[] createImages();

	abstract Shape[] createShapes();
}
