package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GenQueriesARB implements TwoWayRequest<GenQueriesARBReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = (byte) 162;

  private int contextTag;

  private int n;

  public XReplyFunction<GenQueriesARBReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GenQueriesARBReply.readGenQueriesARBReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GenQueriesARB readGenQueriesARB(X11Input in) throws IOException {
    GenQueriesARB.GenQueriesARBBuilder javaBuilder = GenQueriesARB.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int n = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.n(n);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(n);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GenQueriesARBBuilder {
    public int getSize() {
      return 12;
    }
  }
}
