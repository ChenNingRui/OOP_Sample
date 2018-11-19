package loading;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoadingJsonFile {
	private JSONArray jsonArr;
	
	public LoadingJsonFile(String filePath) {
		parseFile(filePath);
	}

	private void parseFile(String filePath) {
		Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObj = (JSONObject)obj;
		jsonArr = (JSONArray) jsonObj.get("SubTexture"); 
	}
	
	public JSONObject getJsonByTextureName(String name) {
		for(int i = 0; i < jsonArr.size(); i++)
		{
			JSONObject obj = (JSONObject) jsonArr.get(i);
			if(obj.containsValue(name)) {
				return obj;
			}
		}
		return null;
	}
}
