package application;
	
import fxml.MyResource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class LogCate_NewTag_Dialog extends Application {

	public static MyResource myResource = MyResource.getInstance();
	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = myResource.getLogCate_AddTag_View();
			//Parent root = FXMLLoader.load(getClass().getResource(this.getClass().getSimpleName()+"1.fxml"));  
			  
	        Scene scene = new Scene(root, 360, 240);  
	        primaryStage.initStyle(StageStyle.UTILITY);  
	        primaryStage.setScene(scene);  
	        primaryStage.setTitle("New Tag");  
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
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		LogCate_NewTag_Dialog.primaryStage = primaryStage;
	}
	
}
