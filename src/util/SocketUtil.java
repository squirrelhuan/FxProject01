package util;

import java.net.InetAddress;
/**Date 2016.07.16
 * socket相关工具类
 * @author CGQ
 *
 */
public class SocketUtil {
	
	@SuppressWarnings("static-access")
	public static InetAddress getInetAddress(){
	InetAddress inetAddress = null;
	try {
		inetAddress = inetAddress.getLocalHost();
		
		String localname=inetAddress.getHostName();
		String localip=inetAddress.getHostAddress();
		MySystem.out.println("本机名称是："+ localname);
		MySystem.out.println("本机的ip是 ："+localip);
		return inetAddress;
	} catch (Exception e) {
		e.printStackTrace();
		MySystem.out.println("获取本地ip失败");
		return null;
	}
}
}
