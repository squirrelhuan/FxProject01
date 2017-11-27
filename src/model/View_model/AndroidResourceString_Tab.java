package model.View_model;

import java.io.File;

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

public class AndroidResourceString_Tab extends TabBase {
	Parent edit_Txt;
	private File file;

	public AndroidResourceString_Tab(String str, Test test) {
		super(str, test);
	}

	@Override
	public void onCreatView() {

		AnchorPane anchorpane = new AnchorPane();
		edit_Txt = myResource.getAndroidResourceString_View();
		AnchorPane.setTopAnchor(edit_Txt, 0.0);
		AnchorPane.setLeftAnchor(edit_Txt, 0.0);
		AnchorPane.setRightAnchor(edit_Txt, 0.0);
		AnchorPane.setBottomAnchor(edit_Txt, 0.0);
		anchorpane.getChildren().addAll(edit_Txt);
		this.setContent(anchorpane);
		this.setOnClosed(new EventHandler<Event>() { @Override
		        public void handle(final Event event) {
			System.out.println("setOnClosed");
			event.consume();
			}
		}
		);
		//((Label) edit_Txt).setText("About MyTool");
	}
}
