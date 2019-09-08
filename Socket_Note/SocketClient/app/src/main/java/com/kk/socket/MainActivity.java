package com.kk.socket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MainActivity extends AppCompatActivity {

    private TextView tvChatContent;
    private EditText etSocketServerAddress;
    private EditText etSendMsg;
    private ConnectTask connectTask;
    private BufferedWriter writer;
    private boolean connected = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mContext = this;

        Button btnConnect = findViewById(R.id.btn_connect);
        Button btnSend = findViewById(R.id.btn_send);

        tvChatContent = findViewById(R.id.tv_chat_count);
        etSocketServerAddress = findViewById(R.id.et_socket_server_address);
        etSendMsg = findViewById(R.id.et_send_msg);

        tvChatContent.setMovementMethod(ScrollingMovementMethod.getInstance());


        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });

        SharedPreferences sp = mContext.getSharedPreferences("setting", MODE_PRIVATE);
        String localIP = sp.getString("ip", null);
        int localPort = sp.getInt("port", 0);

        if (localIP != null && localPort != 0) {
            etSocketServerAddress.setText(localIP + ":" + localPort);
        }
    }

    private void sendMsg() {
        String sendMsg = etSendMsg.getText().toString().trim();
        if (TextUtils.isEmpty(sendMsg)) {
            Toast.makeText(this, "请输入发送内容！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (writer == null) {
            Toast.makeText(this, "无法发送！", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            //这个换行符特别重要，如果不加，消息就发送不出去
            writer.write(Build.MODEL + ":" + sendMsg + "\n");
            etSendMsg.setText("");
            //同时也显示在我这个客户端
            tvChatContent.append("我：" + sendMsg + "\n");
            tvChatContent.scrollTo(0,tvChatContent.getText().toString().length());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void connect() {
        if (connected) {
            Toast.makeText(this, "已经连接通了！", Toast.LENGTH_SHORT).show();
            return;
        }
        //todo 要在子线程连接
        String inputServer = etSocketServerAddress.getText().toString().trim();
        if (TextUtils.isEmpty(inputServer)) {
            Toast.makeText(this, "服务器地址不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        String ipAddress = inputServer.split(":")[0];
        int portNum = Integer.parseInt(inputServer.split(":")[1]);

        if (connectTask == null) {
            connectTask = new ConnectTask(ipAddress, portNum);
            connectTask.execute();
        } else {
            Toast.makeText(this, "正在连接！", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 连接 Socket 服务器任务
     */
    class ConnectTask extends AsyncTask<Void, String, Void> {
        private String ipAddress;
        private int port;
        private ProgressDialog progressDialog;

        public ConnectTask(String ipAddress, int port) {
            this.ipAddress = ipAddress;
            this.port = port;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("正在连接...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BufferedReader reader = null;
            writer = null;
            try {
                Socket socket = new Socket();
                InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, port);
                socket.connect(inetSocketAddress, 5000);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                publishProgress("success");
                //保存地址到本地
                SharedPreferences sp = mContext.getSharedPreferences("setting", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("ip", ipAddress);
                edit.putInt("port", port);
                edit.commit();
                connected = true;
                String line = null;
                while ((line = reader.readLine()) != null) {
                    publishProgress(line);
                }


            } catch (IOException e) {
                e.printStackTrace();
                publishProgress("error");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (values[0].equals("success")) {

                progressDialog.dismiss();
                tvChatContent.setText("连接服务器：" + ipAddress + ":" + port + " 成功！\n");
            } else if (values[0].equals("error")) {
                tvChatContent.setText("连接服务器：" + ipAddress + ":" + port + " 失败！\n");
            } else {
                tvChatContent.append("Server:" + values[0] + "\n");
                tvChatContent.scrollTo(0,tvChatContent.getText().toString().length());
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
