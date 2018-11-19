package layer;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import event.CreateBulletEvent;
import event.PlayerMoveEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import loading.LoadingJsonFile;
import loading.LoadingPngFile;

public class Game{
	private LoadingJsonFile jsonFile;
	private LoadingPngFile pngFile;
	private Group root;
	
	private int width;
	private int heigth;
	private GameLayer gamelayer;
	
	private ArrayList<PlayerMoveEvent> playerMoveEventList;
	private ArrayList<CreateBulletEvent> createBulletEventList;
	
	public Game() {
		init();
		instantiation();
	}
	
	private void init() {
		root = new Group();
		
		width = 600;
		heigth = 600;
		
		jsonFile = new LoadingJsonFile("src/Spritesheet/sheet.json");
		pngFile = new LoadingPngFile("src/Spritesheet/sheet.png");
		
		playerMoveEventList = new ArrayList<PlayerMoveEvent>();
		createBulletEventList = new ArrayList<CreateBulletEvent>();
	}
	
	private void instantiation() {
		gamelayer = new GameLayer(this, width, heigth);
		playerMoveEventList.add(gamelayer);
		createBulletEventList.add(gamelayer);
	}
	
	private JSONObject getJsonByTextureName(String textureName) {
		return jsonFile.getJsonByTextureName(textureName);
	}
	
	private Image getImageByJsonObject(JSONObject jsonObj) {
		return pngFile.getImageByJsonObject(jsonObj);
	}
	
	private EventHandler<MouseEvent> mouseEventHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent mouseEvent) {
			if(mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
				for(PlayerMoveEvent e: playerMoveEventList) {
					e.onPlayerMoveEvent(mouseEvent.getX(), mouseEvent.getY(), true);
				}
			}
			else if(mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
				for(PlayerMoveEvent e: playerMoveEventList) {
					e.onPlayerMoveEvent(mouseEvent.getX(), mouseEvent.getY(), false);
				}
			}
		}
	};
	
	private EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent event) {
			if(event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.SPACE) {
				for(CreateBulletEvent e: createBulletEventList) {
					e.onCreateBulletEvent();
				}
			}
		}
	};
	
	public Image getImageByObjectName(String textureName) {
		return getImageByJsonObject(getJsonByTextureName(textureName));
	}
	
	public void addToScreen(Node e) {
		root.getChildren().add(e);
	}
	
	public void createStage(Stage primaryStage) throws Exception {
		primaryStage.setWidth(width);
		primaryStage.setHeight(heigth);
		primaryStage.setResizable(false);
        primaryStage.setTitle("OOP");
        
        primaryStage.setScene(new Scene(root));
        
		//mouse event
		primaryStage.getScene().setOnMousePressed(mouseEventHandler);
		primaryStage.getScene().setOnMouseReleased(mouseEventHandler);
		
		//key event
		primaryStage.getScene().setOnKeyPressed(keyEventHandler);
		primaryStage.getScene().setOnKeyReleased(keyEventHandler);
	}
	
	public void render() {
		gamelayer.render();
	}
	
	public void update() {
		gamelayer.update();
	}
}
