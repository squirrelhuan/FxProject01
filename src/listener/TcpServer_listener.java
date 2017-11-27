package listener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Tcp_model.MyTcpServerSocket;
import model.Tcp_model.Tcp_userDao;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TcpServer_listener {
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	public static ObservableList<MyTcpServerSocket> observableList;
	public static ArrayList<MyTcpServerSocket> SocketList = new ArrayList<MyTcpServerSocket>();
	private Thread receive_Thread = null;
	private String IP_Server_Str, Port_Server_Str = "40000",
			Port_Server_Str_tmp = "40000";
	public static boolean isRunning = false;
	public static TextArea received_TextArea_d;
	private static boolean selectAll = true;
	public static int lineCount = 0;
	public static ArrayList<CheckBox> CheckBoxList = new ArrayList<CheckBox>();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

	private String recvStr, send_Str = null;
	@FXML
	private TextArea edit_TextArea;
	@FXML
	public ListView<MyTcpServerSocket> user_online_ListView = new ListView<MyTcpServerSocket>();
	public static ListView<MyTcpServerSocket> user_online_ListView_d = new ListView<MyTcpServerSocket>();
	@FXML
	private TextField Port_Server_TextField;
	@FXML
	public TextArea received_TextArea;
	@FXML
	private Button start_btn, stop_btn;
	@FXML
	private RadioButton all_rdbtn, part_rdbtn;
	private static RadioButton all_rdbtn_d, part_rdbtn_d;
	@FXML
	private Label IP_Server_Label, Port_Server_Label, IP_Client_Label,
			Port_Client_Label;

	/** 初始化 */
	@FXML
	private void initialize() {
		received_TextArea_d = received_TextArea;
		part_rdbtn_d = part_rdbtn;
		user_online_ListView_d = user_online_ListView;
		Port_Server_Str_tmp = Port_Server_Str;

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

		IP_Server_Str = SocketUtil.getInetAddress().getHostAddress();
		IP_Server_Label.setText(IP_Server_Str);
		Port_Server_TextField.setText(Port_Server_Str);
		/*
		 * IP_Client_Label.setText(IP_Client_Str);
		 * Port_Client_Label.setText("Port_Client_Str");
		 */

		// IP_Label.setText(ip_Str);
	}

	/** 开启线程 */
	@FXML
	public void onStartTcp(ActionEvent event) {
		start_btn.setDisable(true);
		Port_Server_Str = Port_Server_Str_tmp;
		isRunning = true;
		if (receive_Thread == null) {
			receive_Thread = new Thread(new Recv_Thread(this));
			receive_Thread.start();
		}
		stop_btn.setDisable(false);
	}

	@FXML
	public void onStopTcp(ActionEvent event) {
		stop_btn.setDisable(true);
		isRunning = false;
		
		if (serverSocket != null) {
			try {
				Socket socket_t = new Socket(IP_Server_Str,
						Integer.valueOf(Port_Server_Str));
				socket_t.close();
				socket_t = null;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				MySystem.out.println("重启失败!");
				e.printStackTrace();
			}
		}
		MySystem.out.println("已停止监听 ");

		CheckBoxList.clear();
		SocketList.clear();
		observableList = FXCollections
				.observableArrayList(SocketList);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				user_online_ListView.setItems(observableList);
				user_online_ListView
						.setCellFactory((
								ListView<MyTcpServerSocket> l) -> new ColorRectCell());
			}
		});
		start_btn.setDisable(false);
	}

	/** 发送数据 */
	@FXML
	public void onSendMessage(ActionEvent event) throws IOException {
		if (isRunning) {
			if (SocketList.size() > 0) {
				// 数据校验
				if (edit_TextArea.getText() == null
						|| edit_TextArea.getText().trim().equals("")) {
					return;
				}
				// 设置要发送的数据
				send_Str = edit_TextArea.getText();
				for (MyTcpServerSocket skt : SocketList) {
					System.out.println("i");
					if (skt.isSelected) {
						MySystem.out.println("skt.send_Str:" + skt.send_Str);
						skt.SendMessage(send_Str, skt.getSocket());
					}
				}
				// 发送
				// if (SocketList.get(0).SendMessage(send_Str)) {
				received_TextArea
						.appendText(/* df.format(new Date()) */LocalDateTime
								.now()
								+ " 发送："
								+ edit_TextArea.getText()
								+ "\n\r");
				edit_TextArea.setText("");
				// }
			} else {
				MySystem.out.println("发送失败，当前无用户连接！");
			}
		} else {
			MySystem.out.println("发送失败，请先开启监听在发送数据！");
		}
	}

	// 发送数据线程类
	class Recv_Thread implements Runnable {
		TcpServer_listener tcpServer_listener;

		Recv_Thread(TcpServer_listener tcpServer_listener) {
			this.tcpServer_listener = tcpServer_listener;
		}

		@Override
		public void run() {
			
			while (true) {
				boolean go = false;
				while (isRunning) {
					try {
						serverSocket = new ServerSocket(
								Integer.valueOf(Port_Server_Str));
						go = true;
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					MySystem.out.println("服务器启动,端口号：" + Port_Server_Str);
					while (isRunning && go) {
						// 一直处于监听状态,这样可以处理多个用户
						try {
							socket = serverSocket.accept();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (isRunning) {
							MySystem.out.println("用户已连接！");
							MyTcpServerSocket mySocket = new MyTcpServerSocket(
									tcpServer_listener, socket);
							SocketList.add(mySocket);
							observableList = FXCollections
									.observableArrayList(SocketList);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									user_online_ListView
											.setItems(observableList);
									user_online_ListView
											.setCellFactory((
													ListView<MyTcpServerSocket> l) -> new ColorRectCell());
								}
							});
						}
					}
					try {
						socket.close();
						socket = null;
						serverSocket.close();
						serverSocket = null;
						MySystem.out.println("ServerSocket已释放！");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	static class ColorRectCell extends ListCell<MyTcpServerSocket> {

		@Override
		public void updateItem(MyTcpServerSocket item, boolean empty) {
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
								item.isSelected = (new_val ? true : false);
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

	public void RefreshUI(MyTcpServerSocket mySocket) {
		SocketList.remove(mySocket);
		MySystem.out.println("断开连接" + SocketList.size());
		mySocket = null;
		observableList = FXCollections.observableArrayList(SocketList);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				user_online_ListView.setItems(observableList);
				user_online_ListView.setCellFactory((
						ListView<MyTcpServerSocket> l) -> new ColorRectCell());
			}
		});
	}

	@FXML
	public void onClicketModifyPortButton(ActionEvent event) {

		if (Port_Server_TextField.disableProperty().getValue()) {
			Port_Server_TextField.setDisable(false);
			((Button) event.getSource()).setText("完成");
		} else {
			if (isNumeric(Port_Server_TextField.getText())) {
				if (0 < Integer.valueOf(Port_Server_TextField.getText())
						&& Integer.valueOf(Port_Server_TextField.getText()) < 65535) {
					if (!Port_Server_TextField.getText().equals(
							Port_Server_Str_tmp)) {
						MySystem.out.println("端口已更改请从新开启监听服务！");
						if (isRunning)
							start_btn.setDisable(false);
						stop_btn.setDisable(true);
						isRunning = false;
						Port_Server_Str_tmp = Port_Server_TextField.getText();

						if (serverSocket != null) {
							try {
								Socket socket_t = new Socket(IP_Server_Str,
										Integer.valueOf(Port_Server_Str));
								socket_t.close();
								socket_t = null;
							} catch (UnknownHostException e) {
								e.printStackTrace();
							} catch (IOException e) {
								MySystem.out.println("重启失败!");
								e.printStackTrace();
							}
						}
						MySystem.out.println("已停止监听 ");

						CheckBoxList.clear();
						SocketList.clear();
						observableList = FXCollections
								.observableArrayList(SocketList);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								user_online_ListView.setItems(observableList);
								user_online_ListView
										.setCellFactory((
												ListView<MyTcpServerSocket> l) -> new ColorRectCell());
							}
						});
					}
				} else {
					Port_Server_TextField.setText(Port_Server_Str_tmp);
				}
			} else {
				Port_Server_TextField.setText(Port_Server_Str_tmp);
			}
			Port_Server_TextField.setDisable(true);
			((Button) event.getSource()).setText("更改");
		}

	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
