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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.Test;

public class About_Tab extends TabBase {
	Parent edit_Txt;
	private File file;

	private static About_Tab instance;
	
	public static About_Tab getInstance(String str, Test test){
		if (instance == null){
			instance = new About_Tab( str, test);
		}
		return instance;
	}
	
	public About_Tab(String str, Test test) {
		super(str, test);
	}

	@Override
	public void onCreatView() {

		AnchorPane anchorpane = new AnchorPane();
		edit_Txt = myResource.getAbout_View();
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
		((Label) edit_Txt).setText("\n\r About MyTool \n\r Author: squirrel桓 \n\r Email: 978252909@qq.com \n\r CreatTime: 2016/05/01 \n\r Motify: 2017/6/26 \n\r Version: 0.1.1");
	
		((Label) edit_Txt).setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		//((Label) edit_Txt).setFill(Color.RED);
	}
}
