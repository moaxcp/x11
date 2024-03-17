package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetSelectionOwner implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 22;

  private int owner;

  private int selection;

  private int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetSelectionOwner readSetSelectionOwner(X11Input in) throws IOException {
    SetSelectionOwner.SetSelectionOwnerBuilder javaBuilder = SetSelectionOwner.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int owner = in.readCard32();
    int selection = in.readCard32();
    int time = in.readCard32();
    javaBuilder.owner(owner);
    javaBuilder.selection(selection);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(owner);
    out.writeCard32(selection);
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetSelectionOwnerBuilder {
    public int getSize() {
      return 16;
    }
  }
}
