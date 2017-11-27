package model.View_model;

import java.awt.event.ActionListener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import application.Test;

public class CaptureScreen_Tab extends TabBase {
	public CaptureScreen_Tab(String str, Test test) {
		super(str, test);
		// TODO Auto-generated constructor stub
	}

	Parent edit_Txt;

	@Override
	public void onCreatView() {

		AnchorPane anchorpane = new AnchorPane();
		edit_Txt = myResource.getCaptureScreen_View();
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
	}
}
