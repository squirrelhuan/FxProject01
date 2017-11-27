package test.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class socketService {

	public static List<String> ips = new ArrayList<String>();
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(3000);
		System.out.println("begin");

		while (true) {

			new Thread(new TheadServer(ss));
		}
	}
}