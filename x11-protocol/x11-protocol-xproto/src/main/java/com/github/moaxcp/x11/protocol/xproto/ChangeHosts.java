package com.github.moaxcp.x11.protocol.xproto;

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
public class ChangeHosts implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 109;

  private byte mode;

  private byte family;

  @NonNull
  private List<Byte> address;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeHosts readChangeHosts(X11Input in) throws IOException {
    ChangeHosts.ChangeHostsBuilder javaBuilder = ChangeHosts.builder();
    byte mode = in.readCard8();
    short length = in.readCard16();
    byte family = in.readCard8();
    byte[] pad4 = in.readPad(1);
    short addressLen = in.readCard16();
    List<Byte> address = in.readByte(Short.toUnsignedInt(addressLen));
    javaBuilder.mode(mode);
    javaBuilder.family(family);
    javaBuilder.address(address);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(mode);
    out.writeCard16((short) getLength());
    out.writeCard8(family);
    out.writePad(1);
    short addressLen = (short) address.size();
    out.writeCard16(addressLen);
    out.writeByte(address);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * address.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeHostsBuilder {
    public ChangeHosts.ChangeHostsBuilder mode(HostMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeHosts.ChangeHostsBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public ChangeHosts.ChangeHostsBuilder family(Family family) {
      this.family = (byte) family.getValue();
      return this;
    }

    public ChangeHosts.ChangeHostsBuilder family(byte family) {
      this.family = family;
      return this;
    }

    public int getSize() {
      return 8 + 1 * address.size();
    }
  }
}
