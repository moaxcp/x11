package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetDeviceModifierMapping implements TwoWayRequest<SetDeviceModifierMappingReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 27;

  private byte deviceId;

  @NonNull
  private List<Byte> keymaps;

  public XReplyFunction<SetDeviceModifierMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetDeviceModifierMappingReply.readSetDeviceModifierMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceModifierMapping readSetDeviceModifierMapping(X11Input in) throws
      IOException {
    SetDeviceModifierMapping.SetDeviceModifierMappingBuilder javaBuilder = SetDeviceModifierMapping.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte deviceId = in.readCard8();
    byte keycodesPerModifier = in.readCard8();
    byte[] pad5 = in.readPad(2);
    List<Byte> keymaps = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.deviceId(deviceId);
    javaBuilder.keymaps(keymaps);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(deviceId);
    byte keycodesPerModifier = (byte) keymaps.size();
    out.writeCard8(keycodesPerModifier);
    out.writePad(2);
    out.writeCard8(keymaps);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * keymaps.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetDeviceModifierMappingBuilder {
    public int getSize() {
      return 8 + 1 * keymaps.size();
    }
  }
}
