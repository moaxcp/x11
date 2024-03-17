package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FetchRegion implements TwoWayRequest<FetchRegionReply> {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 19;

  private int region;

  public XReplyFunction<FetchRegionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> FetchRegionReply.readFetchRegionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static FetchRegion readFetchRegion(X11Input in) throws IOException {
    FetchRegion.FetchRegionBuilder javaBuilder = FetchRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    javaBuilder.region(region);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FetchRegionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
