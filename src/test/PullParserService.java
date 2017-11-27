package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.View_model.Xml2Java_Tab.ResultType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import util.MySystem;

public class PullParserService {
	/**
	 * 从XML文件中读取数据
	 * 
	 * @param xml
	 *            XML文件输入流
	 */
	public PullParserService(ArrayList<ResultType> list_type_result,ArrayList<ResultType> list_type_paramter){
		this.list_type_result_c = list_type_result;
		this.list_type_paramter_c= list_type_paramter;
	}
	public List<Person> getPeople(InputStream xml) throws Exception {
		if(this.list_type_result_c.size()<=0){
			return null;
		}
		List<Person> lst = null;
		Person person = null;

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
				lst = new ArrayList<Person>();
				break;
			case XmlPullParser.START_TAG: // 标签开始
				setValue(nodeName, pullParser,lst);
				
				break;
			case XmlPullParser.END_TAG: // 标签结束
				/*if ("person".equals(nodeName)) {
					lst.add(person);
					person = null;
				}*/
				break;
			}
			event = pullParser.next(); // 下一个标签
		}
		return lst;
	}

	public static String[] type_result = {"LinearLayout","RelativeLayout","TextView","ImageView","GridView","Button"}; 
	public static String[] type_method = {"name","name(type)","findViewById","OnClick","OnClick(this)","OnClick(Listener)"}; 
	public static ArrayList<ResultType> list_type_result_c;
	public static ArrayList<ResultType> list_type_paramter_c;
	public static boolean setValue(String nodeName, XmlPullParser pullParser, List<Person> lst) {
		
		for (int i = 0; i < list_type_result_c.size(); i++) {
			if(list_type_result_c.get(i).isValue()&&list_type_result_c.get(i).getName().equals(nodeName)){
				addToList(list_type_result_c.get(i).getName(),pullParser,lst);
			}
		}
		return true;
	}

	private static void addToList(String name,XmlPullParser pullParser, List<Person> lst) {
		String id_str;
		//= pullParser.getAttributeValue(0);
		if (pullParser.getAttributeValue(null, "android:id") != null) {
			id_str = new String(pullParser.getAttributeValue(null,
					"android:id"));
		}else{
			return;
		}
		Person person = new Person();
		if (id_str != null) {
			person.setName(name);
			person.setId(id_str);
			lst.add(person);
		}else{
			person = null;
			return;
		}
	}
	
	public interface XmlType{
		void LinearLayout();
	}
}