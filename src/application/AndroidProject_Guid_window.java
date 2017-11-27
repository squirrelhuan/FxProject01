package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import util.MySystem;
import fxml.MyResource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import listener.android.project.guid.CommandForAndroid;

public class AndroidProject_Guid_window extends Application {

	public static MyResource myResource = MyResource.getInstance();
	public static Stage primaryStage;
	private static List<Parent> roots = new ArrayList<Parent>();
	public static Parent root;
	private static Scene scene;
	private static int page_index;
	public static AndroidProject_Guid_window androidProject_Guid_01_window = getInstance();

	private static AndroidProject_Guid_window getInstance() {
		if (androidProject_Guid_01_window == null) {
			return new AndroidProject_Guid_window();
		}
		return androidProject_Guid_01_window;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			roots.add(0, myResource.getNewAndroidProject_guid_01_View());
			roots.add(1, myResource.getNewAndroidProject_guid_02_View());
			// roots.add(0,myResource.getNewAndroidProject_guid_01_View());
			// roots.add(0,myResource.getNewAndroidProject_guid_01_View());
			root = new AnchorPane();
			((AnchorPane) root).getChildren().add(roots.get(0));
			// Parent root =
			// FXMLLoader.load(getClass().getResource(this.getClass().getSimpleName()+"1.fxml"));

			scene = new Scene(root, 600, 400);
			primaryStage.initStyle(StageStyle.DECORATED);
			//modality要使用Modality.APPLICATION_MODEL
	        primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setScene(scene);
			primaryStage.setTitle("New Project");
			primaryStage.showAndWait();
			//primaryStage.show();
			Window window = scene.getWindow();

			setPrimaryStage(primaryStage);
			/*
			 * BorderPane root = new BorderPane(); Scene scene = new
			 * Scene(root,400,400);
			 * scene.getStylesheets().add(getClass().getResource
			 * ("111.fxml").toExternalForm()); primaryStage.setScene(scene);
			 * primaryStage.show();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * // Create and set the Scene. Scene scene = new Scene(new Group(),
		 * 540, 209); primaryStage.setScene(scene);
		 * 
		 * // Name and display the Stage. primaryStage.setTitle("Hello Media");
		 * primaryStage.show();
		 * 
		 * // Create the media source. // String source =
		 * getParameters().getRaw().get(0); String source = "D:\\剪辑版~终.mp4";
		 * String directory = System.getProperty("user.dir"); File file = new
		 * File("D:\\剪辑版~终.mp4");
		 * 
		 * // media = new Media(file.toURI().toString()); Media media = new
		 * Media(file.toURI().toString());
		 * 
		 * // Create the player and set to play automatically. MediaPlayer
		 * mediaPlayer = new MediaPlayer(media); mediaPlayer.setAutoPlay(true);
		 * // Create the view and add it to the Scene. MediaView mediaView = new
		 * MediaView(mediaPlayer); ((Group)
		 * scene.getRoot()).getChildren().add(mediaView);
		 * mediaView.setFitHeight(primaryStage.getHeight());
		 * mediaView.setFitWidth(primaryStage.getWidth());
		 * 
		 * Label label = new Label("hahaha"); ((Group)
		 * scene.getRoot()).getChildren().add(label);
		 */

	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		AndroidProject_Guid_window.primaryStage = primaryStage;
	}

	public String project_Name;
	public String project_Target;
	public String project_Path;
	public String project_PackageName;
	public String project_Activity;
	public int androidTarget; 
	public void Finish() {

		CommandForAndroid.creatAndroidProject(project_Name,project_Target,project_Path,project_PackageName,project_Activity);
	}

	public void Cancel() {
		Event.fireEvent(androidProject_Guid_01_window.primaryStage,
				new WindowEvent(androidProject_Guid_01_window.primaryStage,
						WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	public void Next() {
		if (page_index+1 < 2) {
			page_index += 1;
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				((AnchorPane) root).getChildren().removeAll(roots);
				((AnchorPane) root).getChildren().add(roots.get(page_index));
			}
		});
	}

	public void Previous() {
		if (page_index-1 >=0 ) {
			page_index -= 1;
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				((AnchorPane) root).getChildren().removeAll(roots);
				((AnchorPane) root).getChildren().add(roots.get(page_index));
			}
		});
	}

}
