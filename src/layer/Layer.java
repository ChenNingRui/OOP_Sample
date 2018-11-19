package layer;

import sprite.Sprite;

public interface Layer {
	
	void createLayer(int width, int height);
	
	void render();
	
	void update();
	
	void addToLayer(Sprite sprite);
	
	void removeFromLayer(Sprite sprite);
}
