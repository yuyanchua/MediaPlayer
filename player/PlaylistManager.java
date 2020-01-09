package player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.stage.Stage;
import utility.*;

public class PlaylistManager {
	//Startup 
	//Read file
	//Retrieve list if file found
	//Blank list if file is not found
	//utility.Media player read list
	//Save list when end
	
	private String listName;
	private List<utility.Media> mediaList;
	private List<String> mediaNameList;
	private List<javafx.scene.media.Media> playlist;
	private String defURL ="C:\\Users\\Hp\\neo_workspace\\MediaPlayer\\res\\test.json";
	private utility.Media addedMedia;
	
	public PlaylistManager() {
		startup();
	}
	
	private void startup() {
		ReadJSON read = new ReadJSON();
		read.readFile(defURL);
		listName = read.getPlaylist();
		mediaList = read.getMediaList();
		extractMediaName();
		retrievePlaylist();
	}
	
	private void extractMediaName() {
		List<String> list = new ArrayList<>();
		for(utility.Media media : mediaList) {
			String mediaName = media.getName();
			list.add(mediaName);
		}
		this.mediaNameList = list;
	}
	
	private void retrievePlaylist() {
		List<javafx.scene.media.Media> playlist = new ArrayList<>();
		for(utility.Media media : mediaList) {
			String url = media.getUrl();
			javafx.scene.media.Media mediaFile = 
					new javafx.scene.media.Media(
							new File(url).toURI().toString());
			playlist.add(mediaFile);
			
		}
		this.playlist = playlist;
	}
	
	public void addMedia(Stage stage) {
		Chooser choose = new Chooser();
		utility.Media media = choose.chooseMediaFile(stage);
		if(media != null)
			mediaList.add(media);
		this.addedMedia = media;
	}
	
	public javafx.scene.media.Media getAddedMedia() {
		String url = addedMedia.getUrl();
		javafx.scene.media.Media mediaFile = 
				new javafx.scene.media.Media(
						new File(url).toURI().toString());
		
		return mediaFile;
	}
	
	public String getAddedMediaName() {
		return addedMedia.getName();
	}
	
	public void editName(String listName) {
		this.listName = listName;
	}
	
	public void editList(List<utility.Media> mediaList) {
		this.mediaList = mediaList;
	}
	
	public void saveChange() {
		WriteJSON write = new WriteJSON(defURL);
		try {
			write.writeFile(listName, mediaList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getListName() {
		return this.listName;
	}
	
	public List<String> getList(){
		
		return this.mediaNameList; 	
	}
	
	
	public List<utility.Media> getMediaList() {
		return this.mediaList;
	}
	
	public List<javafx.scene.media.Media> getPlayList(){
		return this.playlist;
	}
	
}
