package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class VendorPrivate implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 16;

  private int vendorCode;

  private int contextTag;

  @NonNull
  private ByteList data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static VendorPrivate readVendorPrivate(X11Input in) throws IOException {
    VendorPrivate.VendorPrivateBuilder javaBuilder = VendorPrivate.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int vendorCode = in.readCard32();
    javaStart += 4;
    int contextTag = in.readCard32();
    javaStart += 4;
    ByteList data = in.readByte(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.vendorCode(vendorCode);
    javaBuilder.contextTag(contextTag);
    javaBuilder.data(data.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(vendorCode);
    out.writeCard32(contextTag);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class VendorPrivateBuilder {
    public int getSize() {
      return 12 + 1 * data.size();
    }
  }
}
