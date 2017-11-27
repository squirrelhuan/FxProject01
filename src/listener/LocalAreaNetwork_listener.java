package listener;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import provider.LogCateProvider;
import provider.TaskManagerProvider;
import application.APK_Setting_Dialog;
import application.LogCate_NewTag_Dialog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import event.LogCateEvent.LogCateManager;
import event.PushFileEvent.PushFileManager;
import listener.TcpServer_listener.ColorRectCell;
import listener.window.LogCate_listener;
import model.CGQ_log.DataEunm;
import model.CGQ_log.DataModel;
import model.CGQ_log.FileModel;
import model.CGQ_log.MyLogBean;
import model.Net_model.NetClient;
import model.Task.TaskModel;
import model.Task.TaskThread;
import model.Tcp_model.MyTcpServerSocket;
import model.Udp_model.UdpData;
import model.Udp_model.UdpMessage;
import model.Udp_model.User;
import model.Udp_model.UserDao;
import model.Udp_model.enums;
import model.View_model.Json_Tab;
import model.View_model.LogCate;
import util.MySystem;
import util.NetUtils;
import util.NetUtils.OnFindDevicedListener;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class LocalAreaNetwork_listener {
	private static DatagramSocket socket = null;
	private Thread receive_Thread = null, send_Thread = null, ip_Thread = null;
	private String IP_Server_Str, Port_Server_Str = "6892",
			IP_Client_Str = "未知";
	int Port_Client_Str = 0;
	private static ArrayList<NetClient> userDaoList = new ArrayList<NetClient>();
	public static ObservableList<NetClient> observableList;
	public static ArrayList<CheckBox> CheckBoxList = new ArrayList<CheckBox>();
	private static boolean selectAll = true;

	private boolean isRunning = false;
	public static int lineCount = 0;
	public static String apk_path = "user.home";;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

	private static String recvStr;
	private String send_Str;
	@FXML
	private RadioButton all_rdbtn, part_rdbtn/* ,clear_rdbtn */;
	private static RadioButton all_rdbtn_d, part_rdbtn_d;
	@FXML
	public ListView<NetClient> user_online_ListView = new ListView<NetClient>();
	public static ListView<NetClient> user_online_ListView_c = new ListView<NetClient>();
	private static ArrayList<NetClient> list_netclient = new ArrayList<NetClient>();
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
		onlineCount_Label_c = onlineCount_Label;

		part_rdbtn_d = part_rdbtn;
		user_online_ListView_c = user_online_ListView;

		ToggleGroup group = new ToggleGroup();
		all_rdbtn.setToggleGroup(group);
		part_rdbtn.setToggleGroup(group);
		part_rdbtn.selectedProperty().set(true);
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

		if (ip_Thread == null) {/* 注意先接受数据 再发送数据 * */
			// 接收
			ip_Thread = new Thread(new IP_Thread());
			ip_Thread.start();
		}
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
			while (true) {
				if (isRunning) {
					try {
						byte[] recvBuf = new byte[1024 * 50];
						DatagramPacket recvPacket = new DatagramPacket(recvBuf,
								recvBuf.length);
						socket.receive(recvPacket);
						IP_Client_Str = recvPacket.getAddress()
								.getHostAddress();
						MySystem.out.println(IP_Client_Str);
						Port_Client_Str = recvPacket.getPort();
						recvStr = new String(recvPacket.getData(), 0,
								recvPacket.getLength());

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								JSON jobject = JSON.parseObject(recvStr);
								MyLogBean myLogBean = JSON.toJavaObject(
										jobject, MyLogBean.class);
								LogCateProvider
										.getInstance()
										.getLogs()
										.add(new LogCate(myLogBean.getLevel(),
												"leve", LocalTime.now(),
												myLogBean.getApplication(),
												myLogBean.getTag() + "",
												myLogBean.getContent()));
								MySystem.out.println(df.format(new Date())
										+ " 收到:" + ",port:" + Port_Client_Str
										+ recvStr + "\n\r");
								if (LogCateProvider.getInstance().isState()) {
									received_TextArea.appendText(df
											.format(new Date())
											+ " 收到来自"
											+ IP_Client_Str
											+ "的消息:"
											+ recvStr
											+ "\n\r");
								}
							}
						});

						/*
						 * 泡泡数据解析使用 analysisData(recvPacket);
						 * 
						 * String sendStr = "server ok"; int port =
						 * recvPacket.getPort(); InetAddress addr =
						 * recvPacket.getAddress(); byte[] sendBuf; sendBuf =
						 * sendStr.getBytes(); DatagramPacket sendPacket = new
						 * DatagramPacket(sendBuf, sendBuf.length, addr, port);
						 * try { socket.send(sendPacket); } catch (IOException
						 * e) { // TODO Auto-generated catch block
						 * e.printStackTrace(); }
						 */
					} catch (SocketException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	// 接收数据线程类
	class IP_Thread implements Runnable {
		@Override
		public void run() {
			analysisData();
		}
	}

	@FXML
	public void onClicketModifyPortButton(ActionEvent event) {
	}

	/** 发送数据 */
	@FXML
	public void onSendMessage(ActionEvent event) throws IOException {
		MySystem.out.println("send...");
		for (NetClient netClient : userDaoList) {
			if (netClient.isSelected()) {
				sendmessage(netClient, edit_TextArea.getText());
			}
		}
		edit_TextArea.clear();
	}

	class ColorRectCell extends ListCell<NetClient> {

		@Override
		public void updateItem(NetClient item, boolean empty) {
			super.updateItem(item, empty);
			// Rectangle rect = new Rectangle(100, 20);
			// rect.setFill(Color.web(item));
			if (item != null) {
				CheckBox checkbox = new CheckBox();
				CheckBoxList.add(checkbox);
				checkbox.setSelected(item.isSelected());
				checkbox.selectedProperty().addListener(
						new ChangeListener<Boolean>() {
							public void changed(
									ObservableValue<? extends Boolean> ov,
									Boolean old_val, Boolean new_val) {
								if (selectAll) {
									part_rdbtn_d.selectedProperty().set(true);
									selectAll = false;
								}
								item.setSelected(new_val);
								// item.isSelected = (new_val ? true : false);
							}
						});
				Label label = new Label(item.getHostName() + " ("
						+ item.getIp().trim() + ")");
				final ContextMenu cm = new ContextMenu();
				MenuItem cmItem1 = new MenuItem("install apk");
				cmItem1.setOnAction((ActionEvent e) -> {
					/*
					 * Clipboard clipboard = Clipboard.getSystemClipboard();
					 * ClipboardContent content = new ClipboardContent();
					 * //content.putImage(pic.getImage());
					 * clipboard.setContent(content);
					 */
					NetClient netClient = new NetClient();
					netClient.setIp(item.getIp());
					sendmessage(netClient, "install apk");
				});
				MenuItem cmItem2 = new MenuItem("apk path");
				cmItem2.setOnAction((ActionEvent e) -> {
					new APK_Setting_Dialog().start(new Stage());
				});
				cm.getItems().add(cmItem1);
				cm.getItems().add(cmItem2);

				FlowPane flowPane = new FlowPane();
				flowPane.getChildren().add(checkbox);
				flowPane.getChildren().add(label);
				flowPane.addEventHandler(
						MouseEvent.MOUSE_CLICKED,
						(MouseEvent e) -> {
							if (e.getButton() == MouseButton.SECONDARY) {
								cm.show(flowPane.getParent(), e.getScreenX(),
										e.getScreenY());
							}
						});
				// flowPane.setOnMouseClicked(value);
				setGraphic(flowPane);
			}
		}
	}

	public static void onClickedSelectAll() {
		for (CheckBox cb : CheckBoxList) {
			cb.setSelected(true);
		}
	}

	public void analysisData() {

		List<String> list_ip = NetUtils.getIPs();
		for (String ip : list_ip) {
			NetClient nClient = new NetClient();
			nClient.setIp(ip);
			boolean isExit = false;
			for (NetClient netClient : userDaoList) {
				if (ip.equals(netClient.getIp())) {
					isExit = true;
				}
			}
			if (!isExit) {
				userDaoList.add(nClient);
			}
		}

		NetUtils.getHostnames(list_ip, new OnFindDevicedListener() {
			@Override
			public void onFindNew(ArrayList<NetClient> map) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for (NetClient xClient : map) {
							boolean isExit = false;
							for (NetClient yClient : userDaoList) {
								if (yClient.getIp().trim()
										.equals(xClient.getIp().trim())) {
									yClient.setHostName(xClient.getHostName());
									break;
								}
							}
						}
						onlineCount_Label_c.setText(userDaoList.size() + "");
						observableList = FXCollections
								.observableArrayList(userDaoList);
						user_online_ListView_c.setItems(observableList);
						user_online_ListView_c.setCellFactory((
								ListView<NetClient> l) -> null);
						user_online_ListView_c.setCellFactory((
								ListView<NetClient> l) -> new ColorRectCell());
					}
				});
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				onlineCount_Label_c.setText(userDaoList.size() + "");
				observableList = FXCollections.observableArrayList(userDaoList);
				user_online_ListView_c.setItems(observableList);
				user_online_ListView_c
						.setCellFactory((ListView<NetClient> l) -> null);
				user_online_ListView_c
						.setCellFactory((ListView<NetClient> l) -> new ColorRectCell());

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

	}

	/**
	 * 发送消息
	 * @param netClient 对方socket信息
	 * @param sendStr 要发送的数据
	 * @return 
	 */
	public boolean sendmessage(NetClient netClient, String sendStr) {
		// System.out.println("netClient:");
		FileModel fileModel = new FileModel();
		File file = new File(LocalAreaNetwork_listener.apk_path);
		fileModel.setPath(file.getName());
		DataModel dataModel = new DataModel(DataEunm.File, fileModel);
		sendStr = JSON.toJSONString(dataModel);
		String ip = netClient.getIp().trim();
		int port = 3149;
		byte[] sendBuf;
		sendBuf = sendStr.getBytes();
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		MySystem.out.println("ip:" + ip + ",port:" + port + "," + sendStr);
		DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length,
				addr, port);
		try {
			if(socket==null){
				MySystem.out.println("socket服务未开启，请先开启服务再操作！");
				return false;
			}
			socket.send(sendPacket);
			MySystem.out.println("消息已发送！");
			if (dataModel.getType() == DataEunm.File) {
				pushFile(ip);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void pushFile(String ip) throws IOException {

		// TaskThread taskThread = new TaskThread();

		/*
		 * Tcp_Thread th = new Tcp_Thread(ip); th.setDaemon(false); th.start();
		 * // 启动线程运行
		 */

		TaskModel taskModel = new TaskModel("install apk");
		Tcp_TaskThread mTaskThread = new Tcp_TaskThread(taskModel, ip);
		TaskManagerProvider.getList_task().add(taskModel);
	}

	private class Tcp_TaskThread extends TaskThread {
		String ip;

		protected Tcp_TaskThread(TaskModel taskModel, String ip) {
			super(taskModel);
			this.ip = ip;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void running() {
			int length = 0;
			double sumL = 0;
			byte[] sendBytes = null;
			Socket socket = null;
			DataOutputStream dos = null;
			FileInputStream fis = null;
			boolean bool = false;
			try {
				this.sleep(1000);
				File file = new File(LocalAreaNetwork_listener.apk_path); // 要传输的文件路径
				long l = file.length();
				socket = new Socket();
				socket.connect(new InetSocketAddress(ip, 48127));
				dos = new DataOutputStream(socket.getOutputStream());
				fis = new FileInputStream(file);
				sendBytes = new byte[1024 * 10];
				while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
					sumL += length;
					System.out.println("已传输：" + ((sumL / l) * 100) + "%");
					setProcess(sumL / l);
					// PushFileManager.getInstance().onProcessChanged((sumL / l)
					// );
					dos.write(sendBytes, 0, length);
					dos.flush();
				}
				// 虽然数据类型不同，但JAVA会自动转换成相同数据类型后在做比较
				if (sumL == l) {
					bool = true;
				}

			} catch (Exception e) {

				if (e.getMessage().equals("Connection timed out: connect")) {
					MySystem.out.println("连接超时,请检查手机服务是否开启！");
					bool = false;
				} else if (e.getMessage().equals("Connection refused: connect")) {
					MySystem.out.println("拒绝连接,ip:" + ip);
				} else {
					e.printStackTrace();
				}
			} finally {
				try {
					if (dos != null)
						dos.close();
					if (fis != null)
						fis.close();
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(bool ? "成功" : "失败");
		}
	}

	private class Tcp_Thread extends Thread {
		String ip;

		Tcp_Thread(String ip) {
			this.ip = ip;
		}

		@Override
		public void run() {

			int length = 0;
			double sumL = 0;
			byte[] sendBytes = null;
			Socket socket = null;
			DataOutputStream dos = null;
			FileInputStream fis = null;
			boolean bool = false;
			try {
				File file = new File(LocalAreaNetwork_listener.apk_path); // 要传输的文件路径
				long l = file.length();
				socket = new Socket();
				socket.connect(new InetSocketAddress(ip, 48127));
				dos = new DataOutputStream(socket.getOutputStream());
				fis = new FileInputStream(file);
				sendBytes = new byte[1024 * 10];
				while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
					sumL += length;
					System.out.println("已传输：" + ((sumL / l) * 100) + "%");
					PushFileManager.getInstance().onProcessChanged((sumL / l));
					dos.write(sendBytes, 0, length);
					dos.flush();
				}
				// 虽然数据类型不同，但JAVA会自动转换成相同数据类型后在做比较
				if (sumL == l) {
					bool = true;
				}

			} catch (Exception e) {
				if (e.getMessage().equals("Connection timed out: connect")) {
					MySystem.out.println("连接超时,请检查手机服务是否开启！");
					bool = false;
				} else if (e.getMessage().equals("Connection refused: connect")) {
					MySystem.out.println("拒绝连接,ip:" + ip);
				} else {
					e.printStackTrace();
				}
			} finally {
				try {
					if (dos != null)
						dos.close();
					if (fis != null)
						fis.close();
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(bool ? "成功" : "失败");
		}
	}

}
