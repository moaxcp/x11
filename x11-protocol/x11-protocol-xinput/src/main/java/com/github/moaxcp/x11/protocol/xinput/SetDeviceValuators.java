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
public class SetDeviceValuators implements TwoWayRequest<SetDeviceValuatorsReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 33;

  private byte deviceId;

  private byte firstValuator;

  @NonNull
  private List<Integer> valuators;

  public XReplyFunction<SetDeviceValuatorsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetDeviceValuatorsReply.readSetDeviceValuatorsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceValuators readSetDeviceValuators(X11Input in) throws IOException {
    SetDeviceValuators.SetDeviceValuatorsBuilder javaBuilder = SetDeviceValuators.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte deviceId = in.readCard8();
    byte firstValuator = in.readCard8();
    byte numValuators = in.readCard8();
    byte[] pad6 = in.readPad(1);
    List<Integer> valuators = in.readInt32(Byte.toUnsignedInt(numValuators));
    javaBuilder.deviceId(deviceId);
    javaBuilder.firstValuator(firstValuator);
    javaBuilder.valuators(valuators);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(deviceId);
    out.writeCard8(firstValuator);
    byte numValuators = (byte) valuators.size();
    out.writeCard8(numValuators);
    out.writePad(1);
    out.writeInt32(valuators);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * valuators.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetDeviceValuatorsBuilder {
    public int getSize() {
      return 8 + 4 * valuators.size();
    }
  }
}
