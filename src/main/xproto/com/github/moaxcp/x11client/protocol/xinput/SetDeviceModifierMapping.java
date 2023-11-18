package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetDeviceModifierMapping implements TwoWayRequest<SetDeviceModifierMappingReply>, XinputObject {
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
    byte deviceId = in.readCard8();
    short length = in.readCard16();
    byte keycodesPerModifier = in.readCard8();
    byte[] pad4 = in.readPad(2);
    List<Byte> keymaps = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.deviceId(deviceId);
    javaBuilder.keymaps(keymaps);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(deviceId);
    out.writeCard16((short) getLength());
    byte keycodesPerModifier = (byte) keymaps.size();
    out.writeCard8(keycodesPerModifier);
    out.writePad(2);
    out.writeCard8(keymaps);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7 + 1 * keymaps.size();
  }

  public static class SetDeviceModifierMappingBuilder {
    public int getSize() {
      return 7 + 1 * keymaps.size();
    }
  }
}
