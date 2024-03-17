package com.github.moaxcp.x11.protocol.xvmc;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateSubpicture implements TwoWayRequest<CreateSubpictureReply> {
  public static final String PLUGIN_NAME = "xvmc";

  public static final byte OPCODE = 6;

  private int subpictureId;

  private int context;

  private int xvimageId;

  private short width;

  private short height;

  public XReplyFunction<CreateSubpictureReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateSubpictureReply.readCreateSubpictureReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateSubpicture readCreateSubpicture(X11Input in) throws IOException {
    CreateSubpicture.CreateSubpictureBuilder javaBuilder = CreateSubpicture.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int subpictureId = in.readCard32();
    int context = in.readCard32();
    int xvimageId = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.subpictureId(subpictureId);
    javaBuilder.context(context);
    javaBuilder.xvimageId(xvimageId);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(subpictureId);
    out.writeCard32(context);
    out.writeCard32(xvimageId);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateSubpictureBuilder {
    public int getSize() {
      return 20;
    }
  }
}
