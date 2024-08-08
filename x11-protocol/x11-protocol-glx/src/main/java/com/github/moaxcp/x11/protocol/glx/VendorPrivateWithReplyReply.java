package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class VendorPrivateWithReplyReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int retval;

  @NonNull
  private ByteList data1;

  @NonNull
  private ByteList data2;

  public static VendorPrivateWithReplyReply readVendorPrivateWithReplyReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    VendorPrivateWithReplyReply.VendorPrivateWithReplyReplyBuilder javaBuilder = VendorPrivateWithReplyReply.builder();
    int length = in.readCard32();
    int retval = in.readCard32();
    ByteList data1 = in.readByte(24);
    ByteList data2 = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.retval(retval);
    javaBuilder.data1(data1.toImmutable());
    javaBuilder.data2(data2.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    int length = data2.size();
    out.writeCard32(getLength());
    out.writeCard32(retval);
    out.writeByte(data1);
    out.writeByte(data2);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * data1.size() + 1 * data2.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class VendorPrivateWithReplyReplyBuilder {
    public int getSize() {
      return 12 + 1 * data1.size() + 1 * data2.size();
    }
  }
}
