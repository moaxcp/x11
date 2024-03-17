package com.github.moaxcp.x11.protocol.randr;

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
public class SetCrtcConfig implements TwoWayRequest<SetCrtcConfigReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 21;

  private int crtc;

  private int timestamp;

  private int configTimestamp;

  private short x;

  private short y;

  private int mode;

  private short rotation;

  @NonNull
  private List<Integer> outputs;

  public XReplyFunction<SetCrtcConfigReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetCrtcConfigReply.readSetCrtcConfigReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCrtcConfig readSetCrtcConfig(X11Input in) throws IOException {
    SetCrtcConfig.SetCrtcConfigBuilder javaBuilder = SetCrtcConfig.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int crtc = in.readCard32();
    javaStart += 4;
    int timestamp = in.readCard32();
    javaStart += 4;
    int configTimestamp = in.readCard32();
    javaStart += 4;
    short x = in.readInt16();
    javaStart += 2;
    short y = in.readInt16();
    javaStart += 2;
    int mode = in.readCard32();
    javaStart += 4;
    short rotation = in.readCard16();
    javaStart += 2;
    byte[] pad10 = in.readPad(2);
    javaStart += 2;
    List<Integer> outputs = in.readCard32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.crtc(crtc);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.mode(mode);
    javaBuilder.rotation(rotation);
    javaBuilder.outputs(outputs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard32(mode);
    out.writeCard16(rotation);
    out.writePad(2);
    out.writeCard32(outputs);
    out.writePadAlign(getSize());
  }

  public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
    for(Rotation m : maskEnums) {
      if(!m.isEnabled(rotation)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28 + 4 * outputs.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetCrtcConfigBuilder {
    public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        if(!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public SetCrtcConfig.SetCrtcConfigBuilder rotationEnable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public SetCrtcConfig.SetCrtcConfigBuilder rotationDisable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public int getSize() {
      return 28 + 4 * outputs.size();
    }
  }
}
