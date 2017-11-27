package model.View_model;

import fxml.MyResource;
import application.Test;
import javafx.scene.control.Tab;

public abstract class TabBase extends Tab implements TabBaseInterface{
	private static String description = "tabs=";
	public static MyResource myResource = MyResource.getInstance();
	public static Test test;
	@SuppressWarnings("static-access")
	public TabBase(String str,Test test) {
		super(str);
		this.test = test;
		onCreatView();
	}

	@Override
	public void onCreatView() {
		// TODO Auto-generated method stub
		
	}

	public static String getDescription() {
		return description;
	}

	public static void setDescription(String description) {
		TabBase.description = description;
	}

	/*@Override
	public void setOnCloseRequest(EventHandler<Event> arg0) {
		
		//Event.consume();
		System.out.println("closed");
		//JOptionPane.showMessageDialog(null, "你好");
		//super.setOnCloseRequest(arg0);
	}*/
	
}
