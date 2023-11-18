package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SAActionMessage implements ActionUnion, XStruct, XkbObject {
  private byte type;

  private byte flags;

  @NonNull
  private List<Byte> message;

  public static SAActionMessage readSAActionMessage(X11Input in) throws IOException {
    SAActionMessage.SAActionMessageBuilder javaBuilder = SAActionMessage.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    List<Byte> message = in.readCard8(6);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.message(message);
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
