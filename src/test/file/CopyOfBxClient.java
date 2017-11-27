package test.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

/** 
 * 文件发送客户端主程序 
 * @author admin_Hzw 
 * 
 */  
public class CopyOfBxClient {  
      
    /** 
     * 程序main方法 
     * @param args 
     * @throws IOException 
     */  
    public static void main(String[] args) throws IOException {  
        int length = 0;  
        double sumL = 0 ;  
        byte[] sendBytes = null;  
        Socket socket = null;  
        DataOutputStream dos = null;  
        FileInputStream fis = null;  
        boolean bool = false;  
        try {  
            File file = new File("D:/天啊.zip"); //要传输的文件路径  
            long l = file.length();   
            socket = new Socket();    
            socket.connect(new InetSocketAddress("127.0.0.1", 48123));  
            
            receiveFile(socket);  
            
            if(sumL==l){  
                bool = true;  
            }  
        }catch (Exception e) {  
            System.out.println("客户端文件传输异常");  
            bool = false;  
            
            e.printStackTrace();    
        } finally{    
            if (dos != null)  
                dos.close();  
            if (fis != null)  
                fis.close();     
            if (socket != null)  
                socket.close();      
        }  
        System.out.println(bool?"成功":"失败");  
    } 
    
    /** 
     * 接收文件方法 
     * @param socket 
     * @throws IOException 
     */  
    public static void receiveFile(Socket socket) throws IOException {  
        byte[] inputByte = null;  
        int length = 0;  
        DataInputStream dis = null;  
        FileOutputStream fos = null;  
        String filePath = "D:/temp/"+GetDate.getDate()+"SJ"+new Random().nextInt(10000)+".zip";  
        try {  
            try {  
                dis = new DataInputStream(socket.getInputStream());  
                File f = new File("D:/temp");  
                if(!f.exists()){  
                    f.mkdir();    
                }  
                /*   
                 * 文件存储位置   
                 */  
                fos = new FileOutputStream(new File(filePath));      
                inputByte = new byte[1024*50];     
                System.out.println("开始接收数据...");    
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {  
                    fos.write(inputByte, 0, length);  
                    fos.flush();      
                }  
                System.out.println("完成接收："+filePath);  
            } finally {  
                if (fos != null)  
                    fos.close();  
                if (dis != null)  
                    dis.close();  
                if (socket != null)  
                    socket.close();   
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  