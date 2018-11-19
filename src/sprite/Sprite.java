package sprite;

import javafx.scene.image.Image;

public abstract class Sprite {
	private int width;
	private int height;
	private int x;
	private int y;
	private int rotation;
	private boolean rotate;
	private Image image;
	
	public Sprite() {
		rotate = true;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isRotate() {
		return rotate;
	}

	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
