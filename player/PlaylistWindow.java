package player;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.MediaDetail;

//Table
//Able to do some editing £¨add, delete)
//Communicate with the media player
//only need to send the media url, or just media?
//Send url?

public class PlaylistWindow {

	private EdittedMediaPlayer editmp;
	private PlaylistManager playMag;

	private Button btDel, btAdd, btSave;
	private Label lbNotify;
	private HBox controlBox;
	
	private int currIndex;
	//	private FlowPane flowpane;

	//	private List<Media> playlist;
	private List<String> mediaNameList;
	private List<String> mediaUrlList;

	//	private List<MediaPlayer> playerList;
	//	private List<MediaControl> controlList;
	private List<MediaDetail> detailList;

	private TableView <MediaDetail> mediaTable;
	private ObservableList<MediaDetail> record;

	private Stage stage;

	public PlaylistWindow(EdittedMediaPlayer editmp) {
		this.editmp = editmp;

		playMag = new PlaylistManager();
		mediaNameList = playMag.getList();
		mediaUrlList = playMag.getMediaUrlList();
		detailList = new ArrayList<>();

		for(int i = 0; i < mediaNameList.size(); i++) {
			detailList.add(new MediaDetail(
					mediaNameList.get(i), mediaUrlList.get(i)));
		}
		
		currIndex = 0;

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

			mediaTable.getItems().add(new MediaDetail(newMediaName, newMediaUrl));

			lbNotify.setVisible(false);

		}catch(Exception ex) {
			System.out.println("Null");
		}
	}

	private void deleteMedia() {
		MediaDetail item = mediaTable.getSelectionModel().getSelectedItem();
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
		record = FXCollections.observableArrayList(detailList);
		mediaTable.setEditable(false);

		TableColumn<MediaDetail, Void> indexCol = new TableColumn<>("Index");
		indexCol.setCellFactory(col ->{
			TableCell<MediaDetail, Void> cell = new TableCell<>();
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

		TableColumn<MediaDetail, String> mediaName = new TableColumn<>("Name");
		mediaName.setCellValueFactory(
				new PropertyValueFactory<MediaDetail, String>("name"));
		mediaName.setSortable(false);

		mediaTable.setItems(record);
		mediaTable.getColumns().addAll(indexCol, mediaName);

		//Fixed the column size to the window
		//So that no unused space appear
		mediaTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		mediaTable.setPlaceholder(new Label("No rows to display"));
		
		mediaTable.setRowFactory(tv ->{
			TableRow<MediaDetail> row = new TableRow<>();
			row.setOnMouseClicked(e ->{
				if(e.getClickCount() == 2 && (!row.isEmpty())) {
					MediaDetail mediaDetail = row.getItem();
					editmp.changeMedia(row.getIndex(), mediaDetail);
				}
			});
			return row;
		});
	}

	public void start(Stage stage) throws Exception {
		Stage tableStage = new Stage();
		initialiseTable();
		initialiseControl();

		VBox vbox = new VBox();
		vbox.getChildren().addAll(mediaTable, controlBox);

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		Scene scene = new Scene(vbox, 400, 460);

		tableStage.setTitle("Playlist");
		tableStage.setScene(scene);
		tableStage.sizeToScene();
		tableStage.setMinHeight(500);
		tableStage.setMinWidth(400);
		tableStage.setAlwaysOnTop(true);
		//		stage.setResizable(false);
		tableStage.show();
	}

	public MediaDetail getFirstMedia() {
		return detailList.get(0);
	}

	public MediaDetail changeMedia(int index) {
		if(index < mediaNameList.size() && index >= 0) {
			return detailList.get(index);
		}else {
			return null;
		}
		
	}
	
	//	public static void main(String[] args) {
	//		Application.launch(args);
	//	}
}
