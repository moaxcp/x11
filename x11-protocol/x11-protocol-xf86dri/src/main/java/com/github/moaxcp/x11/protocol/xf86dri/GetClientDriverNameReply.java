package com.github.moaxcp.x11.protocol.xf86dri;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetClientDriverNameReply implements XReply {
  public static final String PLUGIN_NAME = "xf86dri";

  private short sequenceNumber;

  private int clientDriverMajorVersion;

  private int clientDriverMinorVersion;

  private int clientDriverPatchVersion;

  @NonNull
  private List<Byte> clientDriverName;

  public static GetClientDriverNameReply readGetClientDriverNameReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetClientDriverNameReply.GetClientDriverNameReplyBuilder javaBuilder = GetClientDriverNameReply.builder();
    int length = in.readCard32();
    int clientDriverMajorVersion = in.readCard32();
    int clientDriverMinorVersion = in.readCard32();
    int clientDriverPatchVersion = in.readCard32();
    int clientDriverNameLen = in.readCard32();
    byte[] pad8 = in.readPad(8);
    List<Byte> clientDriverName = in.readChar((int) (Integer.toUnsignedLong(clientDriverNameLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.clientDriverMajorVersion(clientDriverMajorVersion);
    javaBuilder.clientDriverMinorVersion(clientDriverMinorVersion);
    javaBuilder.clientDriverPatchVersion(clientDriverPatchVersion);
    javaBuilder.clientDriverName(clientDriverName);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(clientDriverMajorVersion);
    out.writeCard32(clientDriverMinorVersion);
    out.writeCard32(clientDriverPatchVersion);
    int clientDriverNameLen = clientDriverName.size();
    out.writeCard32(clientDriverNameLen);
    out.writePad(8);
    out.writeChar(clientDriverName);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * clientDriverName.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetClientDriverNameReplyBuilder {
    public int getSize() {
      return 32 + 1 * clientDriverName.size();
    }
  }
}
