package test.plugin;


import java.net.MalformedURLException;
import java.util.List;

import interfaces.Lorder;
import org.dom4j.DocumentException;

import interfaces.PluginService;
import model.plugin_model.Plugin;
import util.XMLParser;

public class MainTest {
	public static void main(String[] args) {
		/*try {
			List<Plugin> pluginList = XMLParser.getPluginList();
			PluginManager pluginManager = new PluginManager(pluginList);
			for(Plugin plugin : pluginList) {
				PluginService pluginService = pluginManager.getInstance(plugin.getClassName());
				System.out.println("开始执行[" + plugin.getName() + "]插件...");
				// 调用插件
				pluginService.service();
				System.out.println("[" + plugin.getName() + "]插件执行完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		//test();
	}
	
	public static void test1(){
		try {
			List<Plugin> pluginList = XMLParser.getPluginList();
			PluginManager pluginManager = PluginManager.getInstance();
			for(Plugin plugin : pluginList) {
				PluginService pluginService = pluginManager.getInstance(plugin.getClassName());
				System.out.println("开始执行[" + plugin.getName() + "]插件...");
				// 调用插件
				pluginService.onCreate(new Lorder());
				System.out.println("[" + plugin.getName() + "]插件执行完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void singletest(Plugin plugin) throws DocumentException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
			PluginManager pluginManager = PluginManager.getInstance();
				PluginService pluginService = pluginManager.getInstance(plugin.getClassName());
				System.out.println("开始执行[" + plugin.getName() + "]插件...");
				// 调用插件
				pluginService.onCreate(new Lorder());
				System.out.println("[" + plugin.getName() + "]插件执行完成");
	}
}
