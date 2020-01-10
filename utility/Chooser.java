package utility;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Chooser { //To add to playlist
	private String filepath = null;
	private FileChooser fileChooser;
	
	public Chooser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Select media file");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("MP3 File", "*.mp3"),
				new FileChooser.ExtensionFilter("MP4 File", "*.mp4"));
	}
	
	public String getFilepath() {
		return this.filepath;
	}
	
	public MediaDetail chooseMediaFile(Stage stage) {
//		this.stage = stage;
//		stage.initModality(Modality.APPLICATION_MODAL);
		File mediaFile = fileChooser.showOpenDialog(stage);
		return getMediaInfo(mediaFile);
	}
	
	private MediaDetail getMediaInfo(File mediaFile) {
		try {
			String filepath = mediaFile.getAbsolutePath();
			String name = "";
		
			for(int i = filepath.length() - 1; i >= 0 ; i --) {
				if(filepath.charAt(i) == '\\') {
					name = filepath.substring(i+1);
					break;
				}
			}
		
			return new MediaDetail(name, filepath);
		}catch(NullPointerException ex) {
			return null;
		}
	}
}
