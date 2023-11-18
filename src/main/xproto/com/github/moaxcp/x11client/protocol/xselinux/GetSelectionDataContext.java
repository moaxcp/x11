package com.github.moaxcp.x11client.protocol.xselinux;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetSelectionDataContext implements TwoWayRequest<GetSelectionDataContextReply>, XselinuxObject {
  public static final byte OPCODE = 20;

  private int selection;

  public XReplyFunction<GetSelectionDataContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetSelectionDataContextReply.readGetSelectionDataContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetSelectionDataContext readGetSelectionDataContext(X11Input in) throws
      IOException {
    GetSelectionDataContext.GetSelectionDataContextBuilder javaBuilder = GetSelectionDataContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int selection = in.readCard32();
    javaBuilder.selection(selection);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(selection);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetSelectionDataContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
