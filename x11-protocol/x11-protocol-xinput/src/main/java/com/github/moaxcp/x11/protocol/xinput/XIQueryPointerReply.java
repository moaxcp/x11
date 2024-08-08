package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class XIQueryPointerReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private short sequenceNumber;

  private int root;

  private int child;

  private int rootX;

  private int rootY;

  private int winX;

  private int winY;

  private boolean sameScreen;

  @NonNull
  private ModifierInfo mods;

  @NonNull
  private GroupInfo group;

  @NonNull
  private IntList buttons;

  public static XIQueryPointerReply readXIQueryPointerReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    XIQueryPointerReply.XIQueryPointerReplyBuilder javaBuilder = XIQueryPointerReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    int child = in.readCard32();
    int rootX = in.readInt32();
    int rootY = in.readInt32();
    int winX = in.readInt32();
    int winY = in.readInt32();
    boolean sameScreen = in.readBool();
    byte[] pad11 = in.readPad(1);
    short buttonsLen = in.readCard16();
    ModifierInfo mods = ModifierInfo.readModifierInfo(in);
    GroupInfo group = GroupInfo.readGroupInfo(in);
    IntList buttons = in.readCard32(Short.toUnsignedInt(buttonsLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.child(child);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.winX(winX);
    javaBuilder.winY(winY);
    javaBuilder.sameScreen(sameScreen);
    javaBuilder.mods(mods);
    javaBuilder.group(group);
    javaBuilder.buttons(buttons.toImmutable());
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
    out.writeCard32(root);
    out.writeCard32(child);
    out.writeInt32(rootX);
    out.writeInt32(rootY);
    out.writeInt32(winX);
    out.writeInt32(winY);
    out.writeBool(sameScreen);
    out.writePad(1);
    short buttonsLen = (short) buttons.size();
    out.writeCard16(buttonsLen);
    mods.write(out);
    group.write(out);
    out.writeCard32(buttons);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 56 + 4 * buttons.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIQueryPointerReplyBuilder {
    public int getSize() {
      return 56 + 4 * buttons.size();
    }
  }
}
