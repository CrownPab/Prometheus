package prometheus.entity.boundedbox;

import javafx.geometry.Rectangle2D;

public class RectBoundedBox {

	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle2D boundary;
	
	/**
	 * Constructor for Rectangular bounding box
	 * @param x starting x location
	 * @param y starting y location
	 * @param w 
	 * @param h
	 */
	public RectBoundedBox(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		boundary = new Rectangle2D(this.x, this.y, width, height);
	}

	public Rectangle2D getBoundary() {
		return boundary;
	}

	public boolean checkCollision(RectBoundedBox b) {
		return b.getBoundary().intersects(getBoundary());
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		boundary = new Rectangle2D(x, y, width, height);
	}

}
