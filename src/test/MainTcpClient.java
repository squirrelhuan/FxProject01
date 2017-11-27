package test;

import java.net.Socket;


public class MainTcpClient
{

    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("127.0.0.1", 40000);

        new ClientInputThread(socket).start();
        new ClientOutputThread(socket).start();

    }
}

