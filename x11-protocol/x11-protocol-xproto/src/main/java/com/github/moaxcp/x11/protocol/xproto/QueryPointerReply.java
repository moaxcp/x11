package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryPointerReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private boolean sameScreen;

  private short sequenceNumber;

  private int root;

  private int child;

  private short rootX;

  private short rootY;

  private short winX;

  private short winY;

  private short mask;

  public static QueryPointerReply readQueryPointerReply(byte sameScreen, short sequenceNumber,
      X11Input in) throws IOException {
    QueryPointerReply.QueryPointerReplyBuilder javaBuilder = QueryPointerReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    int child = in.readCard32();
    short rootX = in.readInt16();
    short rootY = in.readInt16();
    short winX = in.readInt16();
    short winY = in.readInt16();
    short mask = in.readCard16();
    byte[] pad11 = in.readPad(2);
    in.readPad(4);
    javaBuilder.sameScreen(sameScreen > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.child(child);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.winX(winX);
    javaBuilder.winY(winY);
    javaBuilder.mask(mask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(sameScreen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(root);
    out.writeCard32(child);
    out.writeInt16(rootX);
    out.writeInt16(rootY);
    out.writeInt16(winX);
    out.writeInt16(winY);
    out.writeCard16(mask);
    out.writePad(2);
  }

  public boolean isMaskEnabled(@NonNull KeyButMask... maskEnums) {
    for(KeyButMask m : maskEnums) {
      if(!m.isEnabled(mask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryPointerReplyBuilder {
    public boolean isMaskEnabled(@NonNull KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        if(!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public QueryPointerReply.QueryPointerReplyBuilder maskEnable(KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        mask((short) m.enableFor(mask));
      }
      return this;
    }

    public QueryPointerReply.QueryPointerReplyBuilder maskDisable(KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        mask((short) m.disableFor(mask));
      }
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
