package test.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.resource.StringModel;
import model.setting.SettingMenu;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import test.Person;
import util.MySystem;

public class Java2XML_Setting {

	public static void BuildXMLDoc(ObservableList<StringModel> books, int type)
			throws IOException, JDOMException {
		// 创建根节点 并设置它的属性 ;
		Element root = new Element("resources").setAttribute("count", "4");
		// 将根节点添加到文档中；
		Document Doc = new Document(root);

		for (int i = 0; i < books.size(); i++) {
			// 创建节点 book;
			Element elements = new Element("string");
			elements.setAttribute("name", books.get(i).getKey());
			if (type == 0) {
				elements.setText(books.get(i).getValueCH() == null ? books.get(
						i).getValueEN() : books.get(i).getValueCH());
			} else {
				elements.setText((books.get(i).getValueEN() == null && books
						.get(i).getValueEN() == "") ? books.get(i).getValueCH()
						: books.get(i).getValueEN());
			}
			// 给 book 节点添加子节点并赋值；
			// elements.addContent(new
			// Element("id").setText(books.get(i).getKey()));
			// elements.addContent(new
			// Element("name").setText(books.get(i).getValue()));
			//
			root.addContent(elements);
		}
		// 输出 books.xml 文件；
		// 使xml文件 缩进效果
		Format format = Format.getPrettyFormat();
		XMLOutputter XMLOut = new XMLOutputter(format);
		XMLOut.output(Doc, new FileOutputStream(
				"C:/Users/Administrator/Desktop/string_"
						+ (type == 0 ? "zh" : "en") + ".xml"));
	}

	/**
	 * 获取父节点
	 * 
	 * @return
	 */
	public static SettingMenu getParentNode(SettingMenu menu) {
		if (!menu.getParent().isEnd()) {
			return menu.getParent();
		}else{
			return getParentNode(menu.getParent());
		}
	}

	/**
	 * 解析test3.xml
	 * 
	 * @param is
	 * @return list
	 */
	public static SettingMenu parseFile3(InputStream is) {
		SettingMenu menu_root = null;
		SettingMenu menu = null;
		try {
			// 获得pull解析器工厂
			XmlPullParserFactory pullParserFactory = XmlPullParserFactory
					.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			parser.setInput(is, "utf-8");
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					// setting = new Setting();
					break;
				case XmlPullParser.START_TAG:
					if ("SettingMenu".equals(parser.getName())) {
						// 开始
						if (menu != null) {
							if (menu.isEnd()) {// 如果上一个标签结束了返回到上一层（本节点和上一节点的关系是并列）
								//SettingMenu menu_last = null;
								//if (!menu.getParent().isEnd()) {
									//menu_last = menu.getParent().getParent();
								//}

								SettingMenu menu_last = getParentNode(menu);
								menu = new SettingMenu();
								menu_last.addMenus(menu);
								MySystem.out.println(menu_last.getName()+"并列");
							} else  {// 如果上一个标签没结束（本节点和上一节点的关系是嵌套）
								SettingMenu menu_last = menu;
								menu = new SettingMenu();
								menu_last.addMenus(menu);
								MySystem.out.println(menu_last.getName()+"嵌套");
							}
						} else {
							menu = new SettingMenu();
							menu.setParent(new SettingMenu());
							menu_root = menu;
							MySystem.out.println("第一次" + menu.getName());
						}
						menu.setEnd(false);
						String id;
						String name;
						// = pullParser.getAttributeValue(0);
						if (parser.getAttributeValue(null, "id") != null) {
							id = new String(
									parser.getAttributeValue(null, "id"));
							menu.setId(id);
						}
						if (parser.getAttributeValue(null, "name") != null) {
							name = new String(parser.getAttributeValue(null,
									"name"));
							menu.setName(name);
						}
						//MySystem.out.println(menu.getName());
					} else if ("key".equals(parser.getName())) {
						menu.setKey(parser.nextText());
					} else if ("value".equals(parser.getName())) {
						menu.setValue(parser.nextText());
					} else if ("title".equals(parser.getName())) {
						menu.setTitle(parser.nextText());
					}else if ("type".equals(parser.getName())) {
						menu.setType(Integer.valueOf(parser.nextText()));
					}
					break;
				case XmlPullParser.END_TAG:

					// 标签结束重新定位父节点
					if ("SettingMenu".equals(parser.getName())) {
						menu.setEnd(true);
						menu=menu.getParent();
					}

					/*
					 * if (menu == menu_root) { List<SettingMenu> items =
					 * menu_root.getItems(); items = new
					 * ArrayList<SettingMenu>(); items.add(menu);
					 * menu_root.setItems(items); }
					 */
					break;
				}
				event = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menu_root;
	}

	public static SettingMenu ParseXml(InputStream xml)
			throws XmlPullParserException, IOException {
		SettingMenu lst = null;
		// 获得pull解析器工厂
		XmlPullParserFactory pullParserFactory = XmlPullParserFactory
				.newInstance();

		// 获取XmlPullParser的实例
		XmlPullParser pullParser = pullParserFactory.newPullParser();

		// 设置需要解析的XML数据
		pullParser.setInput(xml, "UTF-8");

		// 取得事件
		int event = pullParser.getEventType();

		// 若为解析到末尾
		while (event != XmlPullParser.END_DOCUMENT) // 文档结束
		{
			// 节点名称
			String nodeName = pullParser.getName();
			switch (event) {
			case XmlPullParser.START_DOCUMENT: // 文档开始
				break;
			case XmlPullParser.START_TAG: // 标签开始
				if (nodeName.equals("Setting")) {
					lst = new SettingMenu();
				} else {
					addToList(nodeName, pullParser, lst);
				}
				break;
			case XmlPullParser.END_TAG: // 标签结束
				// lst.get(lst.size()-1).setValue(pullParser.getText());
				/*
				 * if ("person".equals(nodeName)) { lst.add(person); person =
				 * null; }
				 */
				break;
			}
			event = pullParser.next(); // 下一个标签
		}
		return lst;
	}

	private static void addToList(String nodeName, XmlPullParser pullParser,
			SettingMenu lst) throws XmlPullParserException, IOException {
		switch (nodeName) {
		case "SettingMenu":
			String id;
			String name;
			String key;
			String value;
			String title;
			// = pullParser.getAttributeValue(0);
			if (pullParser.getAttributeValue(null, "id") != null) {
				id = new String(pullParser.getAttributeValue(null, "id"));
			}
			if (pullParser.getAttributeValue(null, "name") != null) {
				name = new String(pullParser.getAttributeValue(null, "name"));
			} else {
				return;
			}
			if (name != null) {
				SettingMenu menu = new SettingMenu();
				menu.setName(name);
				// menu.setKey(pullParser.get));
				// menu.setValueEN(pullParser.nextText());
				ArrayList<SettingMenu> items = (ArrayList<SettingMenu>) lst
						.getItems();
				if (items == null)
					items = new ArrayList<SettingMenu>();
				items.add(menu);
				lst.setItems(items);
			}
			break;
		case "key":
			lst.setKey(pullParser.nextText());
			break;
		case "value":
			lst.setValue(pullParser.nextText());
			break;
		case "title":
			lst.setTitle(pullParser.nextText());
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {
		try {
			Java2XML_Setting j2x = new Java2XML_Setting();
			System.out.println("正在生成 books.xml 文件...");
			// j2x.BuildXMLDoc();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("c:/books.xml 文件已生成");
	}
}
