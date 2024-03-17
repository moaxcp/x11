package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChangeSaveSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 1;

  private byte mode;

  private byte target;

  private byte map;

  private int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeSaveSet readChangeSaveSet(X11Input in) throws IOException {
    ChangeSaveSet.ChangeSaveSetBuilder javaBuilder = ChangeSaveSet.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte mode = in.readByte();
    byte target = in.readByte();
    byte map = in.readByte();
    byte[] pad6 = in.readPad(1);
    int window = in.readCard32();
    javaBuilder.mode(mode);
    javaBuilder.target(target);
    javaBuilder.map(map);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeByte(mode);
    out.writeByte(target);
    out.writeByte(map);
    out.writePad(1);
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeSaveSetBuilder {
    public ChangeSaveSet.ChangeSaveSetBuilder mode(SaveSetMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder target(SaveSetTarget target) {
      this.target = (byte) target.getValue();
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder target(byte target) {
      this.target = target;
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder map(SaveSetMapping map) {
      this.map = (byte) map.getValue();
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder map(byte map) {
      this.map = map;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
