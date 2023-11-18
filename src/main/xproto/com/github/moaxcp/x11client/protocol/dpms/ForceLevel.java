package com.github.moaxcp.x11client.protocol.dpms;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ForceLevel implements OneWayRequest, DpmsObject {
  public static final byte OPCODE = 6;

  private short powerLevel;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ForceLevel readForceLevel(X11Input in) throws IOException {
    ForceLevel.ForceLevelBuilder javaBuilder = ForceLevel.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short powerLevel = in.readCard16();
    javaBuilder.powerLevel(powerLevel);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(powerLevel);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 6;
  }

  public static class ForceLevelBuilder {
    public ForceLevel.ForceLevelBuilder powerLevel(DPMSMode powerLevel) {
      this.powerLevel = (short) powerLevel.getValue();
      return this;
    }

    public ForceLevel.ForceLevelBuilder powerLevel(short powerLevel) {
      this.powerLevel = powerLevel;
      return this;
    }

    public int getSize() {
      return 6;
    }
  }
}
