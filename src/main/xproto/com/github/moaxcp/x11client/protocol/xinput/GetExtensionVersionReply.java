package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetExtensionVersionReply implements XReply, XinputObject {
  private byte xiReplyType;

  private short sequenceNumber;

  private short serverMajor;

  private short serverMinor;

  private boolean present;

  public static GetExtensionVersionReply readGetExtensionVersionReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    GetExtensionVersionReply.GetExtensionVersionReplyBuilder javaBuilder = GetExtensionVersionReply.builder();
    int length = in.readCard32();
    short serverMajor = in.readCard16();
    short serverMinor = in.readCard16();
    boolean present = in.readBool();
    byte[] pad7 = in.readPad(19);
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.serverMajor(serverMajor);
    javaBuilder.serverMinor(serverMinor);
    javaBuilder.present(present);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(serverMajor);
    out.writeCard16(serverMinor);
    out.writeBool(present);
    out.writePad(19);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GetExtensionVersionReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
