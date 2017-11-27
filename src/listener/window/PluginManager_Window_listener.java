package listener.window;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import application.APK_Setting_Dialog;
import application.Test;
import event.Console.ConsoleEvent;
import event.Console.ConsoleManager;
import event.Console.IConsoleListener;
import event.LogCateEvent.LogCateEvent;
import model.Net_model.NetClient;
import model.View_model.TabBase;
import model.plugin_model.Plugin;
import test.plugin.MainTest;
import test.plugin.PluginManager;
import util.MySystem;
import util.XMLParser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class PluginManager_Window_listener {

	@FXML
	public ListView<Plugin> user_online_ListView = new ListView<Plugin>();
	private static ArrayList<Plugin> userDaoList = new ArrayList<Plugin>();
	public static ObservableList<Plugin> observableList;

	Plugin plugin = null;
	/**
	 * add
	 */
	@FXML
	private void onClickedAddButton(ActionEvent event) {
		MySystem.out.println("手动添加插件");
		PluginManager pluginManager = PluginManager.getInstance();
		try {
			plugin = pluginManager.addPlugin(Test.getInstance(),this);

			/* 初始化插件 */
			if (plugin != null) {
				MenuItem menuItem = new MenuItem(plugin.getName());
				menuItem.setOnAction((ActionEvent e) -> {
					MainTest mainTest = new MainTest();
					MySystem.out.println("插件" + plugin.getName() + "正在加载。。。");
					try {
						mainTest.singletest(plugin);
						MySystem.out.println("插件加载完成");
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
						MySystem.out.println("插件加载异常：" + e2.getMessage());
					}
				});
				Test.getInstance().menu_plugins.getItems().add(menuItem);
			}
			initialize();
		} catch (Exception e) {

		}
	}
	/**
	 * delete
	 */
	@FXML
	private void onClickedDeleteButton(ActionEvent event) {
		MySystem.out.println("delete");
		if(plugin_c!=null){
		MySystem.out.println(plugin_c.getJar());
		try {
			PluginManager.getInstance().remove(plugin_c);
			/*ArrayList<MenuItem> plugins = Test.getInstance().menu_plugins.getItems();
			Test.getInstance().menu_plugins.getItems().remove(plugin_c);*/
			initialize();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	private Plugin plugin_c;
	@FXML
	private void initialize() {
	Platform.runLater(new Runnable() {
		@Override
		public void run() {
			try {
				userDaoList = (ArrayList<Plugin>) XMLParser.getPluginList();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			observableList = FXCollections
					.observableArrayList(userDaoList);
			user_online_ListView.setItems(observableList);
			user_online_ListView.setCellFactory((
					ListView<Plugin> l) -> null);
			user_online_ListView.setCellFactory((
					ListView<Plugin> l) -> new ColorRectCell());
		}
	});
	}
	
	class ColorRectCell extends ListCell<Plugin> {

		@Override
		public void updateItem(Plugin item, boolean empty) {
			super.updateItem(item, empty);
			// Rectangle rect = new Rectangle(100, 20);
			// rect.setFill(Color.web(item));
			if (item != null) {
				
				Label label = new Label(item.getName() + " ("
						+ item.getJar() + ")");
				final ContextMenu cm = new ContextMenu();
				MenuItem cmItem1 = new MenuItem("install apk");
				cmItem1.setOnAction((ActionEvent e) -> {
					/*
					 * Clipboard clipboard = Clipboard.getSystemClipboard();
					 * ClipboardContent content = new ClipboardContent();
					 * //content.putImage(pic.getImage());
					 * clipboard.setContent(content);
					 */
					
					
				});
				MenuItem cmItem2 = new MenuItem("apk path");
				cmItem2.setOnAction((ActionEvent e) -> {
					new APK_Setting_Dialog().start(new Stage());
				});
				cm.getItems().add(cmItem1);
				cm.getItems().add(cmItem2);

				FlowPane flowPane = new FlowPane();
				flowPane.getChildren().add(label);
				/*flowPane.getChildren().add(checkbox);
				flowPane.getChildren().add(label);
				flowPane.addEventHandler(
						MouseEvent.MOUSE_CLICKED,
						(MouseEvent e) -> {
							if (e.getButton() == MouseButton.SECONDARY) {
								cm.show(flowPane.getParent(), e.getScreenX(),
										e.getScreenY());
							}
						});*/
				flowPane.addEventHandler(
						MouseEvent.MOUSE_CLICKED,
						(MouseEvent e) -> {
							if (e.getButton() == MouseButton.PRIMARY) {
								plugin_c = item;
							}
						});
				// flowPane.setOnMouseClicked(value);
				setGraphic(flowPane);
			}
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
