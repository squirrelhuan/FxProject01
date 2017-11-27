package test.unicode;

import javafx.scene.control.TextArea;
import model.resource.StringModel2;
import org.xmlpull.v1.XmlPullParserException;
import util.FileTools;
import util.MySystem;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huan on 2017/9/8.
 */
public class StringUtil {

    static Map<String,String> maps = new HashMap<String,String>();
    public static List<StringModel2> ParseXml(File file, int type) throws XmlPullParserException, IOException {
        String str = "";
        List<StringModel2> models = new ArrayList<StringModel2>();
        StringBuilder resultStr = new StringBuilder();  //�
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
            String line = bReader.readLine();
            while (line != null) {
                resultStr.append(line + "\n\r");
                if (line.contains("|")) {
                    String id = line.split("\\|")[line.split("\\|").length - 1];
                    Pattern p = Pattern.compile("^-?\\d{6}+$");
                    Matcher m = p.matcher(id);
                    if (m.matches()) {
                        if(maps.get(id)==null){
                        StringModel2 model = new StringModel2();
                        model.setKey(id);
                        models.add(model);
                        }
                    }
                }
                line = bReader.readLine();
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        maps.clear();
        str = resultStr.toString();
        MySystem.out.println(str);
        return models;
    }
//去除下划线减号
    public static List<StringModel2> ParseXml2(File file, int type) throws XmlPullParserException, IOException {
        String str = "";
        List<StringModel2> models = new ArrayList<StringModel2>();
        StringBuilder resultStr = new StringBuilder();  //�
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String line = bReader.readLine();
            while (line != null) {
                resultStr.append(line + "\n\r");
                String name_en = line;
                if (line.contains("|")) {
                    String id = line.split("\\|")[line.split("\\|").length - 1];
                    name_en = line.split("\\|")[0];
                }
                //去除数字
                Pattern pattern = Pattern.compile("[\\d]");
                Matcher matcher = pattern.matcher(name_en);
                name_en = (matcher.replaceAll("").trim());

                //TODO 去除下划线减号
                if(name_en.contains("_")){
                    name_en = name_en.replace("_"," ");
                }
                if(name_en.contains("-")){
                    name_en = name_en.replace("-"," ");
                }
                StringModel2 model = new StringModel2();
               // model.setKey(id);
                model.setValueEN(name_en);
                models.add(model);
                line = bReader.readLine();
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        maps.clear();
        str = resultStr.toString();
        MySystem.out.println(str);
        return models;
    }

    public static List<StringModel2> ParseXml3(File file1,File file2, int type) throws XmlPullParserException, IOException {
        String str = "";
        List<StringModel2> models = new ArrayList<StringModel2>();
        List<String> strings = new ArrayList<>();
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "utf-8"));
            String line = bReader.readLine();
            while (line != null) {
                if(line.contains("\t")){
                strings.add(line.split("\t")[1]);}
                line = bReader.readLine();
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder resultStr = new StringBuilder();  //�
        try {
            BufferedReader bReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2), "utf-8"));
            String line2 = bReader2.readLine();
            int i =0;
            while (line2 != null) {
                resultStr.append(line2+"\t"+strings.get(i)+"\n");
                i++;
                line2 = bReader2.readLine();
            }
            bReader2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //str = resultStr.toString();
        MySystem.out.println(resultStr);
        String fname = file1.getAbsolutePath();
        fname = fname.replace(".txt", "(副本).txt");
        File filet = new File(fname);
        FileTools.writeFile(filet, resultStr.toString());
        MySystem.out.println(filet.getName() + "已保存！");
        return models;
    }
}
