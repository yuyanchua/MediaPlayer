package player;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

//Table
//Able to do some editing £¨add, delete)
//Communicate with the media player
//only need to send the media url, or just media?
//Send url?

public class PlaylistWindow extends Application{
	
	private EdittedMediaPlayer editmp;
	private PlaylistManager playMag;
	
	private Button btNext, btPrev, btDel, btAdd, btSave;
	private HBox controlBox;
	
	private List<Media> playlist;
	private List<String> mediaNameList;
	private List<MediaPlayer> playerList;
	private List<MediaControl> controlList;
	private List<MediaInfo> infoList;
	
	private TableView <MediaInfo> mediaTable;
	private ObservableList<MediaInfo> record;
	
	private Stage stage;
	
//	class MediaInfo{
//		String mediaName;
//		int index;
//		
//		MediaInfo(int index, String mediaName){
//			this.index = index;
//			this.mediaName = mediaName;
//		}
//		
//		String getName() {
//			return mediaName;
//		}
//		
//		int getIndex() {
//			return this.index;
//		}
//		
//	}
	
	public PlaylistWindow() {
//		this.editmp = editmp;
		
		playMag = new PlaylistManager();
		playlist = playMag.getPlayList();
		mediaNameList = playMag.getList();
		playerList = new ArrayList<>();
		controlList = new ArrayList<>();
		infoList = playMag.getInfoList();
		
		for(Media media : playlist) {
			MediaPlayer player = new MediaPlayer(media);
			MediaControl control = new MediaControl(player);
			playerList.add(player);
			controlList.add(control);
		}
	}

	
	private void initialiseControl() {
		controlBox = new HBox();
		
		btAdd = new Button("Add");
		btDel = new Button("Delete");
		btSave = new Button("Save");
		
		controlBox.getChildren().addAll(btAdd, btDel, btSave);
		
	}
	
//	private void addNewMedia() {
//		try {
//			playMag.addMedia(stage);
//			
//			String newMediaName = playMag.getAddedMediaName();
//			String newMediaUrl = playMag.getAddedMediaUrl();
//			
//			
//		}catch(Exception ex) {
//			
//		}
//	}
	
	@SuppressWarnings("unchecked")
	private void initialiseTable() {
		mediaTable = new TableView<>();
		record = FXCollections.observableArrayList(infoList);
		mediaTable.setEditable(false);
		
		TableColumn<MediaInfo, Integer> index = new TableColumn<>("Index");
		index.setCellValueFactory(new PropertyValueFactory<MediaInfo, Integer>("index"));
		index.setMaxWidth(50);
		index.setMinWidth(50);
		
		TableColumn<MediaInfo, String> mediaName = new TableColumn<>("Name");
		mediaName.setCellValueFactory(new PropertyValueFactory<MediaInfo, String>("mediaName"));
//		mediaName.setMaxWidth(320);
		
		mediaTable.setItems(record);
		mediaTable.getColumns().addAll(index, mediaName);
		
		//Fixed the column size to the window
		//So that no unused space appear
		mediaTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		mediaTable.setPlaceholder(new Label("No rows to display"));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initialiseTable();
		initialiseControl();
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(mediaTable, controlBox);
		
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		Scene scene = new Scene(vbox, 400, 500);
		
		primaryStage.setTitle("Playlist");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setMinHeight(500);
		primaryStage.setMinWidth(400);
//		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
