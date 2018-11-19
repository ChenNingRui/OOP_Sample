package sprite;

import javafx.scene.image.Image;

public abstract class MoveSprite extends Sprite {
	private int velocity;
	
	
	public MoveSprite(int x, int y, int rotation, int width, int height, Image image, int velocity) {
		setX(x);
		setY(y);
		setSize(width, height);
		setImage(image);
		setRotation(rotation);
		setVelocity(velocity);
	}
	
	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
}
