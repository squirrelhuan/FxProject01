package listener.window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.util.TempFile;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import test.xml.Java2XML;
import test.xml.Java2XML_Setting;
import util.MySystem;
import util.PropertiesUtil;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.util.Callback;
import listener.ListenerBase;
import model.Net_model.NetClient;
import model.View_model.TabBase;
import model.View_model.AndroidAnnotation_Tab.MyTreeItem;
import model.resource.StringModel;
import model.setting.SettingMenu;

public class Setting_listener extends ListenerBase {
	@FXML
	private AnchorPane left_panle;
	@FXML
	private AnchorPane right_panle;
	@FXML
	private Label right_title;
	HBox right_content;

	private static TreeTableView<String> fileTree;
	private static HashMap<String, String> hashMap_setting;// 当前页面内的key与value集合

	/** 表格初始化 */
	@FXML
	private void initialize() {
		// 初始化设置选项列表
		// try {
		FileInputStream fInputStream;
		File file = null;
		InputStream in = getClass().getClassLoader().getResourceAsStream("setting.xml");
		// fInputStream = (FileInputStream) in;
		// fInputStream = new FileInputStream(file);
		SettingMenu menuItme = null;
		// menuItme = Java2XML_Setting.ParseXml(in);
		menuItme = Java2XML_Setting.parseFile3(in);
		// menuItme.setName("Setting");
		menuItme.setTitle("设置");
		root = new MyTreeItem<String>();
		root.getChildren().clear();
		// 读取默认配置文件
		readAllSetting(root, menuItme);

		fileTree = initLeftView();

		AnchorPane.setTopAnchor(fileTree, 5.0);
		AnchorPane.setLeftAnchor(fileTree, 0.0);
		AnchorPane.setRightAnchor(fileTree, 0.0);
		AnchorPane.setBottomAnchor(fileTree, 0.0);
		left_panle.getChildren().add(fileTree);

		right_content = new HBox();
		AnchorPane.setTopAnchor(right_content, 5.0);
		AnchorPane.setLeftAnchor(right_content, 5.0);
		AnchorPane.setRightAnchor(right_content, 5.0);
		AnchorPane.setBottomAnchor(fileTree, 5.0);
		right_content.setAlignment(Pos.BASELINE_RIGHT);
		right_panle.getChildren().add(right_content);
		// 读取配置文件
		PropertiesUtil.readProperties("setting.properties");
	}

	/** 清空 */
	@FXML
	public void onClickedOkButton(ActionEvent event) {
		modifyValues();
	}

