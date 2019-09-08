package com.kk.grpc;

import android.util.Log;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcExecutor {
    private static final String TAG = "GrpcExecutor";
    public static GrpcExecutor instance;
    private ManagedChannel channel;
    private FacePassServiceGrpc.FacePassServiceStub stub;
    private StreamObserver<ClientResponse> clientResponseStreamObserver;

    private GrpcExecutor() {
    }

    public static GrpcExecutor getInstance() {
        if (instance == null) {
            instance = new GrpcExecutor();
        }
        return instance;
    }

    public GrpcExecutor init(String ipAddress, int portNo, StreamObserver<ClientResponse> requestStreamObserver) {
        channel = ManagedChannelBuilder
                .forAddress(ipAddress, portNo)
                .usePlaintext()
                .build();

        printChannelState();
        this.clientResponseStreamObserver = requestStreamObserver;
        stub = FacePassServiceGrpc.newStub(channel);


        channel.notifyWhenStateChanged(channel.getState(true), new Runnable() {
            @Override
            public void run() {
//                printChannelState();
                Log.d(TAG, "run: -------------监听管道状态变化：" + channel.getState(true).name());
            }
        });
        return this;
    }

    public StreamObserver<ClientRequest> getObserver() {
        return stub.clienTrade(clientResponseStreamObserver);
    }


    public void stopConn() {
        if (channel != null) {
            channel.shutdown();
        }
    }

    public void printChannelState() {
        ConnectivityState state = channel.getState(true);
        switch (state) {
            case IDLE:
                Log.d(TAG, "init: ---------IDLE");
                break;
            case CONNECTING:
                Log.d(TAG, "init: ---------CONNECTING");
                break;
            case READY:
                Log.d(TAG, "init: ---------READY");
                break;
            case TRANSIENT_FAILURE:
                Log.d(TAG, "init: ---------TRANSIENT_FAILURE");
                break;
            case SHUTDOWN:
                Log.d(TAG, "init: ---------SHUTDOWN");
                break;
            default:
                break;
        }
    }


}
