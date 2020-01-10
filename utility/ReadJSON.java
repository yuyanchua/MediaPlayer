//read for one playlist

package utility;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.*;

public class ReadJSON {
	private String playlist;
	private List<MediaDetail> mediaList;
	
	public ReadJSON() {}
	
	//Default filepath
	public void readFile(String filepath){
		File jsonFile = new File(filepath);
		retrieveData(jsonFile);
	}
	
	private void retrieveData(File jsonFile){
		try {
			Object obj = new JSONParser().parse(new FileReader(jsonFile));
			JSONObject jsonObj = (JSONObject) obj;
			playlist = (String) jsonObj.get("playlist");
			JSONArray jsonArr = (JSONArray) jsonObj.get("list");
			getMediaList(jsonArr);
		} catch (Exception ex) {
			//create blank playlist (default)
			playlist = null;
			mediaList = null;
//			ex.printStackTrace();
		}
		
	}
	
	private void getMediaList(JSONArray jsonArr) {
		mediaList = new ArrayList<>();
		
		for(int i = 0; i < jsonArr.size(); i ++) {
			JSONObject jsonmedia = (JSONObject) jsonArr.get(i);
			String name = (String) jsonmedia.get("name");
			String url = (String) jsonmedia.get("url");
			
			mediaList.add(new MediaDetail(name, url));
			
		}
	}
	
	public String getPlaylist() {
		return this.playlist;
	}
	
	public List<MediaDetail> getMediaList(){
		return this.mediaList;
	}
	
}
