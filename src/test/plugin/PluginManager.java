package test.plugin;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.dom4j.DocumentException;

import application.Test;
import interfaces.PluginService;
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import listener.window.PluginManager_Window_listener;
import model.plugin_model.Plugin;
import util.FileTools;
import util.JarUtils;
import util.MySystem;
import util.XMLParser;

public class PluginManager {
	private URLClassLoader urlClassLoader;

	private static PluginManager pluginManager;
	
	public static PluginManager getInstance(){
		if(pluginManager==null){
			try {
			List<Plugin> pluginList;
			pluginList = XMLParser.getPluginList();
				pluginManager = new PluginManager(pluginList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pluginManager;
	}
	
	private PluginManager(List<Plugin> plugins) throws MalformedURLException {
		init(plugins);
	}
	
	private void init(List<Plugin> plugins) throws MalformedURLException {
		int size = plugins.size();
		URL[] urls = new URL[size];
		
		for(int i = 0; i < size; i++) {
			Plugin plugin = plugins.get(i);
			String filePath = plugin.getJar();

			urls[i] = new URL("file:" + filePath);
		}
		
		// 将jar文件组成数组，来创建一个URLClassLoader
		urlClassLoader = new URLClassLoader(urls);
	}
	
	public PluginService getInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// 插件实例化对象，插件都是实现PluginService接口
		Class<?> clazz = urlClassLoader.loadClass(className);
		System.out.println(className);
		Object instance = clazz.newInstance();

		return (PluginService)instance;
	}
	
	
	public Plugin addPlugin(Test test,PluginManager_Window_listener pluginManager_Window_listener) throws DocumentException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("指定要添加的插件");
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("java插件 (*.jar)", "*.jar"));
				//new FileChooser.ExtensionFilter("所有文件", "*.*"));
		File file = fileChooser.showOpenDialog(test.layoutPane.getScene()
				.getWindow());
		Plugin plugin = null;
		if (file != null) {
			//FileTools.writeFile(file, ((TextArea) edit_Txt).getText());
			MySystem.out.println(file.getName()+"路径："+file.getAbsolutePath());
			plugin = new Plugin(file.getName(),file.getAbsolutePath(),"chajian.class");
			String[] jarname = null;
			try {
				jarname = JarUtils.findClassesImplementInterfaceFromJar("interfaces.PluginService", file.getAbsolutePath());
			    plugin.setClassName(jarname[0]);
				System.out.println(jarname[0]);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				MySystem.out.println("没有匹配到正确的插件");
				return null;
			}
			XMLParser.addPlugin(plugin);
		}
		return plugin;
	}
	
	public Plugin addPlugin(Test test) throws DocumentException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("指定要添加的插件");
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("java插件 (*.jar)", "*.jar"));
				//new FileChooser.ExtensionFilter("所有文件", "*.*"));
		File file = fileChooser.showOpenDialog(test.layoutPane.getScene()
				.getWindow());
		Plugin plugin = null;
		if (file != null) {
			//FileTools.writeFile(file, ((TextArea) edit_Txt).getText());
			MySystem.out.println(file.getName()+"路径："+file.getAbsolutePath());
			plugin = new Plugin(file.getName(),file.getAbsolutePath(),"chajian.class");
			String jarname = null;
			try {
				jarname = JarUtils.findClassesImplementInterfaceFromJar2("interfaces.PluginService", file.getAbsolutePath());
			    plugin.setClassName(jarname);
				System.out.println(jarname);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				MySystem.out.println("没有匹配到正确的插件");
				return null;
			}
			XMLParser.addPlugin(plugin);
		}
		return plugin;
	}

	public void remove(Plugin plugin) throws DocumentException {
		XMLParser.removePlugin(plugin);
	}
	
}