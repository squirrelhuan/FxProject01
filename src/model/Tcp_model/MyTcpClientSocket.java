package model.Tcp_model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javafx.application.Platform;
import listener.TcpClient_listener;
import util.MySystem;

public class MyTcpClientSocket extends Socket {
	private static TcpClient_listener tcpClient_listener;
	private static Socket socket;
	private static InputStream is;
	private static OutputStream os;
	private String recvStr;
	public static boolean OK = true;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public MyTcpClientSocket(TcpClient_listener tcpClient_listener,
			Socket socket) {
		this.socket = socket;
		this.tcpClient_listener = tcpClient_listener;
		// 启动读写线程
		Thread receive_Thread = new Thread(new Recv_Thread(socket, this));
		receive_Thread.start();
	}

	// 发送数据线程类
	class Recv_Thread implements Runnable {
		Socket socket;
		MyTcpClientSocket mySocket;

		Recv_Thread(Socket socket, MyTcpClientSocket mySocket) {
			this.socket = socket;
			this.mySocket = mySocket;
		}

		@Override
		public void run() {
			while (OK) {
				try {
					// 获得输入流
					is = socket.getInputStream();
					while (tcpClient_listener.isRunning()) {
						byte[] buffer = new byte[1024];
						System.out.println("read");
						int length = is.read(buffer);
						recvStr = new String(buffer, 0, length);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if (!recvStr.trim().equals("")) {
									tcpClient_listener.RefreshUI(recvStr);
								}
							}
						});
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (StringIndexOutOfBoundsException e) {
					e.printStackTrace();
				} finally {
					tcpClient_listener.DisConnect();
				}
			}
		}
	}

	// 发送数据线程类
	public static boolean SendMessage(String send_Str, Socket socket) {
		try {
			os = socket.getOutputStream();
			if (send_Str != null && !send_Str.trim().equals("")) {
				MySystem.out.println("send");
				os.write(send_Str.getBytes());
				send_Str = null;

				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean CloseThread() {
		OK = false;
		try {
			if (os != null) {
				os.flush();
				os.close();
				os = null;
			}
			if (is != null) {
				is.close();
				is = null;
				os = null;
				socket.close();
				socket = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 发送数据线程类
	/*
	 * class Send_Thread implements Runnable { Socket socket; Send_Thread(Socket
	 * socket) { this.socket = socket; }
	 * 
	 * @Override public void run() { try { while (true) { OutputStream os =
	 * socket.getOutputStream(); while(tcpServer_listener.isRunning){ if
	 * (send_Str != null && !send_Str.trim().equals("")) {
	 * os.write(send_Str.getBytes()); os.flush(); //os.close(); send_Str = null;
	 * } } } } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } }
	 */

	@Override
	public String toString() {
		return socket.toString();
	}

}
