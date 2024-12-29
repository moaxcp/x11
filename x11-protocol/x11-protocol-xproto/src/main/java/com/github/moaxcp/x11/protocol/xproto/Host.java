package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class Host implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private byte family;

  @NonNull
  private ByteList address;

  public static Host readHost(X11Input in) throws IOException {
    Host.HostBuilder javaBuilder = Host.builder();
    byte family = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short addressLen = in.readCard16();
    ByteList address = in.readByte(Short.toUnsignedInt(addressLen));
    in.readPadAlign(Short.toUnsignedInt(addressLen));
    javaBuilder.family(family);
    javaBuilder.address(address.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
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
