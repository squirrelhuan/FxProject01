package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.Tcp_model.MyTcpServerSocket;
import util.MySystem;

public class MainTcpServer {
	public MainTcpServer(){
		 ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(40000);

            System.out.println("服务器启动");
	        while (true)
	        {
	            // 一直处于监听状态,这样可以处理多个用户
	            Socket socket = serverSocket.accept();
                MySystem.out.println("lian jie...");
	            // 启动读写线程
	           // new TcpServerInputThread(socket).start();
	            //new TcpServerOutputThread(socket).start();

	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public static void main(String[] args) throws Exception
	    {
	        ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("服务器启动");
	        while (true)
	        {
	            // 一直处于监听状态,这样可以处理多个用户
	            MyTcpServerSocket socket = (MyTcpServerSocket) serverSocket.accept();
                MySystem.out.println("lian jie...");
	            // 启动读写线程
	            new TcpServerInputThread(socket).start();
	            new TcpServerOutputThread(socket).start();

	        }

	    }
}
