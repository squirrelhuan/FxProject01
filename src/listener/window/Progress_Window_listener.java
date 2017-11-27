package listener.window;

import java.util.ArrayList;

import provider.TaskManagerProvider;
import event.LogCateEvent.LogCateEvent;
import event.Task.ITaskListener;
import event.Task.TaskEvent;
import event.Task.TaskManager;
import model.Task.TaskModel;
import model.View_model.TabBase;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class Progress_Window_listener implements ITaskListener {

	@FXML
	public ListView<TaskModel> listView_task = new ListView<TaskModel>();
	private static ArrayList<TaskModel> list_task = new ArrayList<TaskModel>();
	public static ObservableList<TaskModel> observableList;

	/** 表格初始化 */
	@FXML
	private void initialize() {
		TaskManager.getInstance().addDoorListener(this);// 给门1增加监听器
		// getTask();

	}

	/**
	 * LogCate_Tab
	 */
	@FXML
	private void onClickedLogCateMenuItem() {
		// LogCate_Tab tab = new LogCate_Tab("Logcate", Test.getInstance());
		// tabPane_bottom.getTabs().add(tab);
		// tabPane_bottom.getSelectionModel().select(tabPane_bottom.getTabs().size()
		// - 1);
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

	public static ArrayList<CheckBox> CheckBoxList = new ArrayList<CheckBox>();
	private static boolean selectAll = true;

	class ColorRectCell extends ListCell<TaskModel> {

		@Override
		public void updateItem(TaskModel item, boolean empty) {
			super.updateItem(item, empty);
			// Rectangle rect = new Rectangle(100, 20);
			// rect.setFill(Color.web(item));
			if (item != null) {
				CheckBox checkbox = new CheckBox();
				CheckBoxList.add(checkbox);
				// checkbox.setSelected(item.isSelected());
				checkbox.selectedProperty().addListener(
						new ChangeListener<Boolean>() {
							public void changed(
									ObservableValue<? extends Boolean> ov,
									Boolean old_val, Boolean new_val) {
								if (selectAll) {
									// part_rdbtn_d.selectedProperty().set(true);
									selectAll = false;
								}
								// item.setSelected(new_val);
								// item.isSelected = (new_val ? true : false);
							}
						});
				// Label label = new Label(item.getName());
				// + item.getIp().trim() + ")");
				final ContextMenu cm = new ContextMenu();
				MenuItem cmItem1 = new MenuItem("install apk");
				// cmItem1.setOnAction((ActionEvent e) -> {
				// });

				cm.getItems().add(cmItem1);
				FlowPane flowPane = new FlowPane();
				AnchorPane anchorPane = new AnchorPane();
				flowPane.getChildren().add(anchorPane);
				ImageView imageView = new ImageView(getClass().getClass()
						.getResource("/fxml/image/folder_icon_16.png")
						.toString());
				AnchorPane.setTopAnchor(imageView, 8.0);
				AnchorPane.setLeftAnchor(imageView, 0.0);
				// AnchorPane.setRightAnchor(imageView, 0.0);
				AnchorPane.setBottomAnchor(imageView, 0.0);
				anchorPane.getChildren().add(imageView);

				ProgressBar progressBar = new ProgressBar();
				progressBar.setProgress(item.getProcess());
				progressBar.setStyle("-fx-accent: green");
				progressBar.setPrefHeight(13);
				progressBar.setMinSize(10, 10);
				// AnchorPane.setTopAnchor(progressBar, 25.0);
				AnchorPane.setLeftAnchor(progressBar, 20.0);
				AnchorPane.setRightAnchor(progressBar, 20.0);
				AnchorPane.setBottomAnchor(progressBar, 0.0);
				anchorPane.getChildren().add(progressBar);

				Label label = new Label(item.getName());
				// label.setTextFill(Color.BLUE);
				AnchorPane.setTopAnchor(label, 0.0);
				AnchorPane.setLeftAnchor(label, 25.0);
				AnchorPane.setRightAnchor(label, 25.0);
				AnchorPane.setBottomAnchor(label, 15.0);
				anchorPane.getChildren().add(label);

				ImageView button_cancel = new ImageView(getClass().getClass()
						.getResource("/fxml/image/process_stop_close_16px.png")
						.toString());
				AnchorPane.setTopAnchor(button_cancel, 8.0);
				// AnchorPane.setLeftAnchor(button_cancel, 0.0);
				AnchorPane.setRightAnchor(button_cancel, 0.0);
				AnchorPane.setBottomAnchor(button_cancel, 0.0);
				anchorPane.getChildren().add(button_cancel);

				// FlowPane flowPane = new FlowPane();
				flowPane.getChildren().add(checkbox);
				// flowPane.getChildren().add(label);
				flowPane.addEventHandler(
						MouseEvent.MOUSE_CLICKED,
						(MouseEvent e) -> {
							if (e.getButton() == MouseButton.SECONDARY) {
								cm.show(flowPane.getParent(), e.getScreenX(),
										e.getScreenY());
							}
						});
				// flowPane.setOnMouseClicked(value);
				setGraphic(anchorPane);
			}
		}
	}

	@Override
	public void onTaskChangedEvent(TaskEvent event) {
		// list_task.get(0).setProcess(30);
		getTask();
		if (event.getEventType() == TaskEvent.TaskEventType.onStarted) {

		} else if (event.getEventType() == TaskEvent.TaskEventType.onFinished) {

		} else if (event.getEventType() == TaskEvent.TaskEventType.onProgressChanged) {

		} else if (event.getEventType() == TaskEvent.TaskEventType.onErrored) {

		}
	}

	void getTask() {
		list_task.clear();
		list_task.addAll(TaskManagerProvider.getList_task());
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				observableList = FXCollections.observableArrayList(list_task);
				listView_task.setItems(observableList);
				listView_task.setCellFactory((ListView<TaskModel> l) -> null);
				listView_task
						.setCellFactory((ListView<TaskModel> l) -> new ColorRectCell());

			}
		});
	}
}
