package fxml;
	
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class MyResource {
	private static MyResource myResource = new MyResource();
	public static MyResource getInstance(){
		return myResource;
	}
	
	public Parent getMain_View() {
		return getView("Main_View1.fxml");
	}
	public Parent getBottom_Window_View() {
		return getView("Bottom_Window_View.fxml");
	}
	public Parent getDialog_View() {
		return getView("Dialog_View1.fxml");
	}
	public Parent getAbout_View() {
		return getView("About_View.fxml");
	}
	public Parent getHtmlEditor_View() {
		return getView("HtmlEditor_View.fxml");
	}
	public Parent getLogCate_View() {
		return getView("LogCate_View.fxml");
	}
	public Parent getConsole_View() {
		return getView("Console_View.fxml");
	}
	public Parent getPluginManager_View() {
		return getView("PluginManager_View.fxml");
	}
	public Parent getProgress_View() {
		return getView("Progress_View.fxml");
	}
	public Parent getLogCate_AddTag_View() {
		return getView("LogCate_AddTag_View.fxml");
	}
	public Parent getApk_Setting_View() {
		return getView("Apk_Setting_View.fxml");
	}
	public Parent getMediaPlayer_View() {
		return getView("MediaPlayer_View.fxml");
	}
	public Parent getMediaCamera_View() {
		return getView("MediaCamera_View.fxml");
	}
	public Parent getSetting_View() {
		return getView("Setting_View.fxml");
	}
	public Parent getTouchScreen_View() {
		return getView("TouchScreen_View.fxml");
	}
	public Parent getCaptureScreen_View() {
		return getView("CaptureScreen_View1.fxml");
	}
	public Parent getNewText_View() {
		return getView("NewText_View.fxml");
	}
	public Parent getNewAndroidProject_guid_01_View() {
		return getView("NewAndroidProject_guid_01_View.fxml");
	}
	public Parent getNewAndroidProject_guid_02_View() {
		return getView("NewAndroidProject_guid_02_View.fxml");
	}
	public Parent getNewAndroidProject_guid_03_View() {
		return getView("NewAndroidProject_guid_03_View.fxml");
	}
	public Parent getNewAndroidProject_guid_04_View() {
		return getView("NewAndroidProject_guid_04_View.fxml");
	}
	public Parent getAndroidAnnotation_View() {
		return getView("AndroidAnnotation_View.fxml");
	}
	public Parent getAndroidResourceString_View() {
		return getView("AndroidResourceString_View.fxml");
	}
	public Parent getAndroidGetDescription_View(){
		return getView("JpsGetDescription_View.fxml");
	}

	public Parent getXml2Java_View() {
		return getView("Xml2Java_View.fxml");
	}
	public Parent getExcel2Java_View() {
		return getView("Excel2Java_View.fxml");
	}
	public Parent getLocalAreaNetwork_View() {
		return getView("LocalAreaNetwork_View.fxml");
	}
	public Parent getUdpServer_View() {
		return getView("UdpServer_View.fxml");
	}
	public Parent getTcpServer_View() {
		return getView("TcpServer_View.fxml");
	}
	public Parent getTcpClient_View() {
		return getView("TcpClient_View.fxml");
	}
	public Parent getJson_View() {
		return getView("Json_View.fxml");
	}
	public Parent getImage2Json_View() {
		return getView("Image2Json_View.fxml");
	}
	
    /**获取view*/
	public Parent getView(String name){
		Parent common_View = null ;
		try {
			common_View = FXMLLoader.load(getClass().getResource(name));
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return common_View;
	}
}
