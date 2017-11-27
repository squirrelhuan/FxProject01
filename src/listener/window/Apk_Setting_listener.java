package listener.window;


import java.io.File;
import java.io.FileInputStream;

import javax.swing.JOptionPane;

import application.APK_Setting_Dialog;
import application.LogCate_NewTag_Dialog;
import provider.LogCateProvider;
import util.FileTools;
import util.MySystem;
import listener.ListenerBase;
import listener.Json_listener.Conversion_Type;
import listener.LocalAreaNetwork_listener;
import model.CGQ_log.LogCate_Tag;
import model.View_model.TabBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class Apk_Setting_listener extends ListenerBase{
	@FXML
	private TextField textField_FilePath;
	@FXML
	private TextField textField_Tag;
	@FXML
	private TextField textField_ApplicationName;
	@FXML
	private Label lable_error;
	@FXML
	private ChoiceBox<String> choiceBox_Leve;
	private String[] list_level={"all","verbose","debug","info","warn","error","assert"};

	/** 表格初始化 */
	@FXML
	private void initialize() {
		for (int i = 0; i< list_level.length;i++) {
			choiceBox_Leve.getItems().add(list_level[i]);
		}
		choiceBox_Leve.getSelectionModel().select(0);
		textField_FilePath.setText(LocalAreaNetwork_listener.apk_path);
	}
	
	
	/** 选择路径按钮 */
	@FXML
	public void onClickedSelectPathButton(ActionEvent event) {
		String path_old = LocalAreaNetwork_listener.apk_path;
		String path = path_old.contains("\\") ? path_old.substring(0, path_old.lastIndexOf("\\")):path_old;
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("选择要安装的apk");
			if(path.equals("user.home")){
				fileChooser.setInitialDirectory(new File(System
						.getProperty(path)));
			}else{
				fileChooser.setInitialDirectory(new File(path));
			}
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("文件路径", "*.*"),
					new FileChooser.ExtensionFilter("所有文件", "*.*"));

			File file = fileChooser.showOpenDialog(textField_FilePath.getScene()
					.getWindow());
			fileChooser.setInitialDirectory(new File(System
					.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("文件路径", "*.*"),
					new FileChooser.ExtensionFilter("所有文件", "*.*"));

			if (file != null) {
				String filepath = file.getAbsolutePath();
				textField_FilePath.setText(filepath);
			}
	}
	/** 保存tag */
	@FXML
	public void onClickedSaveButton(ActionEvent event) {
		if(textField_FilePath.getText().trim().equals("")){
			lable_error.setText("path can not be null");
			return ;
		}
		LocalAreaNetwork_listener.apk_path = textField_FilePath.getText();

		APK_Setting_Dialog.primaryStage.close();
		
	}
	
	/**
	 * Json_Tab
	 */
	@FXML
	private void onMouseEntered(MouseEvent event) {
		/*
		 * Rectangle rect = new Rectangle(0, 0, 100, 100); Tooltip t = new
		 * Tooltip("A Square"); Tooltip.install(rect, t);
		 */
		if (event.getSource() instanceof TabBase) {
			((TabBase) event.getSource()).setTooltip(new Tooltip(
					((TabBase) event.getSource()).getDescription()));
		} else {
			((Control) event.getSource()).setTooltip(new Tooltip(
					"清空控制台"));
		}
	}
}