	//
	public void modifyValues() {
		Set set = hashMap_setting.entrySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Object> enter = (Map.Entry<String, Object>) iter.next();
			PropertiesUtil.setProperty(enter.getKey(), (String) enter.getValue());
			MySystem.out.println("修改了" + enter.getKey() + ":" + PropertiesUtil.getProperty(enter.getKey()));
		}
	}

	private ImageView depIcon = new ImageView(
			getClass().getClass().getResource("/fxml/image/folder_icon_16.png").toString());

	/**
	 * 读取setting.xml配置文件
	 */
	public boolean readAllSetting(MyTreeItem<String> rootTreeItem, SettingMenu menuItme) {
		if (!menuItme.isDirectory()) {
			rootTreeItem.getChildren().add(new MyTreeItem<String>(menuItme.getName(), menuItme));
		} else if (menuItme.isDirectory()) {
			MyTreeItem<String> treeItem = new MyTreeItem<String>(menuItme.getName(), menuItme/* , depIcon */);
			if (rootTreeItem.getName() == null) {
				root = treeItem;
			} else {
				rootTreeItem.getChildren().add(treeItem);
			}
			if (menuItme.getName().equals("Setting")) {
				treeItem.expandedProperty().set(true);
			}
			ArrayList<SettingMenu> Menulist = (ArrayList<SettingMenu>) menuItme.getItems();
			for (int i = 0; i < Menulist.size(); i++) {
				SettingMenu menuc = Menulist.get(i);
				readAllSetting(treeItem, menuc);
			}
		}
		return true;
	}

	// Creating the root element
	private static MyTreeItem<String> root;

	public TreeTableView<String> initLeftView() {
		// Creating tree items
		MyTreeItem<String> childNode1 = new MyTreeItem<>("Child Node 1");
		MyTreeItem<String> childNode2 = new MyTreeItem<>("Child Node 2");
		MyTreeItem<String> childNode3 = new MyTreeItem<>("Child Node 3");

		// root = new MyTreeItem<>("Setting", ""/* ,depIcon */);
		root.setExpanded(true);

		// Adding tree items to the root
		// root.getChildren().setAll(childNode1, childNode2, childNode3);

		// Creating a column
		TreeTableColumn<String, String> column = new TreeTableColumn<>("Column");
		column.setPrefWidth(150);
		// column.setVisible(false);

		column.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<String, String> param) {
						// TODO Auto-generated method stub
						// System.out.println("onClick");
						return new ReadOnlyStringWrapper(param.getValue().getValue());
					}
				});
		column.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			System.out.println("onClicked");
		});
		// Defining cell content
		// column.setCellValueFactory((CellDataFeatures<String, String> p) ->
		// new ReadOnlyStringWrapper(
		// p.getValue().getValue()));
		// root.setCellFactory((
		// ListView<NetClient> l) -> new ColorRectCell());
		// right_title

		// Creating a tree table view
		final TreeTableView<String> treeTableView = new TreeTableView<>(root);
		treeTableView.getColumns().add(column);
		treeTableView.setPrefWidth(180);
		treeTableView.setShowRoot(false);
		// 支持多选
		treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		treeTableView.getSelectionModel().setCellSelectionEnabled(true);
		treeTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				int index = treeTableView.getSelectionModel().getSelectedIndex();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (t1 != null) {
							// 更改当前页面ui
							SettingMenu menu = ((MyTreeItem) t1).getSettingMenu();
							switch (menu.getType()) {
							case 0://简单视图
								right_title.setText(treeTableView.getSelectionModel().getSelectedItem().getValue());
								right_content.getChildren().clear();
								Label label = new Label(menu.getTitle());
								TextField textField = new TextField(PropertiesUtil.getProperty(menu.getKey(), "没有配置"));
								textField.textProperty().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										// 更改当前页面数据
										hashMap_setting = new HashMap<String, String>();
										hashMap_setting.put(menu.getKey(), newValue);
									}
								});
								right_content.getChildren().add(label);
								right_content.getChildren().add(textField);
								break;
							case 1://引入复杂视图
                                MySystem.out.println("复杂视图");
                                right_title.setText(treeTableView.getSelectionModel().getSelectedItem().getValue());
								right_content.getChildren().clear();
                                
                                Parent parent;
                                parent = myResource.getPluginManager_View();
                        		AnchorPane.setTopAnchor(parent, 0.0);
                        		AnchorPane.setLeftAnchor(parent, 0.0);
                        		AnchorPane.setRightAnchor(parent, 0.0);
                        		AnchorPane.setBottomAnchor(parent, 0.0);
                                right_content.getChildren().add(parent);
								break;

							default:
								break;
							}
							

						}
					}
				});
			}
		});
		return treeTableView;
	}

	public class MyTreeItem<T> extends TreeItem {
		private String name;
		private SettingMenu settingMenu;

		public MyTreeItem(T value, String path, Node graphic) {
			super(value, graphic);
			this.name = path;
		}

		public MyTreeItem(T value, String path) {
			super(value);
			this.name = path;
		}

		public MyTreeItem() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MyTreeItem(T value, Node graphic) {
			super(value, graphic);
			// TODO Auto-generated constructor stub
		}

		public MyTreeItem(T value) {
			super(value);
			// TODO Auto-generated constructor stub
		}

		public MyTreeItem(T t, SettingMenu menu) {
			super(t);
			this.name = menu.getName();
			this.settingMenu = menu;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public SettingMenu getSettingMenu() {
			return settingMenu;
		}

		public void setSettingMenu(SettingMenu settingMenu) {
			this.settingMenu = settingMenu;
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
			((TabBase) event.getSource()).setTooltip(new Tooltip(((TabBase) event.getSource()).getDescription()));
		} else {
			((Control) event.getSource()).setTooltip(new Tooltip("清空控制台"));
		}
	}

}
