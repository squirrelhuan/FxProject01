package model.View_model.window;

import java.time.LocalTime;

import fxml.MyResource;
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

public class Bottom_Window extends Parent {
	Parent parent;
	public static MyResource myResource = MyResource.getInstance();
	public Bottom_Window(String str, Test test) {
		//super(str, test);
		/*AnchorPane anchorpane = new AnchorPane();*/
		parent = myResource.getBottom_Window_View();
		/*AnchorPane.setTopAnchor(parent, 0.0);
		AnchorPane.setLeftAnchor(parent, 0.0);
		AnchorPane.setRightAnchor(parent, 0.0);
		AnchorPane.setBottomAnchor(parent, 0.0);
		anchorpane.getChildren().addAll(parent);*/
		
	}

}
