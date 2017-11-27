package application;
	
import java.io.File;

import fxml.MyResource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
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


public class TouchScreen_window extends Application {

	public static MyResource myResource = MyResource.getInstance();
	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = myResource.getTouchScreen_View();
			//Parent root = FXMLLoader.load(getClass().getResource(this.getClass().getSimpleName()+"1.fxml"));  
			  
	        Scene scene = new Scene(root, 260, 200);  
	        primaryStage.initStyle(StageStyle.DECORATED); 
	      //modality要使用Modality.APPLICATION_MODEL
	        primaryStage.initModality(Modality.APPLICATION_MODAL);
	        primaryStage.setScene(scene);  
	        primaryStage.setTitle("模拟点击屏幕");  
	        primaryStage.showAndWait();
	        //primaryStage.show(); 
	       // Window  window = scene.getWindow();
	        
			//setPrimaryStage(primaryStage);
			/*BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("111.fxml").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
			//primaryStage.sizeToScene();
			//this.primaryStage.toFront();
			//使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
			//primaryStage.showAndWait();
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
		TouchScreen_window.primaryStage = primaryStage;
	}
	
}
