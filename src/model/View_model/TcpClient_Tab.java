package model.View_model;

import application.Test;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class TcpClient_Tab extends TabBase {

	public TcpClient_Tab(String str, Test test) {
		super(str, test);
	}

	@Override
	public void onCreatView() {
		AnchorPane anchorpane = new AnchorPane();
    	Parent edit_Txt = myResource.getTcpClient_View();
        AnchorPane.setTopAnchor(edit_Txt, 0.0);
        AnchorPane.setLeftAnchor(edit_Txt, 0.0);
        AnchorPane.setRightAnchor(edit_Txt, 0.0);
        AnchorPane.setBottomAnchor(edit_Txt, 0.0);
        anchorpane.getChildren().addAll(edit_Txt);
        this.setContent(anchorpane);
		
	}
	
}
