package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UseExtension implements TwoWayRequest<UseExtensionReply> {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 0;

  private short wantedMajor;

  private short wantedMinor;

  public XReplyFunction<UseExtensionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> UseExtensionReply.readUseExtensionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static UseExtension readUseExtension(X11Input in) throws IOException {
    UseExtension.UseExtensionBuilder javaBuilder = UseExtension.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short wantedMajor = in.readCard16();
    short wantedMinor = in.readCard16();
    javaBuilder.wantedMajor(wantedMajor);
    javaBuilder.wantedMinor(wantedMinor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(wantedMajor);
    out.writeCard16(wantedMinor);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class UseExtensionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
