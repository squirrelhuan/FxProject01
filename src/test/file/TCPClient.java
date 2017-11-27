package test.file;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @author jace
 * 
 * Functions:
 * 			1.TCPClient，运行与Java上的带主函数的程序客户端
 * 
 * 			2.实现从电脑硬盘上传送文件到局域网内的WIFI连接的Android客户端机器
 * 
 * 			3.该程序实现的为Linux下的文件读取，Windows用户需要自行修改几句代码，便可简单实现（代码已经注释）
 * 
 * 			4.自己动手丰衣足食
 *
 */
public class TCPClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//创建TCP客户端的Socket
			Socket mTCPSocket = new Socket("192.168.1.110", 8888);
			
			System.out.println("-----------------请输入需传送的文件路径-----------------");
			
			System.out.println("---------------格式例如：/home/jace/mm.jpg---------------");
			
			Scanner sc = new Scanner(System.in);
			
			String strPath = sc.next().toString();
			
			System.out.println("文件路径为：strPath = " + strPath + "   strPath的长度 = " + strPath.length());
			
			//创建输入流，并设置输入流传递的文件
			InputStream mInputStream = new FileInputStream(strPath);
			
			//创建输出流对象,并获取输出流
			OutputStream mOutputStream = mTCPSocket.getOutputStream();
			
			//创建数据输出流，并将获得的文件输出流的数据
			DataOutputStream mDataOutputStream = new DataOutputStream(mOutputStream);
			
			//创建字节数组
			byte[] buffer = new byte[1024 * 4];
			
			int temp = 0;
			
			//Windows系统文件路径测试
			String strWindowsPath = "D:\\剪辑版~终.mp4";
			
			//字符串(文件路径)分割，存放于String数组中
		//	String[] strDir = strPath.split(File.separator);
			
			String[] strDir2 = strWindowsPath.split("\\\\");
			
			//打印文件名
//			System.out.println("Linux fileName = " + strDir[strDir.length - 1]);
			
			System.out.println("Windows FileName = " + strDir2[strDir2.length - 1]);
			
			//将分割出来的文件名，写入数据输出流中
			mDataOutputStream.writeUTF(strDir2[strDir2.length - 1] + "!" + mInputStream.available());
			
			//打印开始传送提示，并显示当前时间
			System.out.println("-----------------数据开始传送-----------------");
			System.out.println("--现在系统时间: " + Calendar.getInstance().getTime() + "--");
			
			long length = 0;
			
			System.out.println("length  One------>" + mInputStream.available());
			
			//通过循环读取数据进字节数组，并写进数据输出流中
			while ((temp = mInputStream.read(buffer)) != -1) {
				mOutputStream.write(buffer, 0, temp);
//				mDataOutputStream.write(buffer, 0, temp);
				
				//叠加读取缓冲区的字节数大小
				length += temp;
			}
			
			
			//打印写入缓冲区的文件的字节长度
			System.out.println("length Two----->" + length);
			
			mDataOutputStream.writeLong(length);
			
			
			//刷新并推进输出流
			mDataOutputStream.flush();
			
			//打印数据传送完毕提示，并显示当前系统时间
			System.out.println("----------------------------------------------");
			
			System.out.println("-------------------数据传送完毕-----------------");
			
			System.out.println("-----完成时间: " + Calendar.getInstance().getTime() + "-----");
			
			System.out.println("----------------------------------------------");
			
//			while ((temp = mDataInputStream.read(buffer)) != -1) {
//				mDataOutputStream.write(buffer, 0, temp);
//			}
//			
//			mDataOutputStream.flush();

			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
