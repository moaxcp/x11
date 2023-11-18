package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class VendorPrivate implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 16;

  private int vendorCode;

  private int contextTag;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static VendorPrivate readVendorPrivate(X11Input in) throws IOException {
    VendorPrivate.VendorPrivateBuilder javaBuilder = VendorPrivate.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int vendorCode = in.readCard32();
    javaStart += 4;
    int contextTag = in.readCard32();
    javaStart += 4;
    List<Byte> data = in.readByte(javaStart - length);
    javaBuilder.vendorCode(vendorCode);
    javaBuilder.contextTag(contextTag);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class VendorPrivateBuilder {
    public int getSize() {
      return 12 + 1 * data.size();
    }
  }
}
