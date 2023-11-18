package com.github.moaxcp.x11client.protocol.xinput;

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
public class XIPassiveGrabDeviceReply implements XReply, XinputObject {
  private short sequenceNumber;

  @NonNull
  private List<GrabModifierInfo> modifiers;

  public static XIPassiveGrabDeviceReply readXIPassiveGrabDeviceReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    XIPassiveGrabDeviceReply.XIPassiveGrabDeviceReplyBuilder javaBuilder = XIPassiveGrabDeviceReply.builder();
    int length = in.readCard32();
    short numModifiers = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<GrabModifierInfo> modifiers = new ArrayList<>(Short.toUnsignedInt(numModifiers));
    for(int i = 0; i < Short.toUnsignedInt(numModifiers); i++) {
      modifiers.add(GrabModifierInfo.readGrabModifierInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.modifiers(modifiers);
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
    short numModifiers = (short) modifiers.size();
    out.writeCard16(numModifiers);
    out.writePad(22);
    for(GrabModifierInfo t : modifiers) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(modifiers);
  }

  public static class XIPassiveGrabDeviceReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(modifiers);
    }
  }
}
