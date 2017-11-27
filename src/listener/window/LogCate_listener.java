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
import provider.LogCateProvider;
import event.LogCateEvent.ILogCateListener;
import event.LogCateEvent.LogCateEvent;
import event.LogCateEvent.LogCateListenerImpl;
import event.LogCateEvent.LogCateManager;
import model.CGQ_log.LogCate_Tag;
import model.View_model.LogCate;
import model.View_model.TabBase;
import model.View_model.adapter.RadioButton_Tag;
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
import javafx.scene.control.RadioButton;
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

public class LogCate_listener implements ILogCateListener {
	@FXML
	private HBox hbox_tags;
	@FXML
	private Label lable_count;
	@FXML
	private ImageView image_state;
	ToggleGroup group_tags;

	@FXML
	private TableColumn<TableData, String> LevelColumn;
	@FXML
	private TableColumn<TableData, String> TimeColumn;
	@FXML
	private TableColumn<TableData, String> ApplicationColumn;
	@FXML
	private TableColumn<TableData, String> TagColumn;
	@FXML
	private TableColumn<TableData, String> TextColumn;
	@FXML
	public TableView<TableData> LogCateTableView;
	private ObservableList<TableData> personData = FXCollections
			.observableArrayList();
	List<LogCate> logs = new ArrayList<LogCate>();

