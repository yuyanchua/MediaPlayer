//Task
//Create playlist
//Choose file
//Search through all folder
package player;

import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class EmbeddedMediaPlayer extends Application {

	private int numberOfMedia = 8;
    private String mediaName[];
    private String mediaUrl[];
    private Media media[];
    private MediaPlayer mediaPlayer[];
    private MediaControl mediaControl[];
    private int index = 0;
    private Button btNext, btPrev;
    
    public EmbeddedMediaPlayer() {
    	mediaName = new String[numberOfMedia];
    	mediaUrl = new String [numberOfMedia];
    	media = new Media[numberOfMedia];
    	mediaPlayer = new MediaPlayer[numberOfMedia];
    	mediaControl = new MediaControl[numberOfMedia];
    	
    	int index = 13;
    	
    	for(int i = 0; i < numberOfMedia; i++){
    		mediaName[i] = "src/media/shokugeki" + (index) + ".mp4";
    		mediaUrl[i] = new File(mediaName[i]).getAbsolutePath();
    		media[i] = new Media(new File(mediaUrl[i]).toURI().toString());
    		mediaPlayer[i] = new MediaPlayer(media[i]);
    		mediaControl[i] = new MediaControl(mediaPlayer[i]);
    		index++;
    	
    	}
    	
//    	mediaName[0] = "src/media/shokugeki13.mp4";
//    	mediaUrl[0] = new File(mediaName[0]).getAbsolutePath();
//    	media[0] = new Media(new File(mediaUrl[0]).toURI().toString());
//    	mediaPlayer[0] = new MediaPlayer(media[0]);
//    	mediaControl[0] = new MediaControl(mediaPlayer[0]);
    	
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Embedded Media Player");
//        Group root = new Group();
//        Scene scene = new Scene(root, 1500, 800);
        BorderPane borderPane = new BorderPane();
        
        ComboBox<String> playlist = new ComboBox<>();
        ObservableList <String> items = FXCollections.observableArrayList(mediaName);
        
        playlist.getItems().addAll(items);
        playlist.setValue(items.get(index));
        playlist.setOnAction(e->{
        	mediaPlayer[index].stop();
        	index = items.indexOf(playlist.getValue());
        	playlist.setValue(items.get(index));
        	borderPane.setCenter(mediaControl[index]);
        	mediaPlayer[index].play();
        	primaryStage.sizeToScene();
        });
        
       btNext = new Button(">>");
       btNext.setOnAction(e->{
    	   if(index < numberOfMedia -1) {
    		   mediaPlayer[index].stop();
    		   index++;
    		   playlist.setValue(items.get(index));
    		   borderPane.setCenter(mediaControl[index]);
    		   mediaPlayer[index].play();
    	   }
       });
       
       btPrev = new Button("<<");
       btPrev.setOnAction(e->{
    	   if(index > 0) {
    		   mediaPlayer[index].stop();
    		   index--;
    		   playlist.setValue(items.get(index));
    		   borderPane.setCenter(mediaControl[index]);
    		   mediaPlayer[index].play();
    	   }
       });
        
        mediaPlayer[index].setOnEndOfMedia(new Runnable(){
        	@Override
        	public void run() {
        		
        		if(index < numberOfMedia-1) {
        			mediaPlayer[index].stop();
        			index++;
        			playlist.setValue(items.get(index));
         		   	borderPane.setCenter(mediaControl[index]);
         		   	mediaPlayer[index].play();
        		}
        	}
        });
        
        // create media player
//        Media media = new Media (new File(MEDIA_URL).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        mediaPlayer[index].setAutoPlay(true);
        
//        MediaControl mediaControl = new MediaControl(mediaPlayer);
//        scene.setRoot(mediaControl);

        HBox playlistBar = new HBox();
        playlistBar.getChildren().addAll(btPrev, playlist, btNext);
        playlistBar.setAlignment(Pos.CENTER);
        playlistBar.setSpacing(10);
        
        borderPane.setCenter(mediaControl[index]);
        borderPane.setTop(playlistBar);
        
        Scene scene = new Scene(borderPane, 1280, 790);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
