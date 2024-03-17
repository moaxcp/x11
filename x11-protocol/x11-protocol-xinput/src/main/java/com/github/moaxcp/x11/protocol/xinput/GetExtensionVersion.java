package com.github.moaxcp.x11.protocol.xinput;

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
public class GetExtensionVersion implements TwoWayRequest<GetExtensionVersionReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 1;

  @NonNull
  private List<Byte> name;

  public XReplyFunction<GetExtensionVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetExtensionVersionReply.readGetExtensionVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetExtensionVersion readGetExtensionVersion(X11Input in) throws IOException {
    GetExtensionVersion.GetExtensionVersionBuilder javaBuilder = GetExtensionVersion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short nameLen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetExtensionVersionBuilder {
    public int getSize() {
      return 8 + 1 * name.size();
    }
  }
}
