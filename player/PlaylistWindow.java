package player;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
	
	private Button btDel, btAdd, btSave;
	private Label lbNotify;
	private HBox controlBox;
//	private FlowPane flowpane;
	
//	private List<Media> playlist;
	private List<String> mediaNameList;
	private List<String> mediaUrlList;
	
//	private List<MediaPlayer> playerList;
//	private List<MediaControl> controlList;
	private List<MediaInfo> infoList;
	
	private TableView <MediaInfo> mediaTable;
	private ObservableList<MediaInfo> record;
	
	private Stage stage;
	
	public class MediaInfo{
		private String mediaName;
		private String mediaUrl;
		
		MediaInfo(String mediaName, String mediaUrl){
			this.mediaName = mediaName;
			this.mediaUrl = mediaUrl;
		}

		public String getMediaName() {
			return mediaName;
		}
		public String getMediaUrl() {
			return mediaUrl;
		}

	}
	
	public PlaylistWindow() {
//		this.editmp = editmp;
		
		playMag = new PlaylistManager();
		mediaNameList = playMag.getList();
		mediaUrlList = playMag.getMediaUrlList();
		infoList = new ArrayList<>();
		
		for(int i = 0; i < mediaNameList.size(); i++) {
			infoList.add(new MediaInfo(
					mediaNameList.get(i), mediaUrlList.get(i)));
		}
		
	}

	
	private void initialiseControl() {
		controlBox = new HBox();
		
		btAdd = new Button("Add");
		btDel = new Button("Delete");
		btSave = new Button("Save");
		lbNotify = new Label("Save Changes");
		lbNotify.setVisible(false);
		
		controlBox.getChildren().addAll(btAdd, btDel, btSave, lbNotify);
		controlBox.setSpacing(5);
		controlBox.setPadding(new Insets(5, 5, 5 , 5));
		
		btAdd.setOnAction(e -> addNewMedia());
		btDel.setOnAction(e -> deleteMedia());
		btSave.setOnAction(e -> saveList());
	}
	
	private void addNewMedia() {
		try {
			playMag.addMedia(stage);
			
			String newMediaName = playMag.getAddedMediaName();
			String newMediaUrl = playMag.getAddedMediaUrl();
			
			mediaNameList.add(newMediaName);
			mediaUrlList.add(newMediaUrl);
			
			mediaTable.getItems().add(new MediaInfo(newMediaName, newMediaUrl));
			
			lbNotify.setVisible(false);
			
		}catch(Exception ex) {
			System.out.println("Null");
		}
	}
	
	private void deleteMedia() {
		MediaInfo item = mediaTable.getSelectionModel().getSelectedItem();
		int index = mediaTable.getItems().indexOf(item);
		mediaTable.getItems().remove(item);
//		playMag.deleteMedia(item.mediaName, item.mediaUrl);
		playMag.deleteMedia(index);
	}
	
	private void saveList() {
		playMag.saveChange();
		lbNotify.setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	private void initialiseTable() {
		mediaTable = new TableView<>();
		record = FXCollections.observableArrayList(infoList);
		mediaTable.setEditable(false);
		
		TableColumn<MediaInfo, Void> indexCol = new TableColumn<>("Index");
		indexCol.setCellFactory(col ->{
			TableCell<MediaInfo, Void> cell = new TableCell<>();
			cell.textProperty().bind(Bindings.createStringBinding(()->{
				if(cell.isEmpty())
					return null;
				else
					return Integer.toString(cell.getIndex() + 1);

			}, cell.emptyProperty(), cell.indexProperty()));
			return cell;
		});
		indexCol.setMaxWidth(50);
		indexCol.setMinWidth(50);
		
		TableColumn<MediaInfo, String> mediaName = new TableColumn<>("Name");
		mediaName.setCellValueFactory(new PropertyValueFactory<MediaInfo, String>("mediaName"));
		mediaName.setSortable(false);
		
		mediaTable.setItems(record);
		mediaTable.getColumns().addAll(indexCol, mediaName);
		
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
		Scene scene = new Scene(vbox, 400, 460);
		
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
