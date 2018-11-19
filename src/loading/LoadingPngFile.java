package loading;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class LoadingPngFile {
	private BufferedImage bufferedIamge;
	
	public LoadingPngFile(String path) {
		parseFile(path);
	}

	private void parseFile(String path) {
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			
			bufferedIamge = new BufferedImage(image.getWidth(), 
					image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			bufferedIamge.getGraphics().drawImage(image, 0, 0, null);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Image getImageByJsonObject(JSONObject jsonObj) {
    	int width = Integer.parseInt(jsonObj.get("width").toString());
    	int height = Integer.parseInt(jsonObj.get("height").toString());
    	int x = Integer.parseInt(jsonObj.get("x").toString());
    	int y = Integer.parseInt(jsonObj.get("y").toString());
    	
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
    	image.getGraphics().drawImage(bufferedIamge, -x, -y, null);
    	
    	Image fxImage = SwingFXUtils.toFXImage(image, null);
    	return fxImage;
	}
}
