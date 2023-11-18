package com.github.moaxcp.x11client.protocol.xproto;

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
public class ListExtensionsReply implements XReply, XprotoObject {
  private short sequenceNumber;

  @NonNull
  private List<Str> names;

  public static ListExtensionsReply readListExtensionsReply(byte namesLen, short sequenceNumber,
      X11Input in) throws IOException {
    ListExtensionsReply.ListExtensionsReplyBuilder javaBuilder = ListExtensionsReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    List<Str> names = new ArrayList<>(Byte.toUnsignedInt(namesLen));
    for(int i = 0; i < Byte.toUnsignedInt(namesLen); i++) {
      names.add(Str.readStr(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.names(names);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte namesLen = (byte) names.size();
    out.writeCard8(namesLen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
    for(Str t : names) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(names);
  }

  public static class ListExtensionsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(names);
    }
  }
}
