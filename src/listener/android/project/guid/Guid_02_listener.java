package listener.android.project.guid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import util.MySystem;
import application.AndroidProject_Guid_window;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.DirectoryChooserBuilder;
import javafx.stage.WindowEvent;
import listener.Json_listener.Conversion_Type;
import model.setting.AndroidTargetModel;
/**
 * 
 * @author CGQ
 *
 */
public class Guid_02_listener {
	@FXML 
	public Button btn_Previous,btn_Next,btn_Cancel,btn_Finish;
	@FXML 
	public ChoiceBox<String> choiceBox_target;
    
	private AndroidProject_Guid_window androidProject_Guid_01_window;
	
	/** 初始化 */
	@FXML
	private synchronized  void initialize() {
		androidProject_Guid_01_window = AndroidProject_Guid_window.androidProject_Guid_01_window;
		
		List<AndroidTargetModel> targets = CommandForAndroid.GetAndroidTargets();
		for (AndroidTargetModel model : targets) {
			choiceBox_target.getItems().add(model.getName());
		}
		choiceBox_target.getSelectionModel().select(0);
		choiceBox_target.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println(newValue.intValue());
				androidProject_Guid_01_window.androidTarget = newValue.intValue();
				//label.setText(fruits[newValue.intValue()]);
			}
		});
		
		btn_Finish = ButtonBuilder.create().text("Close").onAction(new EventHandler<ActionEvent>(){
		        @Override public void handle(ActionEvent e){
		          Event.fireEvent(androidProject_Guid_01_window.primaryStage, new WindowEvent(androidProject_Guid_01_window.primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST ));
		        }
		      }).build();
	}
	/** Edit按钮 */
	@FXML
	public void onClickEditButton(ActionEvent event) {
		/*boolean b = textField_Package_Name.disableProperty().get();
		textField_Package_Name.setDisable(!b);*/
	}
	/** 路径选择按钮 */
	@FXML
	public void onClickChoicePathButton(ActionEvent event) {
		/*selectFilePath(textField_Project_Location);*/
	}
	
	/** Previous按钮 */
	@FXML
	public void onClickPreviousButton(ActionEvent event) {
		androidProject_Guid_01_window.Previous();
	}
	/** Next按钮 */
	@FXML
	public void onClickNextButton(ActionEvent event) {
		androidProject_Guid_01_window.Next();
		CommandForAndroid.GetAndroidTargets();
	}
	/** Cancel按钮 */
	@FXML
	public void onClickCancelButton(ActionEvent event) {
		androidProject_Guid_01_window.Cancel();
	}
	String project_Name;
	String project_Target;
	String project_Path;
	String project_PackageName;
	String project_Activity; 
	/** Finish按钮 */
	@FXML
	public void onClickFinishButton(ActionEvent event) {
		androidProject_Guid_01_window.Finish();
		
	}
	
	/** 读取文本 */
	public void selectFilePath(TextField textField) {
		DirectoryChooserBuilder builder = DirectoryChooserBuilder.create();
		builder.title("android project 存放路径选择");
		String cwd = System.getProperty("user.dir");
		File file = null;
		builder.initialDirectory(file);
		DirectoryChooser chooser = builder.build();
		/*File chosenDir = chooser.showDialog(textField_Application_Name.getScene()
				.getWindow());
		if (chosenDir != null) {
			textField.setText(chosenDir.getAbsolutePath());
		}*/
	}
}
