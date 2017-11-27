package test;

import java.io.IOException;
import java.io.OutputStream;

import model.Tcp_model.MyTcpServerSocket;
/**
 * 输出流发送消息
 * @author 
 *
 */
public class TcpServerOutputThread extends Thread
{
    private MyTcpServerSocket socket;

    public TcpServerOutputThread(MyTcpServerSocket socket)
    {
        super();
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {

            OutputStream os = socket.getOutputStream();

            while (true)
            {
              /*  while(socket.sendStr != null){
                os.write(socket.sendStr.getBytes());
                socket.sendStr = null;
                }*/
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}