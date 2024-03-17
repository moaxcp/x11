package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AllocColor implements TwoWayRequest<AllocColorReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 84;

  private int cmap;

  private short red;

  private short green;

  private short blue;

  public XReplyFunction<AllocColorReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> AllocColorReply.readAllocColorReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllocColor readAllocColor(X11Input in) throws IOException {
    AllocColor.AllocColorBuilder javaBuilder = AllocColor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cmap = in.readCard32();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    byte[] pad7 = in.readPad(2);
    javaBuilder.cmap(cmap);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AllocColorBuilder {
    public int getSize() {
      return 16;
    }
  }
}
