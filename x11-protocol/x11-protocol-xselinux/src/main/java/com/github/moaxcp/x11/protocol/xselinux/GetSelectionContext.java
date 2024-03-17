package com.github.moaxcp.x11.protocol.xselinux;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetSelectionContext implements TwoWayRequest<GetSelectionContextReply> {
  public static final String PLUGIN_NAME = "xselinux";

  public static final byte OPCODE = 19;

  private int selection;

  public XReplyFunction<GetSelectionContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetSelectionContextReply.readGetSelectionContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetSelectionContext readGetSelectionContext(X11Input in) throws IOException {
    GetSelectionContext.GetSelectionContextBuilder javaBuilder = GetSelectionContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int selection = in.readCard32();
    javaBuilder.selection(selection);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(selection);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetSelectionContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
