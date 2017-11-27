package test.unicode;

import model.resource.StringModel2;
import org.junit.Test;
import util.MySystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huan on 2017/10/30.
 */
public class StringFormat {

    public static StringBuilder dealString(File file){

        StringBuilder resultStr = new StringBuilder();  //�
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
            String line = bReader.readLine();
            while (line != null) {
                if (line.contains("|")) {
                    String str = line.replace("|","\t");

                    resultStr.append(str + "\n");
                   // MySystem.out.println("replace..."+str);
                }
                line = bReader.readLine();
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultStr;
    }
}
