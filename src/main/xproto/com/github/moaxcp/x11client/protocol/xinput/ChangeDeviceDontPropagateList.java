package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeDeviceDontPropagateList implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 8;

  private int window;

  private byte mode;

  @NonNull
  private List<Integer> classes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeDeviceDontPropagateList readChangeDeviceDontPropagateList(X11Input in) throws
      IOException {
    ChangeDeviceDontPropagateList.ChangeDeviceDontPropagateListBuilder javaBuilder = ChangeDeviceDontPropagateList.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    short numClasses = in.readCard16();
    byte mode = in.readCard8();
    byte[] pad6 = in.readPad(1);
    List<Integer> classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.window(window);
    javaBuilder.mode(mode);
    javaBuilder.classes(classes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    short numClasses = (short) classes.size();
    out.writeCard16(numClasses);
    out.writeCard8(mode);
    out.writePad(1);
    out.writeCard32(classes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * classes.size();
  }

  public static class ChangeDeviceDontPropagateListBuilder {
    public ChangeDeviceDontPropagateList.ChangeDeviceDontPropagateListBuilder mode(
        PropagateMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeDeviceDontPropagateList.ChangeDeviceDontPropagateListBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 12 + 4 * classes.size();
    }
  }
}
