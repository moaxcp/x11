package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetImage implements TwoWayRequest<GetImageReply> {
  public static final String PLUGIN_NAME = "shm";

  public static final byte OPCODE = 4;

  private int drawable;

  private short x;

  private short y;

  private short width;

  private short height;

  private int planeMask;

  private byte format;

  private int shmseg;

  private int offset;

  public XReplyFunction<GetImageReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetImageReply.readGetImageReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetImage readGetImage(X11Input in) throws IOException {
    GetImage.GetImageBuilder javaBuilder = GetImage.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    int planeMask = in.readCard32();
    byte format = in.readCard8();
    byte[] pad10 = in.readPad(3);
    int shmseg = in.readCard32();
    int offset = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.planeMask(planeMask);
    javaBuilder.format(format);
    javaBuilder.shmseg(shmseg);
    javaBuilder.offset(offset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(planeMask);
    out.writeCard8(format);
    out.writePad(3);
    out.writeCard32(shmseg);
    out.writeCard32(offset);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetImageBuilder {
    public int getSize() {
      return 32;
    }
  }
}
