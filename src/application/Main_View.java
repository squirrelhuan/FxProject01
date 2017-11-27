package application;
	
import javax.swing.JOptionPane;

import fxml.MyResource;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


public class Main_View extends Application {
	public static boolean canExit = false;
	public static Pane ShadowPane;
    public static AnchorPane layoutPane;
    public static Stage dialog_primaryStage;
    public static Stage AppStage;
    public static FlowPane Clipboard_FlowPane;
    public static MyResource myResource = new MyResource();
    
	@Override
	public void start(Stage primaryStage) {
		this.AppStage = primaryStage;
		try {
			Parent root = myResource.getMain_View();  
			  
	        Scene scene = new Scene(root, 1080, 720);  
	        primaryStage.initStyle(StageStyle.DECORATED);  
	        primaryStage.setScene(scene);  
	        primaryStage.setTitle("MyTool-CGQ");  
	        //primaryStage.setIconified(true);是否只显示图标
	        ImageView depIcon = new ImageView(getClass().getClass().getResource("/fxml/image/cgq.png").toString()); 
	        primaryStage.getIcons().add(depIcon.getImage());
	        primaryStage.show(); 
			
	        //scene.windowProperty().
	        Label label = new Label("xxx");
	        
	        /*Image imageOk = new Image(getClass().getResourceAsStream("Copy_icon.png"));  
	        Button button = new Button("Accept",new ImageView(imageOk)); 
	        button.setTooltip(new Tooltip("Tooltip for Button"));*/
	        
	        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent e) {
	            	if (JOptionPane.showConfirmDialog(null, "Exit Tool?", "Confirm Exit",JOptionPane.YES_NO_OPTION)==1 && canExit) {
	            		System.exit(0);
	                    e.consume();
	            	}
	            }
	        });
	        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent event) {
	                if (true) {
	                    event.consume();
	                    primaryStage.show(); // I tried without this line also.
	                }
	            }
	        });
			
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
	public static void Exit(){
		dialog_primaryStage = new Stage();
    	Dialog_View dialog_View = new Dialog_View();
    	dialog_View.start(dialog_primaryStage);
    	ShadowPane.setVisible(true);
    	ShadowPane.setDisable(false);
    	dialog_primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent e) {
        	ShadowPane.setVisible(false);
        	ShadowPane.setDisable(true);
        	//if (JOptionPane.showConfirmDialog(null, "Exit Tool?", "Confirm Exit",JOptionPane.YES_NO_OPTION)==1) {
            //e.consume();
        	//}
        }
        
    });
	}
	public static void ShowDialog(){
		dialog_primaryStage.show();
	} 
	
	public static void MaxWindow(){
		AppStage.setIconified(false);
	}
	
	public static void MinWindow(){
		AppStage.setIconified(true);
	}
}
