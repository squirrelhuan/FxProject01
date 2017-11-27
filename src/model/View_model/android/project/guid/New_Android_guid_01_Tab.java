package model.View_model.android.project.guid;

import java.io.File;

import model.View_model.TabBase;
import util.FileTools;
import util.MySystem;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.Test;

public class New_Android_guid_01_Tab extends TabBase {
	Parent parent_root;
	private File file;

	public New_Android_guid_01_Tab(String str, Test test) {
		super(str, test);
	}

	@Override
	public void onCreatView() {

		AnchorPane anchorpane = new AnchorPane();
		parent_root = myResource.getNewAndroidProject_guid_01_View();
		AnchorPane.setTopAnchor(parent_root, 0.0);
		AnchorPane.setLeftAnchor(parent_root, 0.0);
		AnchorPane.setRightAnchor(parent_root, 0.0);
		AnchorPane.setBottomAnchor(parent_root, 0.0);
		anchorpane.getChildren().addAll(parent_root);
		this.setContent(anchorpane);
		this.setOnClosed(new EventHandler<Event>() { @Override
		        public void handle(final Event event) {
			System.out.println("setOnClosed");
			event.consume();
			}
		}
		);
	}
}
