package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetProviderOutputSource implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 35;

  private int provider;

  private int sourceProvider;

  private int configTimestamp;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetProviderOutputSource readSetProviderOutputSource(X11Input in) throws
      IOException {
    SetProviderOutputSource.SetProviderOutputSourceBuilder javaBuilder = SetProviderOutputSource.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int provider = in.readCard32();
    int sourceProvider = in.readCard32();
    int configTimestamp = in.readCard32();
    javaBuilder.provider(provider);
    javaBuilder.sourceProvider(sourceProvider);
    javaBuilder.configTimestamp(configTimestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(sourceProvider);
    out.writeCard32(configTimestamp);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class SetProviderOutputSourceBuilder {
    public int getSize() {
      return 16;
    }
  }
}
