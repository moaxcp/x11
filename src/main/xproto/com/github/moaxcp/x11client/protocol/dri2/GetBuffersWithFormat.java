package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetBuffersWithFormat implements TwoWayRequest<GetBuffersWithFormatReply>, Dri2Object {
  public static final byte OPCODE = 7;

  private int drawable;

  private int count;

  @NonNull
  private List<AttachFormat> attachments;

  public XReplyFunction<GetBuffersWithFormatReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetBuffersWithFormatReply.readGetBuffersWithFormatReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetBuffersWithFormat readGetBuffersWithFormat(X11Input in) throws IOException {
    GetBuffersWithFormat.GetBuffersWithFormatBuilder javaBuilder = GetBuffersWithFormat.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int count = in.readCard32();
    javaStart += 4;
    List<AttachFormat> attachments = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      AttachFormat baseObject = AttachFormat.readAttachFormat(in);
      attachments.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.drawable(drawable);
    javaBuilder.count(count);
    javaBuilder.attachments(attachments);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(count);
    for(AttachFormat t : attachments) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(attachments);
  }

  public static class GetBuffersWithFormatBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(attachments);
    }
  }
}
