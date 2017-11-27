package test.unicode;

import org.junit.Test;

public class unicode {

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    //@Test
    public void main() {
        String test = "你好";//\u597d\u554a

        String unicode = string2Unicode(test);

        String string = unicode2String(unicode);

        System.out.println(unicode2String("\\u4f60\\u597d\\u5417"));
        System.out.println(unicode);

        System.out.println(string);

    }

    @Test
    public void teststr() {
        String src = "";
        String trans = "";

        String news = "";

        StringBuilder haha = new StringBuilder();

       /* for (String str :test.split(",")){
            haha.append(str+"\t");
        }*/
       String[] a = src.split("\n");
       String[] b = trans.split("\n");
       for(int i=0;i<src.split("\n").length;i++){
           haha.append(a[i]+" "+b[i*2]);
       }

        System.out.println(haha);
    }

}
