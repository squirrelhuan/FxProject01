package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Net_model.NetClient;

// local area network
public class NetUtils {

	// 执行外部命令
	public static void command() {
		String command = "net view";
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 提取局域网IP
	public static List<String> getIPs() {
		List<String> list = new ArrayList<String>();
		boolean flag = false;
		int count = 0;
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec("arp -a");
			InputStreamReader inputStr = new InputStreamReader(
					p.getInputStream(), "GBK");
			BufferedReader br = new BufferedReader(inputStr);
			String inline;
			while ((inline = br.readLine()) != null) {
				if (inline.indexOf("接口") > -1) {
					flag = !flag;
					if (!flag) {
						// 碰到下一个"接口"退出循环
						break;
					}
				}
				if (flag) {
					count++;
					if (count > 2) {
						// 有效IP
						String[] str = inline.split(" {4}");
						String s2 = new String(str[0].getBytes("utf-8"),
								"utf-8");
						// ISO-8859-1”),”GBK”
						// s2.
						list.add(s2);
						// MySystem.out.println(s2);
					}
				}
				// String s2 = new String(s1.getBytes("ISO-8859-1"),"GBK“);
				// MySystem.out.println(inline);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ip共" + list.size() + "条");
		System.out.println(list);
		return list;
	}

	public static ArrayList<NetClient> getHostnames(List<String> ips,
			OnFindDevicedListener oListener) {
		ArrayList<NetClient> map = new ArrayList<NetClient>();
		// MySystem.out.println("正在提取hostname...");

		//System.out.println("ips:" + ips.size());
		for (int i = 0; i < ips.size(); i++) {
			//System.out.println("index:" + i + ",str=" + ips.get(i));
			String ip = ips.get(i);
			String command = "ping -a " + ip;
			Runtime r = Runtime.getRuntime();
			Process p;
			try {
				p = r.exec(command);
				InputStreamReader inputStr = new InputStreamReader(
						p.getInputStream(), "GBK");
				BufferedReader br = new BufferedReader(inputStr);
				/*
				 * BufferedReader br = new BufferedReader(new InputStreamReader(
				 * p.getInputStream()));
				 */
				String inline;
				NetClient netClient = null;
				while ((inline = br.readLine()) != null) {
					if (inline.indexOf("[") > -1) {
						int start = inline.indexOf("Ping ");
						int end = inline.indexOf("[");
						String hostname = inline.substring(
								start + "Ping ".length(), end - 1);
						MySystem.out.println(hostname);
						netClient = new NetClient();
						netClient.setIp(ip);
						netClient.setHostName(hostname);
						map.add(netClient);
						oListener.onFindNew(map);
					}
				}
				if (netClient == null) {
					netClient = new NetClient();
					netClient.setIp(ip);
					netClient.setHostName("移动设备");
					map.add(netClient);
					oListener.onFindNew(map);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(map.size());
		//System.out.println(map);
		// MySystem.out.println(map.size()+"条数据，提取结束！");
		return map;
	}

	public interface OnFindDevicedListener {
		void onFindNew(ArrayList<NetClient> map);
	}
}
