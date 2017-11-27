package application;
	
import fxml.MyResource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Dialog_View extends Application {

	public static MyResource myResource = new MyResource();
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = myResource.getLogCate_View();
			//Parent root = FXMLLoader.load(getClass().getResource(this.getClass().getSimpleName()+"1.fxml"));  
			  
	        Scene scene = new Scene(root, 300, 160);  
	        primaryStage.initStyle(StageStyle.UTILITY);  
	        primaryStage.setScene(scene);  
	        primaryStage.setTitle("Warning");  
	        primaryStage.show(); 
	        Window  window = scene.getWindow();
	        
			
			/*BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("111.fxml").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
