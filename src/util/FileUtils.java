package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {


    public static String readFileContent(String fileName) throws IOException {

        File file = new File(fileName);

        BufferedReader bf = new BufferedReader(new FileReader(file));

        String content = "";
        StringBuilder sb = new StringBuilder();

        while (content != null) {
            content = bf.readLine();

            if (content == null) {
                break;
            }

            sb.append(content.trim());
        }

        bf.close();
        return sb.toString();
    }


    /**
     * @param fileType ".jpg" ".png" ".pdf" "."txt (lowercase)
     * @param filePath
     * @return
     */
    public static boolean checkFileType(String fileType, String filePath) {
        File file = new File(filePath);
        if(!checkFileExists(file)){
            return false;
        }
        if (filePath != null && filePath.contains(".")) {
            int i = filePath.lastIndexOf(".");
            String str = filePath.substring(i,filePath.length());
            if (str!=null&&fileType.trim().toLowerCase().equals(str)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // 判断文件是否存在
    public static boolean checkFileExists(File file) {
        return  file.exists();
    }

    // 判断文件夹是否存在
    public static boolean checkDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
                return true;
            } else {
                System.out.println("the same name file exists, can not create dir");
                return false;
            }
        } else {
            System.out.println("dir not exists, create it ...");
            return false;
        }
    }


}
