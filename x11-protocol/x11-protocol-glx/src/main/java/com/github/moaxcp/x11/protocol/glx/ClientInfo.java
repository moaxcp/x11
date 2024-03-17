package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ClientInfo implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 20;

  private int majorVersion;

  private int minorVersion;

  @NonNull
  private List<Byte> string;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ClientInfo readClientInfo(X11Input in) throws IOException {
    ClientInfo.ClientInfoBuilder javaBuilder = ClientInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int majorVersion = in.readCard32();
    int minorVersion = in.readCard32();
    int strLen = in.readCard32();
    List<Byte> string = in.readChar((int) (Integer.toUnsignedLong(strLen)));
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    javaBuilder.string(string);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(majorVersion);
    out.writeCard32(minorVersion);
    int strLen = string.size();
    out.writeCard32(strLen);
    out.writeChar(string);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * string.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ClientInfoBuilder {
    public int getSize() {
      return 16 + 1 * string.size();
    }
  }
}
