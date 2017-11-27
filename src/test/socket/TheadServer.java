package test.socket;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import test.socket.model.VideoRequestModel;

public class TheadServer implements Runnable {
	private Socket s = null;
	private String clientIp;
	private BufferedImage bufferedImage;
	public InputStream ins;

	public TheadServer(ServerSocket ss) throws IOException {
		System.out.println("start thread");
		this.s = ss.accept();
		System.out.println("accept");

		// 从服务器端接收信息

		InputStream is = s.getInputStream();

		byte[] buffer = new byte[200];

		int length = is.read(buffer);
		String str = new String(buffer, 0, length);
		System.out.println(str);
		JSONObject object = JSONObject.parseObject(str);
		VideoRequestModel model = JSONObject.toJavaObject(object,
				VideoRequestModel.class);
		System.out.println("ip=" + s.getLocalAddress());
		
		if (!socketService.ips.contains(s.getLocalAddress())) {
			socketService.ips.add(s.getLocalAddress().toString());
			if (model != null) {
				System.out.println("接收到视频请求：from=" + model.getFrom() + ",to="
						+ model.getTo());
				clientIp = model.getTo();
				this.run();
			}
		}
	}

	@Override
	public void run() {
		try {
			ins = s.getInputStream();
			bufferedImage = ImageIO.read(ins);
			ins.close();
			ThreadClient tc = new ThreadClient(bufferedImage, clientIp);
			new Thread(tc).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				if (!s.isClosed())
					s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}