package socket;

import java.util.Vector;

public class ChatManager {

    private static final ChatManager chatManager = new ChatManager();
    private  Vector<ChatSocket> vector = new Vector<>();
    private ChatManager(){}

    public static ChatManager getChatManager(){
        return chatManager;
    }



    public void add(ChatSocket chatSocket){
        vector.add(chatSocket);
    }


    public void publishMsg(ChatSocket chatSocket,String msg){
        for (int i = 0; i < vector.size(); i++) {

            //判断如果给某一个会话发了消息了，那么就不要重复发送
            ChatSocket itemChatSocket = vector.get(i);
            if (!chatSocket.equals(itemChatSocket)) {
                //这个会发给所有的客户端
                itemChatSocket.printOut(msg);
                System.out.println("已经发过了");
            }else {
                System.out.println("发给别别人");

            }
        }
    }
}
