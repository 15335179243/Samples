// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: facePass.proto

package com.kk.grpc.bean;

/**
 * Protobuf type {@code facePass.ClientRequest}
 */
public  final class ClientRequest extends
    com.google.protobuf.GeneratedMessageLite<
        ClientRequest, ClientRequest.Builder> implements
    // @@protoc_insertion_point(message_implements:facePass.ClientRequest)
    ClientRequestOrBuilder {
  private ClientRequest() {
    data_ = "";
    type_ = "";
  }
  public static final int DATA_FIELD_NUMBER = 1;
  private String data_;
  /**
   * <code>optional string data = 1;</code>
   */
  public String getData() {
    return data_;
  }
  /**
   * <code>optional string data = 1;</code>
   */
  public com.google.protobuf.ByteString
      getDataBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(data_);
  }
  /**
   * <code>optional string data = 1;</code>
   */
  private void setData(
      String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    data_ = value;
  }
  /**
   * <code>optional string data = 1;</code>
   */
  private void clearData() {
    
    data_ = getDefaultInstance().getData();
  }
  /**
   * <code>optional string data = 1;</code>
   */
  private void setDataBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    data_ = value.toStringUtf8();
  }

  public static final int TYPE_FIELD_NUMBER = 2;
  private String type_;
  /**
   * <code>optional string type = 2;</code>
   */
  public String getType() {
    return type_;
  }
  /**
   * <code>optional string type = 2;</code>
   */
  public com.google.protobuf.ByteString
      getTypeBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(type_);
  }
  /**
   * <code>optional string type = 2;</code>
   */
  private void setType(
      String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    type_ = value;
  }
  /**
   * <code>optional string type = 2;</code>
   */
  private void clearType() {
    
    type_ = getDefaultInstance().getType();
  }
  /**
   * <code>optional string type = 2;</code>
   */
  private void setTypeBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    type_ = value.toStringUtf8();
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!data_.isEmpty()) {
      output.writeString(1, getData());
    }
    if (!type_.isEmpty()) {
      output.writeString(2, getType());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (!data_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(1, getData());
    }
    if (!type_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(2, getType());
    }
    memoizedSerializedSize = size;
    return size;
  }

  public static ClientRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static ClientRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static ClientRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static ClientRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static ClientRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static ClientRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static ClientRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static ClientRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static ClientRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static ClientRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(ClientRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * Protobuf type {@code facePass.ClientRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        ClientRequest, Builder> implements
      // @@protoc_insertion_point(builder_implements:facePass.ClientRequest)
      com.kk.grpc.bean.ClientRequestOrBuilder {
    // Construct using com.kk.grpc.bean.ClientRequest.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>optional string data = 1;</code>
     */
    public String getData() {
      return instance.getData();
    }
    /**
     * <code>optional string data = 1;</code>
     */
    public com.google.protobuf.ByteString
        getDataBytes() {
      return instance.getDataBytes();
    }
    /**
     * <code>optional string data = 1;</code>
     */
    public Builder setData(
        String value) {
      copyOnWrite();
      instance.setData(value);
      return this;
    }
    /**
     * <code>optional string data = 1;</code>
     */
    public Builder clearData() {
      copyOnWrite();
      instance.clearData();
      return this;
    }
    /**
     * <code>optional string data = 1;</code>
     */
    public Builder setDataBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setDataBytes(value);
      return this;
    }

    /**
     * <code>optional string type = 2;</code>
     */
    public String getType() {
      return instance.getType();
    }
    /**
     * <code>optional string type = 2;</code>
     */
    public com.google.protobuf.ByteString
        getTypeBytes() {
      return instance.getTypeBytes();
    }
    /**
     * <code>optional string type = 2;</code>
     */
    public Builder setType(
        String value) {
      copyOnWrite();
      instance.setType(value);
      return this;
    }
    /**
     * <code>optional string type = 2;</code>
     */
    public Builder clearType() {
      copyOnWrite();
      instance.clearType();
      return this;
    }
    /**
     * <code>optional string type = 2;</code>
     */
    public Builder setTypeBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setTypeBytes(value);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:facePass.ClientRequest)
  }
  protected final Object dynamicMethod(
      MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new ClientRequest();
      }
      case IS_INITIALIZED: {
        return DEFAULT_INSTANCE;
      }
      case MAKE_IMMUTABLE: {
        return null;
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case VISIT: {
        Visitor visitor = (Visitor) arg0;
        ClientRequest other = (ClientRequest) arg1;
        data_ = visitor.visitString(!data_.isEmpty(), data_,
            !other.data_.isEmpty(), other.data_);
        type_ = visitor.visitString(!type_.isEmpty(), type_,
            !other.type_.isEmpty(), other.type_);
        if (visitor == MergeFromVisitor
            .INSTANCE) {
        }
        return this;
      }
      case MERGE_FROM_STREAM: {
        com.google.protobuf.CodedInputStream input =
            (com.google.protobuf.CodedInputStream) arg0;
        com.google.protobuf.ExtensionRegistryLite extensionRegistry =
            (com.google.protobuf.ExtensionRegistryLite) arg1;
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              default: {
                if (!input.skipField(tag)) {
                  done = true;
                }
                break;
              }
              case 10: {
                String s = input.readStringRequireUtf8();

                data_ = s;
                break;
              }
              case 18: {
                String s = input.readStringRequireUtf8();

                type_ = s;
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw new RuntimeException(e.setUnfinishedMessage(this));
        } catch (java.io.IOException e) {
          throw new RuntimeException(
              new com.google.protobuf.InvalidProtocolBufferException(
                  e.getMessage()).setUnfinishedMessage(this));
        } finally {
        }
      }
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        if (PARSER == null) {    synchronized (ClientRequest.class) {
            if (PARSER == null) {
              PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
            }
          }
        }
        return PARSER;
      }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:facePass.ClientRequest)
  private static final ClientRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ClientRequest();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static ClientRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<ClientRequest> PARSER;

  public static com.google.protobuf.Parser<ClientRequest> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
