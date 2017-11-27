package model.View_model;

import application.Test;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;

public class Common_Tab extends TabBase {
	Parent edit_Txt;
	private File file;
	private AnchorPane rootView;

	private static Common_Tab instance;

	public static Common_Tab getInstance(String str, Test test){
		if (instance == null){
			instance = new Common_Tab( str, test);
		}
		return instance;
	}

	public Common_Tab(String str, Test test) {
		super(str, test);
	}

	@Override
	public void onCreatView() {

		rootView = new AnchorPane();
		edit_Txt = myResource.getAbout_View();
		AnchorPane.setTopAnchor(edit_Txt, 0.0);
		AnchorPane.setLeftAnchor(edit_Txt, 0.0);
		AnchorPane.setRightAnchor(edit_Txt, 0.0);
		AnchorPane.setBottomAnchor(edit_Txt, 0.0);
		//rootView.getChildren().addAll(edit_Txt);
		this.setContent(rootView);
		this.setOnClosed(new EventHandler<Event>() { @Override
		        public void handle(final Event event) {
			System.out.println("setOnClosed");
			event.consume();
			}
		}
		);
		//((Label) edit_Txt).setText("\n\r About MyTool \n\r Author: squirrel桓 \n\r Email: 978252909@qq.com \n\r CreatTime: 2016/05/01 \n\r Motify: 2017/6/26 \n\r Version: 0.1.1");
	
		//((Label) edit_Txt).setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		//((Label) edit_Txt).setFill(Color.RED);
	}

	public Parent getRootView() {
		return rootView;
	}

	public void setcontentView(Parent contentView) {
		rootView.getChildren().add(contentView);
	}
}
