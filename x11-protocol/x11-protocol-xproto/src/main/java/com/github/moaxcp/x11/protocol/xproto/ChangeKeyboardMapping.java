package com.github.moaxcp.x11.protocol.xproto;

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
public class ChangeKeyboardMapping implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 100;

  private byte keycodeCount;

  private byte firstKeycode;

  private byte keysymsPerKeycode;

  @NonNull
  private IntList keysyms;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeKeyboardMapping readChangeKeyboardMapping(X11Input in) throws IOException {
    ChangeKeyboardMapping.ChangeKeyboardMappingBuilder javaBuilder = ChangeKeyboardMapping.builder();
    byte keycodeCount = in.readCard8();
    short length = in.readCard16();
    byte firstKeycode = in.readCard8();
    byte keysymsPerKeycode = in.readCard8();
    byte[] pad5 = in.readPad(2);
    IntList keysyms = in.readCard32(Byte.toUnsignedInt(keycodeCount) * Byte.toUnsignedInt(keysymsPerKeycode));
    javaBuilder.keycodeCount(keycodeCount);
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.keysymsPerKeycode(keysymsPerKeycode);
    javaBuilder.keysyms(keysyms.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(keycodeCount);
    out.writeCard16((short) getLength());
    out.writeCard8(firstKeycode);
    out.writeCard8(keysymsPerKeycode);
    out.writePad(2);
    out.writeCard32(keysyms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * keysyms.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeKeyboardMappingBuilder {
    public int getSize() {
      return 8 + 4 * keysyms.size();
    }
  }
}
