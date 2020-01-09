package player;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//Table
//Able to do some editing £¨add, delete)
//Communicate with the media player
//only need to send the media url, or just media?

public class PlaylistWindow {
	
	private EdittedMediaPlayer editmp;
	private PlaylistManager playMag;
	
	private Button btNext, btPrev, btDel, btAdd, btSave;

	private List<Media> playlist;
	private List<String> mediaNameList;
	private List<MediaPlayer> playerList;
	private List<MediaControl> controlList;
	
	private TableView <String> mediaTable;
	private ObservableList<String> record;
	
	
	private int index;
	
	
	
	public PlaylistWindow() {
//		this.editmp = editmp;
		
		playMag = new PlaylistManager();
		playlist = playMag.getPlayList();
		mediaNameList = playMag.getList();
		playerList = new ArrayList<>();
		controlList = new ArrayList<>();
		
		for(Media media : playlist) {
			MediaPlayer player = new MediaPlayer(media);
			MediaControl control = new MediaControl(player);
			playerList.add(player);
			controlList.add(control);
		}
				
	}
	
	private void initialise() {
		
	}
	
	private void initialiseTable() {
		mediaTable = new TableView<>();
		record = FXCollections.observableArrayList(mediaNameList);
		mediaTable.setEditable(false);
		
		
	}

	
}
