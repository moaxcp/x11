package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetScreenConfig implements TwoWayRequest<SetScreenConfigReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 2;

  private int window;

  private int timestamp;

  private int configTimestamp;

  private short sizeID;

  private short rotation;

  private short rate;

  public XReplyFunction<SetScreenConfigReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetScreenConfigReply.readSetScreenConfigReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetScreenConfig readSetScreenConfig(X11Input in) throws IOException {
    SetScreenConfig.SetScreenConfigBuilder javaBuilder = SetScreenConfig.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    short sizeID = in.readCard16();
    short rotation = in.readCard16();
    short rate = in.readCard16();
    byte[] pad9 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.sizeID(sizeID);
    javaBuilder.rotation(rotation);
    javaBuilder.rate(rate);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    out.writeCard16(sizeID);
    out.writeCard16(rotation);
    out.writeCard16(rate);
    out.writePad(2);
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
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetScreenConfigBuilder {
    public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        if(!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public SetScreenConfig.SetScreenConfigBuilder rotationEnable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public SetScreenConfig.SetScreenConfigBuilder rotationDisable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public int getSize() {
      return 24;
    }
  }
}
