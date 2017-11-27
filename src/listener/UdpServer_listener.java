package listener;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import listener.TcpServer_listener.ColorRectCell;
import model.Tcp_model.MyTcpServerSocket;
import model.Udp_model.UdpData;
import model.Udp_model.UdpMessage;
import model.Udp_model.User;
import model.Udp_model.UserDao;
import model.Udp_model.enums;
import util.MySystem;
import util.SocketUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

public class UdpServer_listener {
	private static DatagramSocket socket = null;
	private Thread receive_Thread = null, send_Thread = null;
	private String IP_Server_Str, Port_Server_Str = "5051",
			IP_Client_Str = "未知";
	int Port_Client_Str = 0;
	private static ArrayList<User> userDaoList = new ArrayList<User>();
	public static ObservableList<User> observableList;
	public static ArrayList<CheckBox> CheckBoxList = new ArrayList<CheckBox>();
	private static boolean selectAll = true;

	private boolean isRunning = false;
	public static int lineCount = 0;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

	private static String recvStr;
	private String send_Str;
	@FXML
	private RadioButton all_rdbtn, part_rdbtn;
	private static RadioButton all_rdbtn_d, part_rdbtn_d;
	@FXML
	public ListView<User> user_online_ListView = new ListView<User>();
	public static ListView<User> user_online_ListView_c = new ListView<User>();
	@FXML
	private TextArea edit_TextArea;
	@FXML
	private TextArea received_TextArea;
	private static TextArea received_TextArea_c;
	@FXML
	private Button start_btn, stop_btn;
	@FXML
	private Label IP_Server_Label, onlineCount_Label;
	private Label IP_Server_Label_c;
	private static Label onlineCount_Label_c;
	@FXML
	private TextField Port_Server_TextField;

