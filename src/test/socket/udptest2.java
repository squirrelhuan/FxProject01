package test.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.alibaba.fastjson.JSON;

import model.Udp_model.UdpData;


import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.net.DatagramPacket;  
import java.net.DatagramSocket;  
import java.net.InetSocketAddress;  
import java.net.SocketException; 

public class udptest2 {

	private static long time3;
	public  static void main(String[] args){
		try {
			while(true){
				System.out.println("等待连接。。。");
				long time4 = System.currentTimeMillis();
				System.out.println("等待连接用时："+(time4-time3));
			receive();
			time3 = System.currentTimeMillis();
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	    private static int IMAGE_PORT = 26891;  
	    private static DatagramPacket datagramPacket;  
	    private static DatagramSocket datagramSocket;  
	      
	    static byte b[] = new byte[5120];  
	    public static byte[] receive() throws SocketException{ 
	    	long time1 = System.currentTimeMillis();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        if(datagramSocket == null){  
	            datagramSocket = new DatagramSocket(null);  
	            datagramSocket.setReuseAddress(true);   
	            datagramSocket.bind(new InetSocketAddress(IMAGE_PORT));  
	        }else  
	        try {  
	            datagramSocket = new DatagramSocket(IMAGE_PORT);  
	        } catch (SocketException e) {  
	           // e.printStackTrace();  
	        }   
	        int i = 0;
	        while(true){  
	        	
	            datagramPacket = new DatagramPacket(b, b.length);   
	            try {  
	                datagramSocket.receive(datagramPacket);  
	                i++;
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            //System.out.println("recive");
	            String msg  = new String(datagramPacket.getData(),0,datagramPacket.getLength());  
	            if(msg.startsWith(";!")){  
	            	long time2 = System.currentTimeMillis();
	                System.out.println(i+"次接收到所有数据共"+baos.toByteArray().length+"字节"+baos.toByteArray().length/1024+"K,用时："+(time2-time1));  
	                break;  
	            }  
	            
	            baos.write(datagramPacket.getData(),0,datagramPacket.getLength());  
	            //System.out.println("-->正在接收数据:"+datagramPacket.getData());  
	        }  
	        try {  
	            baos.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	           
	        return baos.toByteArray();  
	          
	    }  
	      
}
