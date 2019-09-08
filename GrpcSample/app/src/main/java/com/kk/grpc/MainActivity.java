package com.kk.grpc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.grpc.stub.StreamObserver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private StreamObserver<ClientResponse> clientResponseStreamObserver;
    private StreamObserver<ClientRequest> requestStreamObserver;
    private int grpcInterruptConnectCount = 0;
    private static final int MAX_GRPC_CONNECT_COUNT = 5;
    private GrpcExecutor grpcExecutor;
    private TextView tvGrpcState;
    private Button btnSendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGrpcState = findViewById(R.id.tv_grpc_state);
        btnSendMsg = findViewById(R.id.btn_send_msg);
        btnSendMsg.setOnClickListener(this);

        clientResponseStreamObserver = new StreamObserver<ClientResponse>() {
            @Override
            public void onNext(ClientResponse value) {
                Log.d(TAG, "onNext: " + value.getData());
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: " + t.getMessage());
                ExecutorUtil.execSingle(new ConnectTask());
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }
        };

        connectGrpcServer();
    }

    public void connectGrpcServer() {
        Log.d(TAG, "connectGrpcServer: .............");
        grpcExecutor = GrpcExecutor
                .getInstance()
                .init("192.168.61.185", 50051, clientResponseStreamObserver);
        requestStreamObserver = grpcExecutor.getObserver();
        grpcExecutor.printChannelState();
        if (grpcInterruptConnectCount > MAX_GRPC_CONNECT_COUNT) {
            //todo 连接超过 5 次了，如果当前是认证中的状态，就显示交易失败，流连接失败
            Log.e(TAG, "connectGrpcServer: 连接超过次数！");
            grpcInterruptConnectCount = 0;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_msg:
                sendData(ClientRequest.newBuilder()
                .setData("test date")
                .setType("good")
                .build());
                break;
            default:
                break;
        }
    }

    /**
     * gRPC stream 断开后连接线程
     */
    class ConnectTask implements Runnable {

        @Override
        public void run() {
            connectGrpcServer();
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendData(ClientRequest request) {
        requestStreamObserver.onNext(request);
    }

}
