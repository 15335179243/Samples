package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatSocket extends Thread {

    private Socket socket;

    public ChatSocket(Socket socket) {
        this.socket = socket;

    }

    public void printOut(String outStr) {
        try {
            socket.getOutputStream().write((outStr+"\n").getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        //获取 socket 输出流
        try {

            //接收数据
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(), "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                ChatManager.getChatManager().publishMsg(this, line);
            }
            br.close();

            //todo 模拟往客户端写数据
            /*int count = 0;
            while (true) {
                printOut("loop:" + count++ + "\n");
                Thread.sleep(1000);
            }*/
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
