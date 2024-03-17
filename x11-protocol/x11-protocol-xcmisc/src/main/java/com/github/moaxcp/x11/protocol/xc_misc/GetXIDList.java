package com.github.moaxcp.x11.protocol.xc_misc;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetXIDList implements TwoWayRequest<GetXIDListReply> {
  public static final String PLUGIN_NAME = "xc_misc";

  public static final byte OPCODE = 2;

  private int count;

  public XReplyFunction<GetXIDListReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetXIDListReply.readGetXIDListReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetXIDList readGetXIDList(X11Input in) throws IOException {
    GetXIDList.GetXIDListBuilder javaBuilder = GetXIDList.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int count = in.readCard32();
    javaBuilder.count(count);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(count);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetXIDListBuilder {
    public int getSize() {
      return 8;
    }
  }
}
