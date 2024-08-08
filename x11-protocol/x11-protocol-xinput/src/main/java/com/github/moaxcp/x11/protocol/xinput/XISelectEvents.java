package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class XISelectEvents implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 46;

  private int window;

  @NonNull
  private ImmutableList<EventMask> masks;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XISelectEvents readXISelectEvents(X11Input in) throws IOException {
    XISelectEvents.XISelectEventsBuilder javaBuilder = XISelectEvents.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    short numMask = in.readCard16();
    byte[] pad5 = in.readPad(2);
    MutableList<EventMask> masks = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(numMask));
    for(int i = 0; i < Short.toUnsignedInt(numMask); i++) {
      masks.add(EventMask.readEventMask(in));
    }
    javaBuilder.window(window);
    javaBuilder.masks(masks.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    short numMask = (short) masks.size();
    out.writeCard16(numMask);
    out.writePad(2);
    for(EventMask t : masks) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(masks);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XISelectEventsBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(masks);
    }
  }
}
