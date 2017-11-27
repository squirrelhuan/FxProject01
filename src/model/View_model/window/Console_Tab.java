package model.View_model.window;

import java.time.LocalTime;

import model.View_model.LogCate;
import model.View_model.TabBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import application.Test;

public class Console_Tab extends TabBase {
	Parent parent;
	private static Console_Tab console_Tab = new Console_Tab("Console",
			Test.getInstance());

	public static Console_Tab getInstance() {
		return console_Tab;
	}

	private Console_Tab(String str, Test test) {
		super(str, test);
	}

	@Override
	public void onCreatView() {

		AnchorPane anchorpane = new AnchorPane();
		parent = myResource.getConsole_View();
		AnchorPane.setTopAnchor(parent, 0.0);
		AnchorPane.setLeftAnchor(parent, 0.0);
		AnchorPane.setRightAnchor(parent, 0.0);
		AnchorPane.setBottomAnchor(parent, 0.0);
		anchorpane.getChildren().addAll(parent);
		this.setContent(anchorpane);
		this.setOnClosed(new EventHandler<Event>() {
			@Override
			public void handle(final Event event) {
				System.out.println("setOnClosed");
				event.consume();
			}
		});

		// ((Label) parent).setText("About MyTool");
	}

}
