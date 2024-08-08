package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SAActionMessage implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte flags;

  @NonNull
  private ByteList message;

  public static SAActionMessage readSAActionMessage(X11Input in) throws IOException {
    SAActionMessage.SAActionMessageBuilder javaBuilder = SAActionMessage.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    ByteList message = in.readCard8(6);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.message(message.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(message);
  }

  public boolean isFlagsEnabled(@NonNull ActionMessageFlag... maskEnums) {
    for(ActionMessageFlag m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 2 + 1 * message.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SAActionMessageBuilder {
    public SAActionMessage.SAActionMessageBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SAActionMessage.SAActionMessageBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull ActionMessageFlag... maskEnums) {
      for(ActionMessageFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SAActionMessage.SAActionMessageBuilder flagsEnable(ActionMessageFlag... maskEnums) {
      for(ActionMessageFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SAActionMessage.SAActionMessageBuilder flagsDisable(ActionMessageFlag... maskEnums) {
      for(ActionMessageFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 2 + 1 * message.size();
    }
  }
}
