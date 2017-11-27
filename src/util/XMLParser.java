package util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import model.plugin_model.Plugin;
import test.plugin.MainTest;

public class XMLParser {
	
	public static List<Plugin> getPluginList() throws DocumentException {
		List<Plugin> list = new ArrayList<Plugin>();
		
		SAXReader saxReader =new SAXReader();
		 // 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
        File f = new File(MainTest.class.getClass().getResource("/").getPath());
		//MySystem.out.println(f);
        /*File file = new File("plugins.xml");
        System.out.println(file);*/
		Document document = saxReader.read(new File("plugins.xml"));
		Element root = document.getRootElement();
		List<?> plugins = root.elements("plugin");
		for(Object pluginObj : plugins) {
			Element pluginEle = (Element)pluginObj;
			Plugin plugin = new Plugin();
			plugin.setName(pluginEle.elementText("name"));
			plugin.setJar(pluginEle.elementText("jar"));
			plugin.setClassName(pluginEle.elementText("class"));
			list.add(plugin);
		}
		return list;
	}
	
	public static void addPlugin(Plugin plugin) throws DocumentException{
		
		SAXReader saxReader =new SAXReader();
		 // 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
       File f = new File(MainTest.class.getClass().getResource("/").getPath());
		//MySystem.out.println(f);
       /*File file = new File("plugins.xml");
       System.out.println(file);*/
		Document doc = saxReader.read(new File("plugins.xml"));
		Element rootElem = doc.getRootElement();
		List<?> plugins = rootElem.elements("plugin");
		for(Object pluginObj : plugins) {
			Element pluginEle = (Element)pluginObj;
			Element pluginc = pluginEle.element("class");
			if(plugin.getClassName().equals(pluginc.getText())){
				MySystem.out.println("插件已存在");
				return;
			}
			//System.out.println(pluginc.getText());
		}
		//1.创建文档
        //Document doc=DocumentHelper.createDocument();
        //2.添加标签
        //Element rootElem=doc.addElement("plugins");
        Element stuElem=rootElem.addElement("plugin");
        Element name = stuElem.addElement("name",plugin.getName());
        name.setText(plugin.getName());
        Element jar = stuElem.addElement("jar", plugin.getJar());
        jar.setText(plugin.getJar());
        Element classname = stuElem.addElement("class", plugin.getClassName());
        classname.setText(plugin.getClassName());
        //4.增加属性
        /*stuElem.addAttribute("jar", plugin.getJar());
        stuElem.addAttribute("class", plugin.getClassName());*/
        //指定文件输出的位置
        FileOutputStream out;
		try {
			out = new FileOutputStream("plugins.xml");
		
        
        // 指定文本的写出的格式：
        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        format.setEncoding("UTF-8");
        
        //1.创建写出对象
        XMLWriter writer=new XMLWriter(out,format);
           
        //2.写出Document对象
        writer.write(doc);
        
        //3.关闭流
        writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removePlugin(Plugin plugin) throws DocumentException {

		SAXReader saxReader =new SAXReader();
		 // 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
       File f = new File(MainTest.class.getClass().getResource("/").getPath());
		//MySystem.out.println(f);
       /*File file = new File("plugins.xml");
       System.out.println(file);*/
		Document doc = saxReader.read(new File("plugins.xml"));
		Element rootElem = doc.getRootElement();
		List<?> plugins = rootElem.elements("plugin");
		for(Object pluginObj : plugins) {
			Element pluginEle = (Element)pluginObj;
			Element pluginc = pluginEle.element("jar");
			if(plugin.getJar().equals(pluginc.getText())){
				MySystem.out.println("插件已删除");
				plugins.remove(pluginObj);
				break;
			}
			//System.out.println(pluginc.getText());
		}
		
        //指定文件输出的位置
        FileOutputStream out;
		try {
			out = new FileOutputStream("plugins.xml");
		
        
        // 指定文本的写出的格式：
        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        format.setEncoding("UTF-8");
        
        //1.创建写出对象
        XMLWriter writer=new XMLWriter(out,format);
           
        //2.写出Document对象
        writer.write(doc);
        
        //3.关闭流
        writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}