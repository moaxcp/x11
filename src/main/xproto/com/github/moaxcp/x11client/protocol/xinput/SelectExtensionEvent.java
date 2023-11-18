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
public class SelectExtensionEvent implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 6;

  private int window;

  @NonNull
  private List<Integer> classes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectExtensionEvent readSelectExtensionEvent(X11Input in) throws IOException {
    SelectExtensionEvent.SelectExtensionEventBuilder javaBuilder = SelectExtensionEvent.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    short numClasses = in.readCard16();
    byte[] pad5 = in.readPad(2);
    List<Integer> classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.window(window);
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
    out.writePad(2);
    out.writeCard32(classes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * classes.size();
  }

  public static class SelectExtensionEventBuilder {
    public int getSize() {
      return 12 + 4 * classes.size();
    }
  }
}
