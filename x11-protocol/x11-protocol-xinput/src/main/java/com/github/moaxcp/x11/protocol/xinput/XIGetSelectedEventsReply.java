package com.github.moaxcp.x11.protocol.xinput;

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
public class XIGetSelectedEventsReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private short sequenceNumber;

  @NonNull
  private ImmutableList<EventMask> masks;

  public static XIGetSelectedEventsReply readXIGetSelectedEventsReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    XIGetSelectedEventsReply.XIGetSelectedEventsReplyBuilder javaBuilder = XIGetSelectedEventsReply.builder();
    int length = in.readCard32();
    short numMasks = in.readCard16();
    byte[] pad5 = in.readPad(22);
    MutableList<EventMask> masks = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(numMasks));
    for(int i = 0; i < Short.toUnsignedInt(numMasks); i++) {
      masks.add(EventMask.readEventMask(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.masks(masks.toImmutable());
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
    short numMasks = (short) masks.size();
    out.writeCard16(numMasks);
    out.writePad(22);
    for(EventMask t : masks) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(masks);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIGetSelectedEventsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(masks);
    }
  }
}
