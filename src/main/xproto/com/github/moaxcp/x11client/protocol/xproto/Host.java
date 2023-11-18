package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Host implements XStruct, XprotoObject {
  private byte family;

  @NonNull
  private List<Byte> address;

  public static Host readHost(X11Input in) throws IOException {
    Host.HostBuilder javaBuilder = Host.builder();
    byte family = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short addressLen = in.readCard16();
    List<Byte> address = in.readByte(Short.toUnsignedInt(addressLen));
    in.readPadAlign(Short.toUnsignedInt(addressLen));
    javaBuilder.family(family);
    javaBuilder.address(address);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(family);
    out.writePad(1);
    short addressLen = (short) address.size();
    out.writeCard16(addressLen);
    out.writeByte(address);
    out.writePadAlign(Short.toUnsignedInt(addressLen));
  }

  @Override
  public int getSize() {
    return 4 + 1 * address.size() + XObject.getSizeForPadAlign(4, 1 * address.size());
  }

  public static class HostBuilder {
    public Host.HostBuilder family(Family family) {
      this.family = (byte) family.getValue();
      return this;
    }

    public Host.HostBuilder family(byte family) {
      this.family = family;
      return this;
    }

    public int getSize() {
      return 4 + 1 * address.size() + XObject.getSizeForPadAlign(4, 1 * address.size());
    }
  }
}
