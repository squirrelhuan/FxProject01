package listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.JDOMException;

import application.APK_Setting_Dialog;
import provider.LogCateProvider;
import model.Net_model.NetClient;
import model.View_model.TabBase;
import model.resource.StringModel;
import test.PullParserService;
import test.http.HttpRequest;
import test.xml.Java2XML;
import util.FileTools;
import util.MySystem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * android资源文件String 编辑器
 * 
 * @author CGQ
 *
 */
public class AndroidResourceString_listener {

	private TableColumn<StringModel, String> KeyColumn;
	private TableColumn<StringModel, String> ValueColumn_CH;
	private TableColumn<StringModel, String> ValueColumn_EN;
	@FXML
	public TableView<StringModel> StringTableView;
	private ObservableList<StringModel> personData = FXCollections
			.observableArrayList();
	List<StringModel> logs = new ArrayList<StringModel>();

	/** 表格初始化 */
	@FXML
	private void initialize() {
		personData.addAll(logs);

		KeyColumn = new TableColumn<StringModel, String>("Key");
		KeyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		KeyColumn.setCellValueFactory(cellData -> cellData.getValue()
				.keyProperty());
		ValueColumn_CH = new TableColumn<StringModel, String>("CH");
		ValueColumn_CH.setCellFactory(TextFieldTableCell.forTableColumn());
		ValueColumn_CH.setCellValueFactory(cellData -> cellData.getValue()
				.valueCHProperty());

		ValueColumn_EN = new TableColumn<StringModel, String>("EN");
		ValueColumn_EN.setCellFactory(TextFieldTableCell.forTableColumn());
		ValueColumn_EN.setCellValueFactory(cellData -> cellData.getValue()
				.valueENProperty());

		/*
		 * ValueColumn.setCellFactory(
		 * 
		 * new Callback<TableColumn<StringModel,String>, TableCell<StringModel,
		 * String>>() {
		 * 
		 * public TableCell<StringModel,String> call(TableColumn<StringModel,
		 * String>param) {
		 * 
		 * TextFieldTableCell<StringModel,String> cell= new
		 * TextFieldTableCell<StringModel,String>(); cell.setOnMouseClicked(new
		 * EventHandler<Event>() {
		 * 
		 * @Override public void handle(Event event) { // TODO Auto-generated
		 * method stub
		 * 
		 * } }); return cell;
		 * 
		 * }
		 * 
		 * });
		 */
		// textColumn = getColumn("columnName", "key", 100, 500);
		// textColumn.setCellFactory(new TaskCellFactory());
		// TextColumn.setCellFactory(TextFieldTableCell.<StringModel>forTableColumn());

		// textColumn.setMinWidth(50);
		/*
		 * TextColumn.setCellValueFactory(new PropertyValueFactory("invited"));
		 * TextColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
		 * public TableCell call( TableColumn p) {
		 * 
		 * return new CheckBoxTableCell(); } });
		 */
		StringTableView.getColumns().add(KeyColumn);
		StringTableView.getColumns().add(ValueColumn_CH);
		StringTableView.getColumns().add(ValueColumn_EN);
		StringTableView.setItems(personData);

		StringTableView.getSelectionModel().setSelectionMode(
				SelectionMode.MULTIPLE);
		StringTableView.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue ov, Object t, Object t1) {
						int index = StringTableView.getSelectionModel()
								.getSelectedIndex();
						MySystem.out.println(index);
						switch (index) {
						case 1: // 所有任务
							// refreshTableData(0, 1, 2);
							break;
						}
					}
				});
		final ContextMenu cm = new ContextMenu();
		MenuItem cmItem1 = new MenuItem("copy");
		cmItem1.setOnAction((ActionEvent e) -> {
			/*
			 * Clipboard clipboard = Clipboard.getSystemClipboard();
			 * ClipboardContent content = new ClipboardContent();
			 * //content.putImage(pic.getImage());
			 * clipboard.setContent(content);
			 */
		});
		MenuItem cmItem2 = new MenuItem("delete");
		cmItem2.setOnAction((ActionEvent e) -> {
			MySystem.out.println("e:" + e.getSource());
			ObservableList<StringModel> cData = StringTableView
					.getSelectionModel().getSelectedItems();
			personData.removeAll(cData);
		});
		cm.getItems().add(cmItem1);
		cm.getItems().add(cmItem2);
		StringTableView.addEventHandler(
				MouseEvent.MOUSE_CLICKED,
				(MouseEvent e) -> {
					if (e.getButton() == MouseButton.SECONDARY) {
						cm.show(StringTableView.getParent(), e.getScreenX(),
								e.getScreenY());
					}
				});
	}

	private TableColumn<StringModel, String> getColumn(String columnName,
			String propertyName, int width, int maxWidth) {
		TableColumn<StringModel, String> tableColumn = new TableColumn<>(
				columnName);
		tableColumn.setCellFactory(new TaskCellFactory());
		tableColumn
				.setCellValueFactory(new PropertyValueFactory<>(propertyName));
		tableColumn.setMinWidth(width);
		tableColumn.setPrefWidth(width);
		tableColumn.setMaxWidth(maxWidth);
		return tableColumn;
	}

	/** 清空 */
	@FXML
	public void onClearStringButtonClicked(ActionEvent event) {
		personData.clear();
		StringTableView.setItems(personData);
	}

	/** 新建tag */
	@FXML
	public void onAddStringButtonClicked(ActionEvent event) {
		personData.add(new StringModel("dd", "cc", "dd"));
		// StringTableView.setItems(personData);
		MySystem.out.println("add...");
	}

	/** 保存文件 zh*/
	@FXML
	public void onSaveStringButtonClicked(ActionEvent event) {
		try {
			Java2XML.BuildXMLDoc(personData,0);//保存成中文
			MySystem.out.println("count:" + personData.size());
			MySystem.out.println("save success");
		} catch (IOException | JDOMException e) {
			MySystem.out.println("save failed reason:" + e.getMessage());
			e.printStackTrace();
		}
	}
	/** 保存文件en */
	@FXML
	public void onSaveStringButtonClickedEN(ActionEvent event) {
		try {
			Java2XML.BuildXMLDoc(personData,1);//保存成英文
			MySystem.out.println("count:" + personData.size());
			MySystem.out.println("save success");
		} catch (IOException | JDOMException e) {
			MySystem.out.println("save failed reason:" + e.getMessage());
			e.printStackTrace();
		}
	}

	private File file;

	/** 读取文本 */
	@FXML
	public void importXmlFile1() {
		openFile(1);
	}

	/** 读取文本 */
	@FXML
	public void importXmlFile2() {
		openFile(2);
	}

	/** 一件翻译 */
	@FXML
	public void onTranslateAllButtonClicked() {
		HttpRequest mRequest = new HttpRequest();
		mRequest.translateAll(personData);
	}

	public void openFile(int type) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开文件");
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("文本文档", "*.xml"),
				new FileChooser.ExtensionFilter("所有文件", "*.*"));

		file = fileChooser.showOpenDialog(StringTableView.getScene()
				.getWindow());
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("文本文档", "*.xml"),
				new FileChooser.ExtensionFilter("所有文件", "*.*"));

		if (file != null) {
			FileInputStream fInputStream;
			try {
				fInputStream = new FileInputStream(file);
				List<StringModel> stringModels = Java2XML.ParseXml(
						fInputStream, type);
				if (type == 2) {// 如果要添加英文对比
					int length = personData.size();
					for (StringModel model : stringModels) {
						boolean isExit = false;
						for (int i = 0; i < length; i++) {
							if (model.getKey().trim()
									.equals(personData.get(i).getKey().trim())) {
								isExit = true;
								personData.get(i)
										.setValueEN(model.getValueEN());
								break;
							}
						}
						if (!isExit) {
							personData.add(model);
						}
					}
				} else {
					personData.addAll(stringModels);
				}
				StringTableView.setItems(personData);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String filename = file.getName();
			/*
			 * this.setText(filename); ((TextArea) left_edit_Txt)
			 * .setText(FileTools.readFileWithLine(file));
			 */
			MySystem.out.println(filename + "已打开！");
		}
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

class TaskCellFactory
		implements
		Callback<TableColumn<StringModel, String>, TableCell<StringModel, String>> {

	@Override
	public TableCell<StringModel, String> call(
			TableColumn<StringModel, String> param) {
		TextFieldTableCell<StringModel, String> cell = new TextFieldTableCell<>();
		cell.setOnMouseClicked((MouseEvent t) -> {
			if (t.getClickCount() == 2) {
				MySystem.out.println("double clicked");
				cell.setEditable(true);
				// view();
			}
		});
		cell.setText("sss");
		// cell.setContextMenu(taskContextMenu);
		return cell;
	}
}
