package com.kk.grpc.bean;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.21.0)",
    comments = "Source: facePass.proto")
public final class FacePassServiceGrpc {

  private FacePassServiceGrpc() {}

  public static final String SERVICE_NAME = "facePass.FacePassService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.kk.grpc.bean.ClientRequest,
      com.kk.grpc.bean.ClientResponse> getClienTradeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "clienTrade",
      requestType = com.kk.grpc.bean.ClientRequest.class,
      responseType = com.kk.grpc.bean.ClientResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.kk.grpc.bean.ClientRequest,
      com.kk.grpc.bean.ClientResponse> getClienTradeMethod() {
    io.grpc.MethodDescriptor<com.kk.grpc.bean.ClientRequest, com.kk.grpc.bean.ClientResponse> getClienTradeMethod;
    if ((getClienTradeMethod = FacePassServiceGrpc.getClienTradeMethod) == null) {
      synchronized (FacePassServiceGrpc.class) {
        if ((getClienTradeMethod = FacePassServiceGrpc.getClienTradeMethod) == null) {
          FacePassServiceGrpc.getClienTradeMethod = getClienTradeMethod = 
              io.grpc.MethodDescriptor.<com.kk.grpc.bean.ClientRequest, com.kk.grpc.bean.ClientResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "facePass.FacePassService", "clienTrade"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.kk.grpc.bean.ClientRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.kk.grpc.bean.ClientResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getClienTradeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FacePassServiceStub newStub(io.grpc.Channel channel) {
    return new FacePassServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FacePassServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FacePassServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FacePassServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FacePassServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class FacePassServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.kk.grpc.bean.ClientRequest> clienTrade(
        io.grpc.stub.StreamObserver<com.kk.grpc.bean.ClientResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getClienTradeMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getClienTradeMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.kk.grpc.bean.ClientRequest,
                com.kk.grpc.bean.ClientResponse>(
                  this, METHODID_CLIEN_TRADE)))
          .build();
    }
  }

  /**
   */
  public static final class FacePassServiceStub extends io.grpc.stub.AbstractStub<FacePassServiceStub> {
    private FacePassServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FacePassServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected FacePassServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FacePassServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.kk.grpc.bean.ClientRequest> clienTrade(
        io.grpc.stub.StreamObserver<com.kk.grpc.bean.ClientResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getClienTradeMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class FacePassServiceBlockingStub extends io.grpc.stub.AbstractStub<FacePassServiceBlockingStub> {
    private FacePassServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FacePassServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected FacePassServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FacePassServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class FacePassServiceFutureStub extends io.grpc.stub.AbstractStub<FacePassServiceFutureStub> {
    private FacePassServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FacePassServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected FacePassServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FacePassServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_CLIEN_TRADE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FacePassServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FacePassServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CLIEN_TRADE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.clienTrade(
              (io.grpc.stub.StreamObserver<com.kk.grpc.bean.ClientResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FacePassServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getClienTradeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
