package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIGetClientPointer implements TwoWayRequest<XIGetClientPointerReply>, XinputObject {
  public static final byte OPCODE = 45;

  private int window;

  public XReplyFunction<XIGetClientPointerReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIGetClientPointerReply.readXIGetClientPointerReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIGetClientPointer readXIGetClientPointer(X11Input in) throws IOException {
    XIGetClientPointer.XIGetClientPointerBuilder javaBuilder = XIGetClientPointer.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class XIGetClientPointerBuilder {
    public int getSize() {
      return 8;
    }
  }
}
