
package utility;

import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteJSON {
	private String dir ;
	
//	public WriteJSON() {}
	
	public WriteJSON(String dir) {
		this.dir = dir;
	}
	
	public void writeFile(String name, MediaDetail media) throws FileNotFoundException{
		List<MediaDetail> mediaList = new ArrayList<>();
		mediaList.add(media);
		writeFile(name,  mediaList);
	}
	
	@SuppressWarnings("unchecked")
	public void writeFile(String name, List<MediaDetail> mediaList) throws FileNotFoundException {
		JSONObject jsonList = new JSONObject();
		
		//Playlist 
		jsonList.put("playlist", name);
		
		JSONArray jsonArr = addMedia(mediaList);
		
		jsonList.put("list", jsonArr);
		
		PrintWriter pw = new PrintWriter(new File(dir));
		
		pw.write(jsonList.toJSONString());
		pw.flush();
		pw.close();
		
		
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray addMedia(List<MediaDetail> mediaList) {
		JSONArray jsonMediaArr = new JSONArray();
		
		for(MediaDetail media: mediaList) {
			JSONObject jsonMedia = new JSONObject();
			
			jsonMedia.put("name", media.getName());
			jsonMedia.put("url", media.getUrl());
			jsonMediaArr.add(jsonMedia);
		}
		
		
		return jsonMediaArr;
	}
}