	/** 初始化 */
	@FXML
	private void initialize() {
		IP_Server_Str = SocketUtil.getInetAddress().getHostAddress();
		IP_Server_Label.setText(IP_Server_Str);
		Port_Server_TextField.setText(Port_Server_Str);
		IP_Server_Label_c = IP_Server_Label;
		received_TextArea_c = received_TextArea;
		onlineCount_Label_c =onlineCount_Label;

		part_rdbtn_d = part_rdbtn;
		user_online_ListView_c = user_online_ListView;

		ToggleGroup group = new ToggleGroup();
		all_rdbtn.setToggleGroup(group);
		all_rdbtn.selectedProperty().set(true);
		part_rdbtn.setToggleGroup(group);
		group.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle old_toggle, Toggle new_toggle) {
						if (group.getSelectedToggle() != null) {
							if (group.getSelectedToggle() == all_rdbtn) {
								onClickedSelectAll();
								selectAll = true;
							} else {
								part_rdbtn.selectedProperty().set(true);
								selectAll = false;
							}
						}
					}
				});
	}

	/** 开启线程 */
	@FXML
	public void onStartUdp(ActionEvent event) {
		start_btn.setDisable(true);
		stop_btn.setDisable(false);
		isRunning = true;
		if (receive_Thread == null) {/* 注意先接受数据 再发送数据 * */
			// 接收
			receive_Thread = new Thread(new Receive_Thread());
			receive_Thread.start();
		}
		MySystem.out.println("udpServer start ...");
	}

	@FXML
	public void onStopUdp(ActionEvent event) {
		start_btn.setDisable(false);
		stop_btn.setDisable(true);
		isRunning = false;
		MySystem.out.println("udpServer end ...");
	}

	/** 发送数据 */
	@FXML
	public void onSendUdpServerMessage(ActionEvent event) throws IOException {
		if (isRunning) {
			// 数据校验
			if (edit_TextArea.getText() == null
					|| edit_TextArea.getText().trim().equals("")) {
				return;
			}
			// 设置要发送的数据
			send_Str = edit_TextArea.getText();
			// 发送
			if (sendMessage()) {
				received_TextArea.appendText(df.format(new Date()) + " 发送:"
						+ edit_TextArea.getText() + "\n\r");
				edit_TextArea.setText("");
			}
		} else {
			MySystem.out.println("发送失败，请先开启监听在发送数据！");
		}
	}


	// 接收数据线程类
	class Receive_Thread implements Runnable {
		@Override
		public void run() {
			// 创建DatagramSocket对象并指定一个端口号，注意，如果客户端需要接收服务器的返回数据,
			// 还需要使用这个端口号来receive，所以一定要记住
			if (socket == null) {
				try {
					socket = new DatagramSocket(
							Integer.valueOf(Port_Server_Str));
					// get name representing the running Java virtual machine.
					String name = ManagementFactory.getRuntimeMXBean()
							.getName();
					System.out.println(name);
					// get pid
					String pid = name.split("@")[0];
					System.out.println("Pid is:" + pid);
				} catch (SocketException e1) {
					if (e1.getMessage().equals(
							"Address already in use: Cannot bind")) {
						MySystem.out.println("e1=" + e1.getMessage() + "\n\r"
								+ Port_Server_Str + "端口被 占用");
					}
					e1.printStackTrace();
				}
			}
			byte data1[] = new byte[4 * 1024];
			while (true) {
				if (isRunning) {
					try {
						byte[] recvBuf = new byte[100];
						DatagramPacket recvPacket = new DatagramPacket(recvBuf,
								recvBuf.length);
						socket.receive(recvPacket);
						IP_Client_Str = recvPacket.getAddress().getHostAddress();
						MySystem.out.println(IP_Client_Str);
						Port_Client_Str = recvPacket.getPort();
						recvStr = new String(recvPacket.getData(), 0,
								recvPacket.getLength());
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								received_TextArea.appendText(df.format(new Date()) + " 收到:"
										+ recvStr + "\n\r");
							}
						});
						
						
						
						/* 泡泡数据解析使用
						analysisData(recvPacket);
						
						String sendStr = "server ok";
						int port = recvPacket.getPort();
						InetAddress addr = recvPacket.getAddress();
						byte[] sendBuf;
						sendBuf = sendStr.getBytes();
						DatagramPacket sendPacket = new DatagramPacket(sendBuf,
								sendBuf.length, addr, port);
						try {
							socket.send(sendPacket);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
					} catch (SocketException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					
				}
			}
		}
	}

	@FXML
	public void onClicketModifyPortButton(ActionEvent event) {
	}

	/** 发送数据 */
	@FXML
	public void onSendMessage(ActionEvent event) throws IOException {
		if(sendMessage2(edit_TextArea.getText())){
			edit_TextArea.setText("");
		}
	}
    //泡泡数据
	private boolean sendMessage() {

		try {
			// 创建DatagramSocket对象并指定一个端口号，注意，如果客户端需要接收服务器的返回数据,
			// 还需要使用这个端口号来receive，所以一定要记住
			if (socket == null) {
				socket = new DatagramSocket(userDaoList.get(1).getPort());
			}
			// 使用InetAddress(Inet4Address).getByName把IP地址转换为网络地址
			InetAddress serverAddress = InetAddress.getByName(userDaoList
					.get(0).getIp()/* "192.168.0.103" */);
			byte data[] = send_Str.getBytes();// 把字符串str字符串转换为字节数组
			// 创建一个DatagramPacket对象，用于发送数据。
			// 参数一：要发送的数据 参数二：数据的长度 参数三：服务端的网络地址 参数四：服务器端端口号
			DatagramPacket packet = new DatagramPacket(data, data.length,
					serverAddress, 8078);
			socket.send(packet);// 把数据发送到服务端。
			MySystem.out.println("send:" + send_Str);
			return true;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	//普通数据
	private boolean sendMessage2(String send_Str) {

		try {
			// 创建DatagramSocket对象并指定一个端口号，注意，如果客户端需要接收服务器的返回数据,
			// 还需要使用这个端口号来receive，所以一定要记住
			if (socket == null) {
				socket = new DatagramSocket(userDaoList.get(1).getPort());
			}
			// 使用InetAddress(Inet4Address).getByName把IP地址转换为网络地址
			InetAddress serverAddress = InetAddress.getByName(IP_Client_Str);
			byte data[] = send_Str.getBytes();// 把字符串str字符串转换为字节数组
			// 创建一个DatagramPacket对象，用于发送数据。
			// 参数一：要发送的数据 参数二：数据的长度 参数三：服务端的网络地址 参数四：服务器端端口号
			DatagramPacket packet = new DatagramPacket(data, data.length,
					serverAddress, Port_Client_Str);
			socket.send(packet);// 把数据发送到服务端。
			MySystem.out.println("send:" + send_Str);
			return true;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	static class ColorRectCell extends ListCell<User> {

		@Override
		public void updateItem(User item, boolean empty) {
			super.updateItem(item, empty);
			// Rectangle rect = new Rectangle(100, 20);
			// rect.setFill(Color.web(item));
			if (item != null) {
				CheckBox checkbox = new CheckBox();
				CheckBoxList.add(checkbox);
				checkbox.setSelected(true);
				checkbox.selectedProperty().addListener(
						new ChangeListener<Boolean>() {
							public void changed(
									ObservableValue<? extends Boolean> ov,
									Boolean old_val, Boolean new_val) {
								if (selectAll) {
									part_rdbtn_d.selectedProperty().set(true);
									selectAll = false;
								}
								// item.isSelected = (new_val ? true : false);
							}
						});
				Label label = new Label(item.toString());
				FlowPane flowPane = new FlowPane();
				flowPane.getChildren().add(checkbox);
				flowPane.getChildren().add(label);
				setGraphic(flowPane);
			}
		}
	}

	public static void onClickedSelectAll() {
		for (CheckBox cb : CheckBoxList) {
			cb.setSelected(true);
		}
	}
	
	public static void analysisData(DatagramPacket recvPacket) {
		recvStr = new String(recvPacket.getData(), 0,
				recvPacket.getLength());
		System.out.println("Hello World!" + recvStr);
		int port = recvPacket.getPort();
		InetAddress addr = recvPacket.getAddress();
		UdpData udpData = JSONObject.parseObject(recvStr,
				UdpData.class);
		User user = UserDao.findByID();
		user.setIp(addr.toString());
		user.setPort(port);
		int sendUserId = user.getId();
		int recUserId = 3/*udpData.getUdpMessage().getReciveUser().getId()*/;
		boolean isExit = false;
		boolean recUserIsOnline = false;
		ArrayList<User> userofflineDaoList = new ArrayList<User>();
		for (User user_c : userDaoList) {
			if (user_c.getId() == sendUserId) {
				user_c.setIp(addr.toString());
				user_c.setPort(port);
				isExit = true;
			}else if(user_c.getId() == recUserId){
				recUserIsOnline = true;
			}
			Date date = new Date();
			long l = (date.getTime() - user_c.getDate().getTime());
			System.out.println("time:" + l);
			if (l > 5000) {
				userofflineDaoList.add(user_c);
			}
		}
		for (User user_c : userofflineDaoList) {
			userDaoList.remove(user_c);
		}
		if (!isExit) {
			userDaoList.add(user);
		}
		
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println(userDaoList.size());
				onlineCount_Label_c.setText(userDaoList.size() + "");
				observableList = FXCollections
						.observableArrayList(userDaoList);
				user_online_ListView_c.setItems(observableList);
				user_online_ListView_c.setCellFactory((
						ListView<User> l) -> null);
				user_online_ListView_c
						.setCellFactory((ListView<User> l) -> new ColorRectCell());

				if (recvStr != null) {
					if (lineCount >= 999) {
						received_TextArea_c.setText("");
						lineCount = 0;
					}
					lineCount++;
					received_TextArea_c.appendText(recvStr + "\n\r");
				}
			}
		});
		String sendStr = "server ok";
		enums.requestType type = udpData.getRequestType();
		if(type == enums.requestType.sendData){
			UdpMessage udpMessage = udpData.getUdpMessage();
			udpMessage.getSendUser();
			User reciveUser = UserDao.findByID(sendUserId/*udpMessage.getReciveUser().getId()*/);
			sendStr = udpMessage.getContent();
			int port1 = reciveUser.getPort();
			String ip = reciveUser.getIp();
			if(recUserIsOnline){
			sendmessage(sendStr,ip,port1);
			}else{
				System.out.println("对方不在线...");
			}
		}else if(type == enums.requestType.getData){
			
		}
		
		
	}
	public static boolean sendmessage(String sendStr,String ip, int port){
		byte[] sendBuf;
		sendBuf = sendStr.getBytes();
		InetAddress addr = null;
		try {
			addr =  InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		DatagramPacket sendPacket = new DatagramPacket(sendBuf,
				sendBuf.length, addr, port);
		System.out.println("消息已发送！");
		try {
			socket.send(sendPacket);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
