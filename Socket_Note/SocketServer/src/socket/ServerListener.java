package socket;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {

    @Override
    public void run() {

        int port = 10086;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ChatManager chatManager = ChatManager.getChatManager();
            //一旦有客户端连接到服务端，这个方法就会返回一个新的 socket 实例。

            while (true) {
                Socket socket = serverSocket.accept();
                //建立连接了。可以用浏览器访问：127.0.0.1:10086 测试。
                JOptionPane.showMessageDialog(null, "有客户端连接到本机的 10086 socket 端口了！");

                //客户端链接后就要开一个新的线程
                ChatSocket chatSocket = new ChatSocket(socket);
                chatSocket.start();
                chatManager.add(chatSocket);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
