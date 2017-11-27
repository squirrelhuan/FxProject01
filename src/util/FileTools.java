package util;
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;
import java.io.InputStreamReader;
  
public class FileTools {  
    
	public static enum fileType{
		png,JPG,bmp,gif
	}
	
    public static String readFile(File file) {  
        StringBuilder resultStr = new StringBuilder();  
        try {  
           // BufferedReader bReader = new BufferedReader(new FileReader(file));  
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String line = bReader.readLine();  
            while (line != null) {  
                resultStr.append(line);  
                line = bReader.readLine();  
            }  
            bReader.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return resultStr.toString();  
    }  
    
    public static String readFileWithLine(File file) {  
        StringBuilder resultStr = new StringBuilder();  //�
        try {  
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK")); 
            String line = bReader.readLine();  
            while (line != null) {  
                resultStr.append(line+"\n\r");  
                line = bReader.readLine();  
            }  
            bReader.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return resultStr.toString();  
    }
    public static String readFileWithLine(File file,String charseName) {
        StringBuilder resultStr = new StringBuilder();  //�
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),charseName));
            String line = bReader.readLine();
            while (line != null) {
                resultStr.append(line+"\n\r");
                line = bReader.readLine();
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr.toString();
    }

    public static void writeFile(File file, String str) {
        try {  
          BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));  
          bWriter.write(str);  
          bWriter.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

    public static String writeFile(File file) {
	    String  str="";
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
            bWriter.write(str);
            bWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 读取某个文件夹下的所有文件
     */
    public static boolean readAllFile(String filepath) throws FileNotFoundException, IOException {
            try {

                    File file = new File(filepath);
                    if (!file.isDirectory()) {
                            System.out.println("文件");
                            System.out.println("path=" + file.getPath());
                            System.out.println("absolutepath=" + file.getAbsolutePath());
                            System.out.println("name=" + file.getName());

                    } else if (file.isDirectory()) {
                            System.out.println("文件夹");
                            String[] filelist = file.list();
                            for (int i = 0; i < filelist.length; i++) {
                                    File readfile = new File(filepath + "\\" + filelist[i]);
                                    if (!readfile.isDirectory()) {
                                            System.out.println("path=" + readfile.getPath());
                                            System.out.println("absolutepath="
                                                            + readfile.getAbsolutePath());
                                            System.out.println("name=" + readfile.getName());

                                    } else if (readfile.isDirectory()) {
                                    	readAllFile(filepath + "\\" + filelist[i]);
                                    }
                            }

                    }

            } catch (FileNotFoundException e) {
                    System.out.println("readfile()   Exception:" + e.getMessage());
            }
            return true;
    }
} 