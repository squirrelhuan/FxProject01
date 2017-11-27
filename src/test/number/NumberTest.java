package test.number;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NumberTest {

	public static void main(String[] args) {
		stringToDate("2017-2-8 11:03");
		System.out.println(stringToDate("2017-3-8 11:03").after(stringToDate("2017-2-8 11:02")));
		
		/*getUTF8XMLString("北京");
		try {
			System.out.println("青岛".getBytes("gbk"));
			System.out.println("北京".getBytes("GB2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 String str="北京";
	        try {
	          byte[] temp=str.getBytes("utf-8");//这里写原编码方式
	            byte[] newtemp=new String(temp,"utf-8").getBytes("GB2312");//这里写转换后的编码方式
	            String newStr=new String(newtemp,"GB2312");//这里写转换后的编码方式
	            byte[] sour = str.getBytes("utf-8");
	            String dest = new String(sour , "GB2312");
	            System.out.println(dest);
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		number();*/
	}

	public static Date stringToDate(String str) {
		System.out.println("start");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		Date date = null;
		try {
			// Fri Feb 24 00:00:00 CST 2012
			date = format.parse(str);
		
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		// 2012-02-24
		//date = java.sql.Date.valueOf(str);
        System.out.println(date);
		return date;
	}
	
	public static void number() {
        
		float ff = 8f;
		System.out.println(Integer.toBinaryString((int) ff));
		int a = 0x2f;// Сдʮ�����ƣ��ȼ���0x002f��
		System.out.println(Integer.toBinaryString(a));
		System.out.println("����47ת�����ƽ��Ϊ��"+Integer.toBinaryString(47));
		int b = 0x2F;// ��дʮ������
		System.out.println("��дʮ������2Fת�����ƽ��Ϊ��"+Integer.toBinaryString(b));

		int c = 10;// ��׼ʮ����
		System.out.println("��׼ʮ����10ת�����ƽ��Ϊ��"+Integer.toBinaryString(c));

		int d = 012;// ���㿪ͷ����ʾ�˽���
		System.out.println("���㿪ͷ����ʾ�˽���012ת�����ƽ��Ϊ��"+Integer.toBinaryString(d));

		char e = 0xff;// charΪ2���ֽڣ�16λ
		byte f = 0xf;// byteΪ8λ
		short g = 0xff;// shortΪ2���ֽڣ�16λ
		System.out.println(Integer.toBinaryString(e));
		System.out.println(Integer.toBinaryString(f));
		System.out.println(Integer.toBinaryString(g));

	}
	
	/** 
	    * Get XML String of utf-8 
	    *  
	    * @return XML-Formed string 
	    */  
	    public static String getUTF8XMLString(String xml) {  
	    // A StringBuffer Object  
	    StringBuffer sb = new StringBuffer();  
	    sb.append(xml);  
	    String xmString = "";  
	    String xmlUTF8="";  
	    try {  
	    xmString = new String(sb.toString().getBytes("UTF-8"));  
	    xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");  
	    System.out.println("utf-8 编码：" + xmlUTF8) ;  
	    } catch (UnsupportedEncodingException e) {  
	    // TODO Auto-generated catch block  
	    e.printStackTrace();  
	    }  
	    // return to String Formed  
	    return xmlUTF8;  
	    }  
}
