package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ConvertSelection implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 24;

  private int requestor;

  private int selection;

  private int target;

  private int property;

  private int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ConvertSelection readConvertSelection(X11Input in) throws IOException {
    ConvertSelection.ConvertSelectionBuilder javaBuilder = ConvertSelection.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int requestor = in.readCard32();
    int selection = in.readCard32();
    int target = in.readCard32();
    int property = in.readCard32();
    int time = in.readCard32();
    javaBuilder.requestor(requestor);
    javaBuilder.selection(selection);
    javaBuilder.target(target);
    javaBuilder.property(property);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(requestor);
    out.writeCard32(selection);
    out.writeCard32(target);
    out.writeCard32(property);
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class ConvertSelectionBuilder {
    public int getSize() {
      return 24;
    }
  }
}
