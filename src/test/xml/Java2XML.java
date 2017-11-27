package test.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.resource.StringModel;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import test.Person;

public class Java2XML {
      
    public static void BuildXMLDoc(ObservableList<StringModel> books,int type) throws IOException, JDOMException {     
        // 创建根节点 并设置它的属性 ;     
        Element root = new Element("resources").setAttribute("count", "4");     
        // 将根节点添加到文档中；     
        Document Doc = new Document(root);     
          
        for (int i = 0; i < books.size(); i++) {    
           // 创建节点 book;     
           Element elements = new Element("string");    
           elements.setAttribute("name",books.get(i).getKey());
           if(type==0){
               elements.setText(books.get(i).getValueCH()==null?books.get(i).getValueEN():books.get(i).getValueCH());
           }else{
        	   elements.setText((books.get(i).getValueEN()==null&&books.get(i).getValueEN()=="")?books.get(i).getValueCH():books.get(i).getValueEN());
           }
           // 给 book 节点添加子节点并赋值；     
           //elements.addContent(new Element("id").setText(books.get(i).getKey()));    
          // elements.addContent(new Element("name").setText(books.get(i).getValue()));    
           //    
           root.addContent(elements);    
       }    
        // 输出 books.xml 文件；    
        // 使xml文件 缩进效果  
        Format format = Format.getPrettyFormat();  
        XMLOutputter XMLOut = new XMLOutputter(format);  
        XMLOut.output(Doc, new FileOutputStream("C:/Users/Administrator/Desktop/string_"+(type==0?"zh":"en")+".xml"));  
    }   
    public static List<StringModel> ParseXml(InputStream xml,int type) throws XmlPullParserException, IOException{
    	List<StringModel> lst = null;
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
				lst = new ArrayList<StringModel>();
				break;
			case XmlPullParser.START_TAG: // 标签开始
				addToList(nodeName, pullParser,lst,type);
				break;
			case XmlPullParser.END_TAG: // 标签结束
				//lst.get(lst.size()-1).setValue(pullParser.getText());
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
    
    private static void addToList(String name,XmlPullParser pullParser, List<StringModel> lst,int type) throws XmlPullParserException, IOException {
		String id_str;
		//= pullParser.getAttributeValue(0);
		if (pullParser.getAttributeValue(null, "name") != null) {
			id_str = new String(pullParser.getAttributeValue(null,
					"name"));
		}else{
			return;
		}
		StringModel person = new StringModel();
		if (id_str != null) {
			person.setKey(id_str);
			if(type==2){
				person.setValueEN(pullParser.nextText());
			}else{
				person.setValueCH(pullParser.nextText());
			}
			
			lst.add(person);
		}
	}
    
    public static void main(String[] args) {    
       try {    
           Java2XML j2x = new Java2XML();    
           System.out.println("正在生成 books.xml 文件...");    
          // j2x.BuildXMLDoc();    
       } catch (Exception e) {    
           e.printStackTrace();    
       }    
       System.out.println("c:/books.xml 文件已生成");  
    }    
}
