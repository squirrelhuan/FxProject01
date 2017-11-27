package model.View_model.jps;

import application.Test;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import model.View_model.TabBase;

import java.io.File;

public class AndroidGetDescription_Tab extends TabBase {
	Parent edit_Txt;
	private File file;

	public AndroidGetDescription_Tab(String str, Test test) {
		super(str, test);
	}

	@Override
	public void onCreatView() {

		AnchorPane anchorpane = new AnchorPane();
		edit_Txt = myResource.getAndroidGetDescription_View();
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
