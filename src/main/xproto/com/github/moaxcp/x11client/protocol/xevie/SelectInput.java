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
public class SelectInput implements TwoWayRequest<SelectInputReply>, XevieObject {
  public static final byte OPCODE = 4;

  private int eventMask;

  public XReplyFunction<SelectInputReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SelectInputReply.readSelectInputReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectInput readSelectInput(X11Input in) throws IOException {
    SelectInput.SelectInputBuilder javaBuilder = SelectInput.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int eventMask = in.readCard32();
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(eventMask);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SelectInputBuilder {
    public int getSize() {
      return 8;
    }
  }
}
