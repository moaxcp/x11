package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FreeLease implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 46;

  private int lid;

  private byte terminate;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeLease readFreeLease(X11Input in) throws IOException {
    FreeLease.FreeLeaseBuilder javaBuilder = FreeLease.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int lid = in.readCard32();
    byte terminate = in.readByte();
    javaBuilder.lid(lid);
    javaBuilder.terminate(terminate);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(lid);
    out.writeByte(terminate);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 9;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FreeLeaseBuilder {
    public int getSize() {
      return 9;
    }
  }
}
