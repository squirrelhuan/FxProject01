package listener.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import listener.ListenerBase;
import model.View_model.TabBase;

public class HtmlEditor_listener extends ListenerBase {
	@FXML
	private HTMLEditor htmlEditor;
	

	/** 表格初始化 */
	@FXML
	private void initialize() {
		
	}

	/** 清空 */
	@FXML
	public void onClickedPlayButton(ActionEvent event) {
	
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
