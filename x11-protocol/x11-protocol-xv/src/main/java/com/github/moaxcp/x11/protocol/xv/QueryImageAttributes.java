package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryImageAttributes implements TwoWayRequest<QueryImageAttributesReply> {
  public static final String PLUGIN_NAME = "xv";

  public static final byte OPCODE = 17;

  private int port;

  private int id;

  private short width;

  private short height;

  public XReplyFunction<QueryImageAttributesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryImageAttributesReply.readQueryImageAttributesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryImageAttributes readQueryImageAttributes(X11Input in) throws IOException {
    QueryImageAttributes.QueryImageAttributesBuilder javaBuilder = QueryImageAttributes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int port = in.readCard32();
    int id = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.port(port);
    javaBuilder.id(id);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard32(id);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryImageAttributesBuilder {
    public int getSize() {
      return 16;
    }
  }
}
