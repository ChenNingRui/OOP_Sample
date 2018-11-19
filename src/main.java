import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import layer.Game;

public class main extends Application
{
	private static Game game;
	
	public static void main(String[] arg) throws Exception {
		initialize();
		Application.launch(arg);
	}
	
	private static void initialize() {
		game = new Game();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		game.createStage(primaryStage);
		
		new AnimationTimer()
	    {
			long lastUpdate = 0;
	        public void handle(long currentTime)
	        {
	        	 if (currentTime - lastUpdate >= 3800000) {
	        		 game.render();
	        		 lastUpdate = currentTime ;
                 }
	            //double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
	        	 game.update();;
	        }
	    }.start();
	    
	    primaryStage.show();
	}
}
