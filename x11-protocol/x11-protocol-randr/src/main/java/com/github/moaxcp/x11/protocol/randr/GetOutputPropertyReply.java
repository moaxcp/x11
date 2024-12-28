package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetOutputPropertyReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private byte format;

  private short sequenceNumber;

  private int type;

  private int bytesAfter;

  private int numItems;

  @NonNull
  private List<Byte> data;

  public static GetOutputPropertyReply readGetOutputPropertyReply(byte format, short sequenceNumber,
      X11Input in) throws IOException {
    GetOutputPropertyReply.GetOutputPropertyReplyBuilder javaBuilder = GetOutputPropertyReply.builder();
    int length = in.readCard32();
    int type = in.readCard32();
    int bytesAfter = in.readCard32();
    int numItems = in.readCard32();
    byte[] pad7 = in.readPad(12);
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(numItems) * (Byte.toUnsignedInt(format) / 8)));
    javaBuilder.format(format);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.type(type);
    javaBuilder.bytesAfter(bytesAfter);
    javaBuilder.numItems(numItems);
    javaBuilder.data(data);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(format);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(type);
    out.writeCard32(bytesAfter);
    out.writeCard32(numItems);
    out.writePad(12);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetOutputPropertyReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
