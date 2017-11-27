package application;
	
import fxml.MyResource;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;


public class UdpServer {
	static MyResource myResource ;

	public UdpServer() {
		myResource = new MyResource();
	}

	public void addUdpServeTab(TabPane tabPane) {
		
		Tab tab = new Tab("新建文本");
    	AnchorPane anchorpane = new AnchorPane();
    	Parent edit_Txt = myResource.getNewText_View();
        AnchorPane.setTopAnchor(edit_Txt, 0.0);
        AnchorPane.setLeftAnchor(edit_Txt, 0.0);
        AnchorPane.setRightAnchor(edit_Txt, 0.0);
        AnchorPane.setBottomAnchor(edit_Txt, 0.0);
        anchorpane.getChildren().addAll(edit_Txt);
        tab.setContent(anchorpane);
    	tabPane.getTabs().add(tab);
    	tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
	}
	
	
}
