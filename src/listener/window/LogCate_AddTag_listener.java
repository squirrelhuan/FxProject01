package listener.window;


import javax.swing.JOptionPane;

import application.LogCate_NewTag_Dialog;
import provider.LogCateProvider;
import listener.ListenerBase;
import listener.Json_listener.Conversion_Type;
import model.CGQ_log.LogCate_Tag;
import model.View_model.TabBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class LogCate_AddTag_listener extends ListenerBase{
	@FXML
	private TextField textField_FilterName;
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
	}
	
	
	/** 清空 */
	@FXML
	public void onClickedCancelButton(ActionEvent event) {
		LogCateProvider.getInstance().clear();
	}
	/** 保存tag */
	@FXML
	public void onClickedSaveButton(ActionEvent event) {
		if(textField_FilterName.getText().trim().equals("")||textField_Tag.getText().trim().equals("")||textField_ApplicationName.getText().trim().equals("")){
			lable_error.setText("paramter can not be null");
			return ;
		}

		String level_Type = choiceBox_Leve.getSelectionModel().getSelectedItem();
		LogCate_Tag tag = new LogCate_Tag(LogCateProvider.getLevel(level_Type), textField_FilterName.getText(),textField_Tag.getText(),textField_ApplicationName.getText());
		LogCateProvider.getInstance().addTag(tag);
		LogCate_NewTag_Dialog.primaryStage.close();
		
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
