package test.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.alibaba.fastjson.JSON;

import model.Udp_model.UdpData;

public class udptest {

	public  static void main(String[] args){
		SendHeartMessage("");
	}
	
	private static Thread heartbeat_thread;
	private static DatagramSocket client;
	public static void SendHeartMessage(String ImagePath){
		
		try {
			if(client==null) {
				client = new DatagramSocket();
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		
		if (heartbeat_thread==null) {
			heartbeat_thread = new Thread(new Heartbeat_Thread(ImagePath));
			heartbeat_thread.start();
		}
	}
	
	// 心跳线程类
	static	 class Heartbeat_Thread implements Runnable {
			String path;
			Heartbeat_Thread (String path){
				this.path = path;
			}
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(6000);
						// 开门
						//Log.i("CGQ", "service:send message...");

					/*	UdpData udpData = new UdpData();
						udpData.setId(001);
						com.huan.administrator.screensharingmobile.bean.Message message = new com.huan.administrator.screensharingmobile.bean.Message();
						message.setContent("ni hao a");
						User user_sender = new User();
						user_sender.setUsername("978252909");
						user_sender.setPassword("112233");
						user_sender.setId(1);
						User user_reciver = new User();
						user_reciver.setId(1);
						message.setReciveUser(user_reciver);
						message.setSendUser(user_sender);
						udpData.setMessage(message);*/

						String sendStr = JSON.toJSONString("okok");
						//Log.d(TAG, "sendStr=" + sendStr);
						byte[] sendBuf;
						sendBuf = (";!"+sendStr).getBytes();
						String ip = "192.168.31.246";
						//ip = "192.168.31.144";
						InetAddress addr = InetAddress
								.getByName(ip);
						int port = 5051;
						port=26891;
						//port = 5554;
						DatagramPacket sendPacket = new DatagramPacket(sendBuf,
								sendBuf.length, addr, port);
						client.send(sendPacket);

						System.out.println("end" + ",ip:"+sendPacket.getAddress()+",port"+ sendPacket.getPort());
						//sendImage(this.path,addr,port);

					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} /*catch (SocketException e) {
						e.printStackTrace();
					}*/ catch (IOException e) {
						e.printStackTrace();
					}
					//Log.i(TAG, "ServiceDemo is running...");
				}
			}
		}
}
