package model.Tcp_model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

import javafx.application.Platform;
import listener.TcpServer_listener;
import util.MySystem;

public class MyTcpServerSocket extends Socket {
	public boolean isSelected = true;
	private TcpServer_listener tcpServer_listener;
	private Socket socket;
	private String recvStr;
	public String send_Str;
	public boolean OK = true;
	private int lineCount = 0;
	
	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public MyTcpServerSocket(TcpServer_listener tcpServer_listener, Socket socket) {
		this.socket = socket;
		this.tcpServer_listener = tcpServer_listener;
		// 启动读写线程
		Thread receive_Thread = new Thread(new Recv_Thread(socket,this));
		receive_Thread.start();
		
		/*Thread send_Thread = new Thread(new Send_Thread(socket));
		send_Thread.start();*/
	}

	// 发送数据线程类
	class Recv_Thread implements Runnable {
		Socket socket;
		MyTcpServerSocket mySocket;
		Recv_Thread(Socket socket,MyTcpServerSocket mySocket) {
			this.socket = socket;
			this.mySocket = mySocket;
		}

		@Override
		public void run() {
			while (OK) {
				try {
					// 获得输入流
					InputStream is = socket.getInputStream();

					while (tcpServer_listener.isRunning) {
						byte[] buffer = new byte[1024];
						int length = is.read(buffer);
						recvStr = new String(buffer, 0, length);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if (recvStr != null) {
									if (lineCount >= 999) {
										tcpServer_listener.received_TextArea
												.setText("");
										lineCount = 0;
									}
									lineCount++;
									tcpServer_listener.received_TextArea_d
											.appendText(LocalDateTime.now()
													+ " 收到：" + recvStr + "\n\r");
									recvStr = null;
								}
							}
						});
					}
				} catch (IOException e) {
					e.printStackTrace();
				}catch (StringIndexOutOfBoundsException e) {
					e.printStackTrace();
				}finally{
					OK = false;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							tcpServer_listener.RefreshUI(mySocket);
						}
					});
				}
			}
		}
	}

	  // 发送数据线程类 
	public static boolean SendMessage(String send_Str ,Socket socket) { try {
	  OutputStream os = socket.getOutputStream();
	  if (send_Str != null && !send_Str.trim().equals("")) {
	      os.write(send_Str.getBytes()); send_Str = null;
	      return true; } 
	  } catch(IOException e) {
		  e.printStackTrace();
	  } return false; }

	@Override
	public String toString() {
		return socket.toString();
	}

}
