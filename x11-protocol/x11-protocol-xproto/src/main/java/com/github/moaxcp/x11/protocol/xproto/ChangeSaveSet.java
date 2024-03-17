package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChangeSaveSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 6;

  private byte mode;

  private int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeSaveSet readChangeSaveSet(X11Input in) throws IOException {
    ChangeSaveSet.ChangeSaveSetBuilder javaBuilder = ChangeSaveSet.builder();
    byte mode = in.readByte();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.mode(mode);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeByte(mode);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeSaveSetBuilder {
    public ChangeSaveSet.ChangeSaveSetBuilder mode(SetMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
