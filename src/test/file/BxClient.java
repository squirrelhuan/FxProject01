package test.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/** 
 * 文件发送客户端主程序 
 * @author admin_Hzw 
 * 
 */  
public class BxClient {  
      
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
            File file = new File("D:/老鼠也疯狂.apk"); //要传输的文件路径  
            long l = file.length();   
            socket = new Socket();    
            socket.connect(new InetSocketAddress("192.168.1.110", 48123));  
            dos = new DataOutputStream(socket.getOutputStream());  
            fis = new FileInputStream(file);        
            sendBytes = new byte[1024];    
            while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {  
                sumL += length;    
                System.out.println("已传输："+((sumL/l)*100)+"%");  
                dos.write(sendBytes, 0, length);  
                dos.flush();  
            }   
            //虽然数据类型不同，但JAVA会自动转换成相同数据类型后在做比较  
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
}  