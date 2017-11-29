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

    //创建文件
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }
}
