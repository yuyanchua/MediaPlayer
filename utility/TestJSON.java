package utility;

import java.io.FileNotFoundException;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestJSON extends Application{
	
	VBox vbox;
	BorderPane borderPane;
	Scene scene;
	Text text;
	Button btBrowse, btSave, btLoad;
	Stage stage;
	Media media;
	
	private String url = "C:\\Users\\Hp\\neo_workspace\\MediaPlayer\\res\\testNull.json";
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		initialise();
		
		borderPane = new BorderPane();
		scene = new Scene(borderPane, 500, 400);
		
		borderPane.setCenter(vbox);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Test Bug");
		primaryStage.sizeToScene();
		primaryStage.show(); 
	}
	
	private void initialise() {
		
		vbox = new VBox();
		vbox.setSpacing(5);
		
		text = new Text();
		FlowPane flow = new FlowPane();
		
		btBrowse = new Button("Browse");
		btSave = new Button("Save");
		btLoad = new Button("Load");
		
		flow.getChildren().addAll(btBrowse, btSave, btLoad);
		
		vbox.getChildren().addAll(text, flow);
		
		btBrowse.setOnAction(e -> {
			Chooser choose = new Chooser();
			media = choose.chooseMediaFile(stage);
			if(media != null)
				text.setText(media.toString());
		});
		
		btSave.setOnAction(e -> savetoJson(media));
		
		btLoad.setOnAction(e -> readJSON());
		
		
	}
	
	private void savetoJson(Media media) {
		String name = " Test PlayList";
		try {
			WriteJSON write = new WriteJSON(url);
			write.writeFile(name, media);
		}catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	private void readJSON() {
		ReadJSON read = new ReadJSON();
		read.readFile(url);
		String playlistName = read.getPlaylist();
		List<Media> mediaList = read.getMediaList();
		printJSON(playlistName, mediaList);
	}
	
	private void printJSON(String playlistName, List<Media> mediaList) {
		System.out.printf("Playlist Name: %s\n", playlistName);
		try {
		for(Media media: mediaList) {
			System.out.printf("Media Name: %s\n", media.getName());
			System.out.printf("Media Url: %s\n\n", media.getUrl());
		}
		}catch(NullPointerException ex) {
			System.out.printf("Media Name: %s\n", "null");
			System.out.printf("Media Url: %s\n\n", "null");
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
