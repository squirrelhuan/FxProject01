package listener.window;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import application.Dialog_View;
import application.LogCate_NewTag_Dialog;
import application.Test;
import provider.ConsoleProvider;
import provider.LogCateProvider;
import event.LogCateEvent.ILogCateListener;
import event.LogCateEvent.LogCateEvent;
import event.LogCateEvent.LogCateListenerImpl;
import event.LogCateEvent.LogCateManager;
import model.CGQ_log.LogCate_Tag;
import model.View_model.LogCate;
import model.View_model.TabBase;
import model.View_model.adapter.RadioButton_Tag;
import model.View_model.window.Console_Tab;
import model.View_model.window.LogCate_Tab;
import model.View_model.window.Progress_Tab;
import util.JsonValidator;
import util.MySystem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.RadioMenuItemBuilder;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Bottom_Window_listener implements ILogCateListener {
	
	@FXML
	public TabPane tabPane_01,tabPane_bottom;
	@FXML
	private MenuButton menuButton_windows;

	@FXML
	private void onTabPaneStarted(ActionEvent event) {
	//	System.out.println(tabPane_01.getTabs());
		System.out.println("Current Tab:" + event.getSource());
	}
	
	/**
	 * 清空控制台
	 */
	@FXML
	private void onClickedClipboardDeleteButton(ActionEvent event) {
		ConsoleProvider.getInstance().getMessage_list().clear();
		//Cosole_TextArea.setText("");
	}
	private TabBase console_Tab,logCate_Tab,progress_Tab;
	private List<TabBase> list_tabs = new ArrayList<TabBase>();
	/** 表格初始化 */
	@FXML
	private void initialize() {
		console_Tab = Console_Tab.getInstance();
		logCate_Tab = LogCate_Tab.getInstance();
		progress_Tab = Progress_Tab.getInstance();
		list_tabs.add(console_Tab);
		list_tabs.add(logCate_Tab);
		list_tabs.add(progress_Tab);
		tabPane_bottom.getTabs().add(console_Tab);
		tabPane_bottom.getTabs().add(logCate_Tab);
		tabPane_bottom.getTabs().add(progress_Tab);
		//logCate_Tab = new LogCate_Tab("Logcate", Test.getInstance());
		
		for (TabBase tab : list_tabs) {
			   RadioMenuItem soundAlarmItem = RadioMenuItemBuilder.create()
		                .text(tab.getText())
		                .build();
		        soundAlarmItem.selectedProperty().set(true);
		        soundAlarmItem.selectedProperty().addListener(
						new ChangeListener<Boolean>() {
							public void changed(
									ObservableValue<? extends Boolean> ov,
									Boolean old_val, Boolean new_val) {
								if(new_val){
									tabPane_bottom.getTabs().add(tab);
									tabPane_bottom.getSelectionModel().select(tab);
								}else{
									tabPane_bottom.getTabs().remove(tab);
								}
								//tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
									//part_rdbtn_d.selectedProperty().set(true);
							}
						});

		        menuButton_windows.getItems().add(soundAlarmItem);
		}
	}

	/**
	 * LogCate_Tab
	 */
	@FXML
	private void onClickedLogCateMenuItem() {
		//LogCate_Tab tab = new LogCate_Tab("Logcate", Test.getInstance());
		//tabPane_bottom.getTabs().add(tab);
		//tabPane_bottom.getSelectionModel().select(tabPane_bottom.getTabs().size() - 1);
	}
	
	@Override
	public void doorEvent(LogCateEvent event) {
		if (event.getEventType() == LogCateEvent.LogCateEventType.onLogsChanged) {
		
		} else if (event.getEventType() == LogCateEvent.LogCateEventType.onTagsChanged) {
			
		}
	}

	/** 清空 */
	@FXML
	public void onClickedClearButton(ActionEvent event) {
		LogCateProvider.getInstance().clear();
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
			((Control) event.getSource()).setTooltip(new Tooltip("清空控制台"));
		}
	}

	
}
