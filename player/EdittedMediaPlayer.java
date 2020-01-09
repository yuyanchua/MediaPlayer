package player;

//End to next media
//Create window for the playlist

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import utility.*;

public class EdittedMediaPlayer extends Application{
	
//Player == Control
	
//	private String mediaUrl = new File("C:\\Users\\Hp\\neo_workspace\\MediaPlayer\\res\\konosuba1.mp4").getAbsolutePath();
//	private String mediaUrl = new File("res/konosuba1.mp4").getAbsolutePath();	
	private List<Media> playlist;
	private List<String> mediaNameList;
	private List<MediaPlayer> playerList;
	private List<MediaControl> controlList;
	private ComboBox<String> mediaBox;
	private ObservableList<String> items;
	private int index;
	private BorderPane borderPane;
	private Stage stage;
	private HBox controlBar;
	private PlaylistManager playMag;
	
	
	public EdittedMediaPlayer() {
		index = 0;
		
		playMag  = new PlaylistManager();
		playlist = playMag.getPlayList();
		mediaNameList = playMag.getList();
		playerList = new ArrayList<>();
		controlList = new ArrayList<>();
//		int cnt = 0;
		for(Media media : playlist) {
//			System.out.printf("Media Name: %s\nMedia Height: %d\nMedia Width: %d\n\n", 
//					mediaNameList.get(cnt++), media.getHeight(), media.getWidth());
			MediaPlayer player = new MediaPlayer(media);
			MediaControl control = new MediaControl(player);
			playerList.add(player);
			controlList.add(control);
		}
	}
	
	private void initializeControl() {
		mediaBox = new ComboBox<>();
		items = FXCollections.observableArrayList(mediaNameList);
		
		mediaBox.getItems().addAll(mediaNameList);
		mediaBox.setValue(items.get(index));
			
		Button btNext = new Button(">>");
		Button btPrev = new Button("<<");
		Button btAdd = new Button("Add");

		Button btSave = new Button("Save");
		Label notify = new Label("Save Changes");
		
		notify.setVisible(false);
		controlBar = new HBox();
		controlBar.getChildren().addAll(btPrev, mediaBox,
				btNext, btAdd, btSave, notify);
		controlBar.setAlignment(Pos.CENTER);
		controlBar.setSpacing(10);
	
		mediaBox.setOnAction(e ->{
//			playerList.get(index).stop();
//			index = items.indexOf(mediaBox.getValue());
//			mediaBox.setValue(items.get(index));
//			borderPane.setCenter(controlList.get(index));
//			playerList.get(index).play();
			changeMedia("select");
		});
		
		btNext.setOnAction(e->{
//			if(index < playlist.size() - 1) {
//				playerList.get(index).stop();
//				index ++;
//				mediaBox.setValue(items.get(index));
//				borderPane.setCenter(controlList.get(index));
//				playerList.get(index).play();
//			}
			changeMedia("next");
		});
		
		btPrev.setOnAction(e ->{
//			if(index > 0) {
//				playerList.get(index).stop();
//				index --;
//				mediaBox.setValue(items.get(index));
//				borderPane.setCenter(controlList.get(index));
//				playerList.get(index).play();
//			}
			changeMedia("prev");
		});
		
		btAdd.setOnAction(e -> {
			addNewMedia();
			notify.setVisible(false);
		});
//			playMag.addMedia(stage);
//			playlist.add(playMag.getAddedMedia());
//			mediaNameList.add(playMag.getAddedMediaName());
//			MediaPlayer player = new MediaPlayer(playMag.getAddedMedia());
//			MediaControl control = new MediaControl(player);
//			playerList.add(player);
//			controlList.add(control);
//			mediaBox.getItems().add(playMag.getAddedMediaName());
//			items.add(playMag.getAddedMediaName());
//			addNewMedia();
//		});
		
		btSave.setOnAction(e ->{
			playMag.saveChange();
			notify.setVisible(true);
		});
		
		playerList.get(index).setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
//				playerList.get(index).stop();
//				if(index < playlist.size() -1) {
//					index ++;
//					mediaBox.setValue(items.get(index));
//					borderPane.setCenter(controlList.get(index));
//					playerList.get(index).play();
//				}
				changeMedia("end");
			}
		});
		
		
	}
	
	private void addNewMedia() {
		try {
			playMag.addMedia(stage);
			
			Media newMedia = playMag.getAddedMedia();
			String newMediaName = playMag.getAddedMediaName();
			
			MediaPlayer player = new MediaPlayer(newMedia);
			MediaControl control = new MediaControl(player);
			
			playlist.add(newMedia);
			mediaNameList.add(newMediaName);
			playerList.add(player);
			controlList.add(control);
			mediaBox.getItems().add(newMediaName);
			items.add(newMediaName);

		}catch(NullPointerException ex) {
			
		}
	}
	
	private void changeMedia(String cases) {
		int curr = index;
		if(cases.equalsIgnoreCase("next")) {
			if(index >= playlist.size() - 1)
				return;
			else {
				index ++;
			}
		}else if (cases.equalsIgnoreCase("prev")) {
			if(index <= 0) {
				return;
			}else {
				index --;
			}
		}else if (cases.equalsIgnoreCase("select")) {
			index = items.indexOf(mediaBox.getValue());
		}else if(cases.equalsIgnoreCase("end")) {
			playerList.get(index).stop();
			if(index >= playlist.size() - 1)
				return;
			else {
				index ++;
			}
		}
		playerList.get(curr).stop();
		mediaBox.setValue(items.get(index));
		borderPane.setCenter(controlList.get(index));
		playerList.get(index).play();
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		stage = primaryStage;
		
		stage.setTitle("Media Player");
		
		borderPane = new BorderPane();
		
//		ComboBox<String> playlist = new Combo
		
//		Group root = new Group();
		
		initializeControl();
		
		borderPane.setCenter(controlList.get(index));
		borderPane.setTop(controlBar);
		
		playerList.get(index).setAutoPlay(true);
		
		Scene scene = new Scene(borderPane, 1280, 760);
		
//		Media media = new Media(new File(mediaUrl).toURI().toString());//May change
//		MediaPlayer mediaPlayer = new MediaPlayer(media);
//		mediaPlayer.setAutoPlay(true);
//		
//		MediaControl mediaControl = new MediaControl(mediaPlayer);
//		scene.setRoot(mediaControl);

		
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setResizable(false);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
