package test;

import java.io.IOException;
import java.io.InputStream;

import model.Tcp_model.MyTcpServerSocket;
/**
 * 输入流获取消息
 * @author CGQ
 *
 */
public class TcpServerInputThread extends Thread
{
    private MyTcpServerSocket socket;

    public TcpServerInputThread(MyTcpServerSocket socket)
    {
        super();
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            // 获得输入流
            InputStream is = socket.getInputStream();

            while (true)
            {
                byte[] buffer = new byte[1024];

                int length = is.read(buffer);

                String str = new String(buffer, 0, length);

                System.out.println("收到消息："+str);

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}