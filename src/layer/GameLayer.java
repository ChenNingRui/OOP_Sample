package layer;

import java.awt.Point;
import java.util.ArrayList;

import event.CreateBulletEvent;
import event.PlayerMoveEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import sprite.Bullet;
import sprite.Enemy;
import sprite.MoveSprite;
import sprite.Player;
import sprite.Sprite;

public class GameLayer implements Layer, PlayerMoveEvent, CreateBulletEvent {
	private GraphicsContext graphics;
	private Canvas canvas;
	private Game game;
	
	private int layerWidth;
	private int layerheight;
	
	private ArrayList<Sprite> renderList;
	
	private Player player;
	private double mouseX;
	private double mouseY;
	private boolean isMove;
	
	private ArrayList<Bullet> bulletList;
	private ArrayList<Enemy> enemyList;
	
	private int[] randomNumberList;
	private long lastUpdate;

	public GameLayer(Game game, int layerWidth, int layerheight) {
		this.game = game;
		init(layerWidth, layerheight);
	}
	
	private void init(int width, int height) {
		createLayer(width, height);
		createPlayer();
		bulletList = new ArrayList<Bullet>();
		enemyList = new ArrayList<Enemy>();
		
		randomNumberList = new int[3];
		lastUpdate = 0;
	}
	
	private void createPlayer() {
		player = new Player(200, 200, 0, 50, 50, game.getImageByObjectName("playerShip1_blue.png"), 10);
		this.addToLayer(player);
	}
	
	private void createBullet() {
		Bullet bullet = new Bullet(player.getX() + 40, player.getY(), 0, 50, 50, game.getImageByObjectName("laserBlue16.png"), 15);
		this.addToLayer(bullet);
		bulletList.add(bullet);
	}
	
	private void createEnemy(int x, int y) {
		Enemy enemy = new Enemy(x, y, 360, 50, 50, game.getImageByObjectName("enemyBlack5.png"), 3);
		this.addToLayer(enemy);
		enemyList.add(enemy);
	}
	
	private void removeBullet(Sprite bullet) {
		bulletList.remove(bullet);
		renderList.remove(bullet);
	}
	
	private void removeEnemy(Sprite enemy) {
		enemyList.remove(enemy);
		renderList.remove(enemy);
	}
	
	private void rotateImage(Sprite sprite) {
		if(sprite.getImage() != null && sprite.getRotation() != 0 && sprite.isRotate()) {
			sprite.setRotate(false);
			ImageView imageView = new ImageView(sprite.getImage());
			imageView.setRotate(sprite.getRotation());
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			Image rotatedImage = imageView.snapshot(params, null);
			sprite.setImage(rotatedImage);
		}
	}
	
	private void spriteMoveToPoint(MoveSprite sprite, int x, int y) {
		if (sprite.getX() < x)
		{
			sprite.setX(player.getX() + sprite.getVelocity());
			if (sprite.getX() > x)
				sprite.setX((int) x);
		}
		else if (sprite.getX() > x)
		{
			sprite.setX(sprite.getX() - sprite.getVelocity());
			if (sprite.getX() < x)
				sprite.setX((int) x);
		}
		
		if (sprite.getY() < y)
		{
			sprite.setY(sprite.getY() + sprite.getVelocity());
			if (sprite.getY() > y)
				sprite.setY((int) y);
		}
		else if (sprite.getY() > y)
		{
			sprite.setY(sprite.getY() - sprite.getVelocity());
			if (sprite.getY() < y)
				sprite.setY((int) y);
		}
	}
	
	private void timeDelay(int millisecond) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastUpdate >= millisecond) {
			randomNumberList();
			
			for(int i = 0, length = randomNumberList.length; i < length; i ++) {
				createEnemy(randomNumberList[i], randomNumberList[i] * -1);
			}
		
			lastUpdate = currentTime ;
        }
	}
	
	private void randomNumberList() {
		for(int i = 0, length = randomNumberList.length; i < length; i++) {
			int position = (int)(Math.random() * layerWidth - 100) + 50;
			randomNumberList[i] = position;
		}
	}
	
	private void collisionCheckForBulletAndEnemy(Sprite enemy, Sprite bullet) {
		if(enemy != null || bullet != null){
			int x = (enemy.getX() + bullet.getWidth() / 2);
			int y = (enemy.getY() + bullet.getHeight() / 2);
			Point p1 = new Point(x, y);
	        if (getDistance(p1, bullet.getX(), bullet.getY()) < 50) {
	        	removeEnemy(enemy);
	        	removeBullet(bullet);
	        }
		}
	}
	
    private double getDistance(Point p, double ox, double oy) {
        double _x = Math.abs(ox - p.x);
        double _y = Math.abs(oy - p.y);
        return Math.sqrt(_x * _x + _y * _y);
    }
	
	@Override
	public void createLayer(int layerWidth, int layerheight) {
		this.layerWidth = layerWidth;
		this.layerheight = layerheight;
		
		canvas = new Canvas(layerWidth, layerheight);
		renderList = new ArrayList<Sprite>();
		graphics = canvas.getGraphicsContext2D();
		game.addToScreen(canvas);
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		graphics.clearRect(0, 0, layerWidth, layerheight);
		
		for(int i = 0, length = renderList.size(); i < length; i++) {
			Sprite sprite = renderList.get(i);
			rotateImage(sprite);
			graphics.drawImage(sprite.getImage(), sprite.getX(), sprite.getY());
		}
		
		timeDelay(3000);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(isMove) {
			spriteMoveToPoint(player, (int) mouseX, (int) mouseY);
		}
		
		//bullet
		for(int i = 0, length = bulletList.size(); i < length; i++) {
			MoveSprite bullet = bulletList.get(i);
			spriteMoveToPoint(bullet, bullet.getX(), bullet.getY() - bullet.getVelocity());
		}
		
		for(int i = 0, length = bulletList.size(); i < length; i++) {
			MoveSprite bullet = bulletList.get(i);
				if(bullet.getY() < 10) {
					removeBullet(bullet);
				}
		}
		
		//enemy
		for(int i = 0, length = enemyList.size(); i < length; i++) {
			MoveSprite enemy = enemyList.get(i);
			spriteMoveToPoint(enemy, enemy.getX(), enemy.getY() + enemy.getVelocity());
		}
		
		for(int i = 0, length = enemyList.size(); i < length; i++) {
			MoveSprite enemy = enemyList.get(i);
			if(enemy.getY() > layerheight) {
				removeEnemy(enemy);
			}
		}
		
		for(int i = 0, length = enemyList.size(); i < length; i++) {
			Enemy enemy = enemyList.get(i);
			for(int j = 0, size = bulletList.size(); j < size; j++) {
				Bullet bullet = bulletList.get(j);
				collisionCheckForBulletAndEnemy(enemy, bullet);
			}
		}
	}

	@Override
	public void addToLayer(Sprite sprite) {
		// TODO Auto-generated method stub
		renderList.add(sprite);
	}

	@Override
	public void removeFromLayer(Sprite sprite) {
		// TODO Auto-generated method stub
		renderList.remove(sprite);
	}

	@Override
	public void onPlayerMoveEvent(double x, double y, boolean isMove) {
		// TODO Auto-generated method stub
		mouseX = x;
		mouseY = y;
		this.isMove = isMove;
	}

	@Override
	public void onCreateBulletEvent() {
		// TODO Auto-generated method stub
		createBullet();
	}
}
