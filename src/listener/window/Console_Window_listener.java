package listener.window;

import java.util.ArrayList;
import java.util.List;

import event.Console.ConsoleEvent;
import event.Console.ConsoleManager;
import event.Console.IConsoleListener;
import event.LogCateEvent.LogCateEvent;
import model.View_model.TabBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class Console_Window_listener implements IConsoleListener {

	@FXML
	public TextArea Cosole_TextArea;
	/** 控制台字符串行数 */
	public static int Cosole_lineCount = 0;

	/**
	 * 清空控制台
	 */
	@FXML
	private void onClickedClipboardDeleteButton(ActionEvent event) {
		// Cosole_TextArea.setText("");
	}

	/** 表格初始化 */
	@FXML
	private void initialize() {
		ConsoleManager.getInstance().addDoorListener(this);// 给门1增加监听器
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
	public void pushFileEvent(ConsoleEvent event) {
		switch (event.getEventType()) {
		case 0:// 添加
			Cosole_TextArea.appendText(event.getMessage());
			break;
		case 1:// 清空
			Cosole_TextArea.setText("");
			break;
		default:
			break;
		}
		/*Cosole_TextArea.appendText(event.getMessage());
		if (event.getEventType() == LogCateEvent.LogCateEventType.onLogsChanged) {

		} else if (event.getEventType() == LogCateEvent.LogCateEventType.onTagsChanged) {

		}*/
	}
}
