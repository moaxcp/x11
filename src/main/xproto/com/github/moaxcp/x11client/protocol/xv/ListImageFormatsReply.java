package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ListImageFormatsReply implements XReply, XvObject {
  private short sequenceNumber;

  @NonNull
  private List<ImageFormatInfo> format;

  public static ListImageFormatsReply readListImageFormatsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    ListImageFormatsReply.ListImageFormatsReplyBuilder javaBuilder = ListImageFormatsReply.builder();
    int length = in.readCard32();
    int numFormats = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<ImageFormatInfo> format = new ArrayList<>((int) (Integer.toUnsignedLong(numFormats)));
    for(int i = 0; i < Integer.toUnsignedLong(numFormats); i++) {
      format.add(ImageFormatInfo.readImageFormatInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.format(format);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int numFormats = format.size();
    out.writeCard32(numFormats);
    out.writePad(20);
    for(ImageFormatInfo t : format) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(format);
  }

  public static class ListImageFormatsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(format);
    }
  }
}
