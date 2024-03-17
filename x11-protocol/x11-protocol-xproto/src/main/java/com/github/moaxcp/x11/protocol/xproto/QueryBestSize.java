package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryBestSize implements TwoWayRequest<QueryBestSizeReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 97;

  private byte clazz;

  private int drawable;

  private short width;

  private short height;

  public XReplyFunction<QueryBestSizeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryBestSizeReply.readQueryBestSizeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryBestSize readQueryBestSize(X11Input in) throws IOException {
    QueryBestSize.QueryBestSizeBuilder javaBuilder = QueryBestSize.builder();
    byte clazz = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.clazz(clazz);
    javaBuilder.drawable(drawable);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(clazz);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryBestSizeBuilder {
    public QueryBestSize.QueryBestSizeBuilder clazz(QueryShapeOf clazz) {
      this.clazz = (byte) clazz.getValue();
      return this;
    }

    public QueryBestSize.QueryBestSizeBuilder clazz(byte clazz) {
      this.clazz = clazz;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
