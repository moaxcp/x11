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
public class SetClientInfoARB implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 33;

  private int majorVersion;

  private int minorVersion;

  @NonNull
  private List<Integer> glVersions;

  @NonNull
  private List<Byte> glExtensionString;

  @NonNull
  private List<Byte> glxExtensionString;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetClientInfoARB readSetClientInfoARB(X11Input in) throws IOException {
    SetClientInfoARB.SetClientInfoARBBuilder javaBuilder = SetClientInfoARB.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int majorVersion = in.readCard32();
    int minorVersion = in.readCard32();
    int numVersions = in.readCard32();
    int glStrLen = in.readCard32();
    int glxStrLen = in.readCard32();
    List<Integer> glVersions = in.readCard32((int) (Integer.toUnsignedLong(numVersions) * 2));
    List<Byte> glExtensionString = in.readChar((int) (Integer.toUnsignedLong(glStrLen)));
    List<Byte> glxExtensionString = in.readChar((int) (Integer.toUnsignedLong(glxStrLen)));
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    javaBuilder.glVersions(glVersions);
    javaBuilder.glExtensionString(glExtensionString);
    javaBuilder.glxExtensionString(glxExtensionString);
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
    int numVersions = glVersions.size();
    out.writeCard32(numVersions);
    int glStrLen = glExtensionString.size();
    out.writeCard32(glStrLen);
    int glxStrLen = glxExtensionString.size();
    out.writeCard32(glxStrLen);
    out.writeCard32(glVersions);
    out.writeChar(glExtensionString);
    out.writeChar(glxExtensionString);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 4 * glVersions.size() + 1 * glExtensionString.size() + 1 * glxExtensionString.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetClientInfoARBBuilder {
    public int getSize() {
      return 24 + 4 * glVersions.size() + 1 * glExtensionString.size() + 1 * glxExtensionString.size();
    }
  }
}
