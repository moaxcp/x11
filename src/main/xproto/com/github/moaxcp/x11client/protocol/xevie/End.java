package com.github.moaxcp.x11client.protocol.xevie;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class End implements TwoWayRequest<EndReply>, XevieObject {
  public static final byte OPCODE = 2;

  private int cmap;

  public XReplyFunction<EndReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> EndReply.readEndReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static End readEnd(X11Input in) throws IOException {
    End.EndBuilder javaBuilder = End.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cmap = in.readCard32();
    javaBuilder.cmap(cmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class EndBuilder {
    public int getSize() {
      return 8;
    }
  }
}
