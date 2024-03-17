package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetBuffersWithFormat implements TwoWayRequest<GetBuffersWithFormatReply> {
  public static final String PLUGIN_NAME = "dri2";

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
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int count = in.readCard32();
    javaStart += 4;
    List<AttachFormat> attachments = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetBuffersWithFormatBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(attachments);
    }
  }
}
