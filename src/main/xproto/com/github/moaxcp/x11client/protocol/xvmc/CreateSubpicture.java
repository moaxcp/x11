package com.github.moaxcp.x11client.protocol.xvmc;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateSubpicture implements TwoWayRequest<CreateSubpictureReply>, XvmcObject {
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
    byte[] pad1 = in.readPad(1);
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
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class CreateSubpictureBuilder {
    public int getSize() {
      return 20;
    }
  }
}
