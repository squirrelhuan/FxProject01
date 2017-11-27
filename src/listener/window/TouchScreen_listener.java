package listener.window;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseListener;
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

import test.MyTouchScreen_javafx;
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

public class TouchScreen_listener extends ListenerBase implements MouseListener{

	@FXML
	private TextField textField_X, textField_Y, textField_Time;

	private int x, y, time = 3000;

	/** 表格初始化 */
	@FXML
	private void initialize() {
		// 初始化设置选项列表
		x = MyTouchScreen_javafx.pc_X;
		textField_X.setText("" + MyTouchScreen_javafx.pc_X);
		y = MyTouchScreen_javafx.pc_Y;
		textField_Y.setText("" + MyTouchScreen_javafx.pc_Y);
		textField_Time.setText("" + time);
	}

	private boolean isRunning = true;
	/** 清空 */
	@FXML
	public void onClickedOkButton(ActionEvent event) {
		Robot robot;
		try {
			robot = new Robot();

			robot.mouseMove(x, y);
			robot.delay(time);
			isRunning = true;
			while (isRunning) {
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.delay(time);
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 清空 */
	@FXML
	public void onClickedCancleButton(ActionEvent event) {

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

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		MySystem.out.println(e.getClickCount());
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
