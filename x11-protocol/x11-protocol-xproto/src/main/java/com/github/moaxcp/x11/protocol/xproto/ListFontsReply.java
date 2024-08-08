package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class ListFontsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private ImmutableList<Str> names;

  public static ListFontsReply readListFontsReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    ListFontsReply.ListFontsReplyBuilder javaBuilder = ListFontsReply.builder();
    int length = in.readCard32();
    short namesLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    MutableList<Str> names = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(namesLen));
    for(int i = 0; i < Short.toUnsignedInt(namesLen); i++) {
      names.add(Str.readStr(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.names(names.toImmutable());
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
    out.writeCard32(getLength());
    short namesLen = (short) names.size();
    out.writeCard16(namesLen);
    out.writePad(22);
    for(Str t : names) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(names);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListFontsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(names);
    }
  }
}
