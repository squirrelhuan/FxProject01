package listener;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Tcp_model.MyTcpClientSocket;
import util.MySystem;
import util.SocketUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TcpClient_listener {
	private Socket socket = null;
	private String IP_Server_Str = "127.0.0.1", Port_Server_Str = "40000";
	private boolean isRunning = false;
	public static int lineCount = 0;
	private MyTcpClientSocket myClientSocket = null;

	private String recvStr, send_Str = null;
	@FXML
	private TextArea edit_TextArea, received_TextArea;
	@FXML
	private TextField Port_Server_TextField, IP_Server_TextField;
	@FXML
	private Button start_btn, stop_btn;

	/*
	 * @FXML private TextField Port_Server_Label, IP_Client_Label,
	 * Port_Client_Label;
	 */

	/** 初始化 */
	@FXML
	private void initialize() {
		IP_Server_Str = SocketUtil.getInetAddress().getHostAddress();
		IP_Server_TextField.setText(IP_Server_Str);
		Port_Server_TextField.setText(Port_Server_Str);

	}

	/** 开启线程 */
	@FXML
	public void onStartTcp(ActionEvent event) {
		start_btn.setDisable(true);
		isRunning = true;
		connect();
		stop_btn.setDisable(false);
	}

	@FXML
	public void onStopTcp(ActionEvent event) {
		fail();
	}

	/** 发送数据 */
	@FXML
	public void onSendMessage(ActionEvent event) throws IOException {
		if (isRunning) {
			if (myClientSocket != null) {
				// 数据校验
				if (edit_TextArea.getText() == null
						|| edit_TextArea.getText().trim().equals("")) {
					return;
				}
				// 设置要发送的数据
				send_Str = edit_TextArea.getText();
				// 发送
				myClientSocket
						.SendMessage(send_Str, myClientSocket.getSocket());
				received_TextArea
						.appendText(/* df.format(new Date()) */LocalDateTime
								.now()
								+ " 发送："
								+ edit_TextArea.getText()
								+ "\n\r");
				edit_TextArea.setText("");
			} else {
				MySystem.out.println("发送失败，未连接上服务器！");
			}
		} else {
			MySystem.out.println("发送失败，请先开启监听在发送数据！");
		}

	}

	public void connect(){
		try {
			Socket socket = new Socket(IP_Server_Str, 40000);
			myClientSocket = new MyTcpClientSocket(this, socket);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			MySystem.out.println("连接服务器失败！");
			fail();
		}
	}

	public void DisConnect() {
		stop_btn.setDisable(true);
		isRunning = false;
		MySystem.out.println("断开连接");
		start_btn.setDisable(false);
	}
//失败操作
	public void fail(){
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stop_btn.setDisable(true);
				isRunning = false;
				if (socket != null) {
					myClientSocket.CloseThread();
					try {
						socket.close();
						socket = null;
						myClientSocket = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				MySystem.out.println("已停止监听 ");
				start_btn.setDisable(false);
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
					if (!Port_Server_TextField.getText()
							.equals(Port_Server_Str)) {
						MySystem.out.println("端口已更改请从新开启监听服务！");
						fail();
						Port_Server_Str = Port_Server_TextField.getText();
					}
				} else {
					Port_Server_TextField.setText(Port_Server_Str);
				}
			} else {
				Port_Server_TextField.setText(Port_Server_Str);
			}
			Port_Server_TextField.setDisable(true);
			((Button) event.getSource()).setText("更改");
		}
	}

	@FXML
	public void onClicketModifyIPButton(ActionEvent event) {

		if (IP_Server_TextField.disableProperty().getValue()) {
			IP_Server_TextField.setDisable(false);
			((Button) event.getSource()).setText("完成");
			}else{
				if(isIpv4(IP_Server_TextField.getText()) && !IP_Server_TextField.getText().trim().equals(IP_Server_Str)){
					MySystem.out.println("端口已更改请从新开启监听服务！");
					fail();
					IP_Server_Str = IP_Server_TextField.getText();
				}else{
					IP_Server_TextField.setText(IP_Server_Str);
				}
			IP_Server_TextField.setDisable(true);
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

	public void RefreshUI(String recvStr2) {
		if (lineCount >= 999) {
			received_TextArea.setText("");
			lineCount = 0;
		}
		lineCount++;
		received_TextArea.appendText(LocalDateTime.now() + " 收到：" + recvStr2
				+ "\n\r");
	}
	
	public static boolean isIpv4(String ipAddress) {  
		  
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";  
  
        Pattern pattern = Pattern.compile(ip);  
        Matcher matcher = pattern.matcher(ipAddress);  
        return matcher.matches();  
    } 
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	
}
