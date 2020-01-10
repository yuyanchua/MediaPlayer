package player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.scene.media.Media;
import javafx.stage.Stage;
//import player.PlaylistWindow.MediaInfo;
import utility.*;


public class PlaylistManager {
	//Startup 
	//Read file
	//Retrieve list if file found
	//Blank list if file is not found
	//Media player read list
	//Save list when end
	
	private String listName;
	private List<MediaDetail> mediaList;
	private List<String> mediaNameList;
	private List<String> mediaUrlList;
	
	private List<Media> playlist;
//	private String defURL ="C:\\Users\\Hp\\neo_workspace\\MediaPlayer\\res\\test.json";
	private String defURL = "res/test.json";
	private MediaDetail addedMedia;
//	private List<MediaInfo> infoList;
	
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
//		convertList();
	}
	
	private void extractMediaName() {
		List<String> list = new ArrayList<>();
		for(MediaDetail media : mediaList) {
			String mediaName = media.getName();
			list.add(mediaName);
		}
		this.mediaNameList = list;
	}
	
	private void retrievePlaylist() {
		List<Media> playlist = new ArrayList<>();
		mediaUrlList = new ArrayList<>();
		for(MediaDetail media : mediaList) {
			String url = media.getUrl();
			mediaUrlList.add(url);
			Media mediaFile = 
					new Media(
							new File(url).toURI().toString());
			playlist.add(mediaFile);
			
		}
		this.playlist = playlist;
	}
	
	public void addMedia(Stage stage) {
		Chooser choose = new Chooser();
		MediaDetail media = choose.chooseMediaFile(stage);
		if(media != null)
			mediaList.add(media);
		this.addedMedia = media;
	}
	
	public void deleteMedia(int index) {
		mediaList.remove(index);
	}
	
	public Media getAddedMedia() {
		String url = addedMedia.getUrl();
		Media mediaFile = 
				new Media(
						new File(url).toURI().toString());
		
		return mediaFile;
	}
	
	public String getAddedMediaName() {
		return addedMedia.getName();
	}
	
	public String getAddedMediaUrl() {
		return addedMedia.getUrl();
	}
	
	public void editName(String listName) {
		this.listName = listName;
	}
	
	public void editList(List<MediaDetail> mediaList) {
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
	
	
	public List<MediaDetail> getMediaList() {
		return this.mediaList;
	}
	
	public List<Media> getPlayList(){
		return this.playlist;
	}
	
//	public List<MediaInfo> getInfoList(){
//		return this.infoList;
//	}
	
	public List<String> getMediaUrlList(){
		return this.mediaUrlList;
	}
	
}