	/** 表格初始化 */
	@FXML
	private void initialize() {
		LogCateManager.getInstance().addDoorListener(this);// 给门1增加监听器
		logs = LogCateProvider.getInstance().getLogs();
		for (LogCate log : logs) {
			personData.add(new TableData(log));
		}

		/*
		 * LevelColumn.setCellValueFactory(cellData -> cellData.getValue()
		 * .LevelnameProperty()); TimeColumn.setCellValueFactory(cellData ->
		 * cellData.getValue() .timeProperty());
		 * ApplicationColumn.setCellValueFactory(cellData -> cellData.getValue()
		 * .applicationProperty()); TagColumn.setCellValueFactory(cellData ->
		 * cellData.getValue() .tagProperty());
		 * TextColumn.setCellValueFactory(cellData -> cellData.getValue()
		 * .textProperty());
		 */
		LevelColumn
				.setCellValueFactory(new PropertyValueFactory<TableData, String>(
						"Level"));
		TimeColumn
				.setCellValueFactory(new PropertyValueFactory<TableData, String>(
						"Time"));
		ApplicationColumn
				.setCellValueFactory(new PropertyValueFactory<TableData, String>(
						"Application"));
		TagColumn
				.setCellValueFactory(new PropertyValueFactory<TableData, String>(
						"Tag"));
		TextColumn
				.setCellValueFactory(new PropertyValueFactory<TableData, String>(
						"Text"));

		Callback myCallback = new Callback<TableColumn, TableCell>() {
			public TableCell call(TableColumn param) {
				return new TableCell<TableData, String>() {
					ObservableValue ov;
					TableData tableData;
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							ov = getTableColumn().getCellObservableValue(
									getIndex());
							int level = LogCateProvider.getLevel(ov.getValue()
									.toString());
							switch (level) {
							case LogCateProvider.LEVEL.debug:
								this.setStyle("-fx-text-fill: blue");
								break;
							case LogCateProvider.LEVEL.error:
								this.setStyle("-fx-text-fill: red");
								break;
							case LogCateProvider.LEVEL.info:
								this.setStyle("-fx-text-fill: green");
								break;
							case LogCateProvider.LEVEL.verbose:
								this.setStyle("-fx-text-fill: black");
								break;
							case LogCateProvider.LEVEL.warn:
								this.setStyle("-fx-text-fill: yellow");
								break;
							default:
								this.setStyle("-fx-text-fill: black");
								break;
							}

							/*if (getTableRow() != null && item.contains("@")) {
								this.getTableRow().setStyle(
										"-fx-background-color: green");
							}*/

							// this.setTextFill(Color.RED);
							// this.getTableRow().setTextFill(Color.GREEN);

							// Get fancy and change color based on data
							// if (item.contains("@"))
							// this.setTextFill(Color.YELLOW);
							setText(item);
						}

					}
				};
			}
		};
		LevelColumn.setCellFactory(myCallback);
		// TimeColumn.setCellFactory(myCallback);
		// ApplicationColumn.setCellFactory(myCallback);
		// TagColumn.setCellFactory(myCallback);
		// TextColumn.setCellFactory(myCallback);

		LogCateTableView.setItems(personData);

		/*
		 * Callback myCallback = new Callback<TableColumn, TableCell>() { public
		 * TableCell call(TableColumn param) { return new TableCell<String,
		 * String>() { ObservableValue ov;
		 * 
		 * @Override public void updateItem(String item, boolean empty) {
		 * super.updateItem(item, empty); if (!empty) { ov =
		 * getTableColumn().getCellObservableValue(getIndex()); if(getTableRow()
		 * != null&&!ov.equals("")){ Map<String,String> vmap =
		 * (Map<String,String>)this.getTableRow().getItem(); boolean colorFlag =
		 * false; if(vmap.get("TEXT_COLOR")!=null){
		 * if(!vmap.get("TEXT_COLOR").equals("")){
		 * this.setStyle("-fx-text-fill: "+vmap.get("TEXT_COLOR")); colorFlag =
		 * true; } } if(vmap.get("BACKGROUND_COLOR")!=null){
		 * if(!vmap.get("BACKGROUND_COLOR").equals("")){ if(!colorFlag){
		 * this.setStyle("-fx-background-color: "+vmap.get("BACKGROUND_COLOR"));
		 * }else{
		 * this.setStyle("-fx-background-color: "+vmap.get("BACKGROUND_COLOR"
		 * )+";"+"-fx-text-fill: "+vmap.get("TEXT_COLOR")); } } } }
		 * setText(item); } } }; }};
		 * 
		 * LevelColumn.setCellValueFactory(new MapValueFactory("Level"));
		 * LevelColumn.setCellFactory(myCallback);
		 * LogCateTableView.getColumns().add(LevelColumn);
		 */

		// LogCateTableView.getColumns().addAll(LevelColumn, TimeColumn,
		// ApplicationColumn,TagColumn,TextColumn);

		// LogCateTableView.setItems(personData);

		Image image;
		boolean state = LogCateProvider.getInstance().isState();
		if (!state) {
			image = new Image(getClass().getClass()
					.getResource("/fxml/image/logcate_start.png").toString());
		} else {
			image = new Image(getClass().getClass()
					.getResource("/fxml/image/logcate_pause.png").toString());
		}
		image_state.setImage(image);

		group_tags = new ToggleGroup();
		List<LogCate_Tag> list_tags = LogCateProvider.getInstance().getTags();
		RadioButton_Tag radioButton = null;
		for (LogCate_Tag mtag : list_tags) {
			radioButton = new RadioButton_Tag(mtag);
			radioButton.setPrefHeight(25);
			hbox_tags.getChildren().add(radioButton);
			radioButton.setToggleGroup(group_tags);
		}
		group_tags.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle old_toggle, Toggle new_toggle) {
						if (group_tags.getSelectedToggle() != null) {
							RadioButton_Tag radioButton = ((RadioButton_Tag) group_tags
									.getSelectedToggle());
							radioButton.selectedProperty().set(true);
							LogCateProvider.getInstance().setTags_current(
									radioButton.getLogCate_Tag());

						}
					}
				});
		group_tags.selectToggle(radioButton);
	}

	@Override
	public void doorEvent(LogCateEvent event) {
		if (event.getEventType() == LogCateEvent.LogCateEventType.onLogsChanged) {
			logs = LogCateProvider.getInstance().getLogs();
			personData.clear();
			for (LogCate log : logs) {
				personData.add(new TableData(log));
			}
			if (logs != null) {
				lable_count.setText("" + logs.size());
			}
		} else if (event.getEventType() == LogCateEvent.LogCateEventType.onTagsChanged) {
			hbox_tags.getChildren().clear();
			group_tags.getToggles().clear();
			List<LogCate_Tag> list_tags = LogCateProvider.getInstance()
					.getTags();
			for (int i = 0; i < list_tags.size(); i++) {
				RadioButton_Tag radioButton = new RadioButton_Tag(
						list_tags.get(i));
				radioButton.setPrefHeight(25);
				hbox_tags.getChildren().add(radioButton);
				radioButton.setToggleGroup(group_tags);
			}
			group_tags.selectToggle(group_tags.getToggles().get(
					list_tags.size() - 1));
		}
	}

	/** 清空 */
	@FXML
	public void onClickedClearButton(ActionEvent event) {
		LogCateProvider.getInstance().clear();
	}

	/** 改变状态 */
	@FXML
	public void onClickedChangeState(ActionEvent event) {
		// load the image
		Image image;
		boolean state = LogCateProvider.getInstance().isState();
		if (state) {
			image = new Image(getClass().getClass()
					.getResource("/fxml/image/logcate_start.png").toString());
		} else {
			image = new Image(getClass().getClass()
					.getResource("/fxml/image/logcate_pause.png").toString());
		}
		image_state.setImage(image);
		LogCateProvider.getInstance().setState(!state);
		MySystem.out
				.println("state:" + LogCateProvider.getInstance().isState());
	}

	/** 新建tag */
	@FXML
	public void onClickedNewTagButton(ActionEvent event) {
		new LogCate_NewTag_Dialog().start(new Stage());
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

	// 按照行来设置颜色
	public TableView initTable(JSONArray tablecolJson, JSONObject tableinfo) {
		// JSONArray tablecolJson,JSONObject tableinfo,JSONObject tablePage
		JSONObject jsonObject = null;
		LogCateTableView = new TableView<>();
		String twidth = (String) tableinfo.get("width");
		String theight = (String) tableinfo.get("height");
		String tboder = (String) tableinfo.get("boder");
		String ttop = (String) tableinfo.get("top");
		String tleft = (String) tableinfo.get("left");
		String tisselect = (String) tableinfo.get("isselect");
		String tselectstyle = "";
		if ("true".equals(tisselect)) {
			tselectstyle = (String) tableinfo.get("selectstyle");
		}
		for (int j = 0; j < tablecolJson.size(); j++) {
			jsonObject = (JSONObject) tablecolJson.get(j);
			String id = jsonObject.get("id").toString().toUpperCase();
			String title = (String) jsonObject.get("title");
			String width = (String) jsonObject.get("width");
			if (id == null || id.equals("")) {
				System.out.println("未设置列id，请重新设置!");
				return null;
			}
			if (title == null || title.equals("")) {
				title = "请输入标题名";
			}
			if (width == null || width.equals("")) {
				title = "60";
			}
			TableColumn tableColu = new TableColumn<>(title);
			tableColu.setCellValueFactory(new MapValueFactory(id));
			tableColu.setCellFactory(new Callback<TableColumn, TableCell>() {
				public TableCell call(TableColumn param) {
					return new TableCell<String, String>() {
						ObservableValue ov;

						@Override
						public void updateItem(String item, boolean empty) {
							super.updateItem(item, empty);
							if (!empty) {
								ov = getTableColumn().getCellObservableValue(
										getIndex());
								if (getTableRow() != null && !ov.equals("")) {
									Map<String, String> vmap = (Map<String, String>) this
											.getTableRow().getItem();
									boolean colorFlag = false;
									if (vmap.get("TEXT_COLOR") != null) {
										if (!vmap.get("TEXT_COLOR").equals("")) {
											this.setStyle("-fx-text-fill: "
													+ vmap.get("TEXT_COLOR"));
											colorFlag = true;
										}
									}
									if (vmap.get("BACKGROUND_COLOR") != null) {
										if (!vmap.get("BACKGROUND_COLOR")
												.equals("")) {
											if (!colorFlag) {
												this.setStyle("-fx-background-color: "
														+ vmap.get("BACKGROUND_COLOR"));
											} else {
												this.setStyle("-fx-background-color: "
														+ vmap.get("BACKGROUND_COLOR")
														+ ";"
														+ "-fx-text-fill: "
														+ vmap.get("TEXT_COLOR"));
											}
										}
									}
								}
								setText(item);
							}
						}
					};
				}
			});
			tableColu.setPrefWidth(Double.valueOf(width));
			LogCateTableView.getColumns().add(tableColu);
		}
		if (ttop == null || ttop.equals("")) {
			ttop = "190";
		}
		if (tleft == null || tleft.equals("")) {
			ttop = "20";
		}
		AnchorPane.setTopAnchor(LogCateTableView, Double.valueOf(ttop));
		AnchorPane.setLeftAnchor(LogCateTableView, Double.valueOf(tleft));
		return LogCateTableView;
	}

	public static class TableData {
		SimpleStringProperty Level, Time, Application, Tag, Text;

		public TableData(LogCate logCate) {
			this.Level = new SimpleStringProperty(
					LogCateProvider.getLevel(logCate.getLeve()));
			this.Time = new SimpleStringProperty(new Date().toString());
			this.Application = new SimpleStringProperty(
					logCate.getApplication());
			this.Tag = new SimpleStringProperty(logCate.getTag());
			this.Text = new SimpleStringProperty(logCate.getText());
		}

		public String getLevel() {
			return Level.get();
		}

		public void setLevel(String levelColumn) {
			this.Level.set(levelColumn);
		}

		public String getTime() {
			return Time.get();
		}

		public void setTime(String timeColumn) {
			this.Time.set(timeColumn);
		}

		public String getApplication() {
			return Application.get();
		}

		public void setApplication(String application) {
			this.Application.set(application);
		}

		public String getTag() {
			return Tag.get();
		}

		public void setTag(String tag) {
			this.Tag.set(tag);
		}

		public String getText() {
			return Text.get();
		}

		public void setText(String text) {
			this.Text.set(text);
		}

	}
}
