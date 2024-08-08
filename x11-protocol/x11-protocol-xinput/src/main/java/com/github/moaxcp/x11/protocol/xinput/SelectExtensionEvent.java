package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class SelectExtensionEvent implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 6;

  private int window;

  @NonNull
  private IntList classes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectExtensionEvent readSelectExtensionEvent(X11Input in) throws IOException {
    SelectExtensionEvent.SelectExtensionEventBuilder javaBuilder = SelectExtensionEvent.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    short numClasses = in.readCard16();
    byte[] pad5 = in.readPad(2);
    IntList classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.window(window);
    javaBuilder.classes(classes.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SelectExtensionEventBuilder {
    public int getSize() {
      return 12 + 4 * classes.size();
    }
  }
}
