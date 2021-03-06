package application;
	
import java.io.File;

import fxml.MyResource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class MediaCamera_window extends Application {

	public static MyResource myResource = MyResource.getInstance();
	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = myResource.getMediaCamera_View();
			//Parent root = FXMLLoader.load(getClass().getResource(this.getClass().getSimpleName()+"1.fxml"));  
			  
	        Scene scene = new Scene(root, 600, 400);  
	        primaryStage.initStyle(StageStyle.DECORATED);  
	        primaryStage.setScene(scene);  
	        primaryStage.setTitle("MediaPlayer");  
	        primaryStage.show(); 
	        Window  window = scene.getWindow();
	        
			setPrimaryStage(primaryStage);
			/*BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("111.fxml").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/* // Create and set the Scene.
	     Scene scene = new Scene(new Group(), 540, 209);
	     primaryStage.setScene(scene);

	     // Name and display the Stage.
	     primaryStage.setTitle("Hello Media");
	     primaryStage.show();

	     // Create the media source.
	    // String source = getParameters().getRaw().get(0);
	    String source = "D:\\剪辑版~终.mp4";
	    String directory = System.getProperty("user.dir");
  	    File file = new File("D:\\剪辑版~终.mp4");

  	    // media = new Media(file.toURI().toString());
	     Media media = new Media(file.toURI().toString());

	     // Create the player and set to play automatically.
	     MediaPlayer mediaPlayer = new MediaPlayer(media);
	     mediaPlayer.setAutoPlay(true);
	     // Create the view and add it to the Scene.
	     MediaView mediaView = new MediaView(mediaPlayer);
	     ((Group) scene.getRoot()).getChildren().add(mediaView);
	     mediaView.setFitHeight(primaryStage.getHeight()); 
	     mediaView.setFitWidth(primaryStage.getWidth());
	     
	     Label label = new Label("hahaha");
	     ((Group) scene.getRoot()).getChildren().add(label);*/
	  
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		MediaCamera_window.primaryStage = primaryStage;
	}
	
}
