package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class XISelectEvents implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 46;

  private int window;

  @NonNull
  private List<EventMask> masks;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XISelectEvents readXISelectEvents(X11Input in) throws IOException {
    XISelectEvents.XISelectEventsBuilder javaBuilder = XISelectEvents.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    short numMask = in.readCard16();
    byte[] pad5 = in.readPad(2);
    List<EventMask> masks = new ArrayList<>(Short.toUnsignedInt(numMask));
    for(int i = 0; i < Short.toUnsignedInt(numMask); i++) {
      masks.add(EventMask.readEventMask(in));
    }
    javaBuilder.window(window);
    javaBuilder.masks(masks);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class XISelectEventsBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(masks);
    }
  }
}
