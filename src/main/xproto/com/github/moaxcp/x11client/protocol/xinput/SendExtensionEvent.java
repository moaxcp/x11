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
public class SendExtensionEvent implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 31;

  private int destination;

  private byte deviceId;

  private boolean propagate;

  @NonNull
  private List<EventForSendEventStruct> events;

  @NonNull
  private List<Integer> classes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SendExtensionEvent readSendExtensionEvent(X11Input in) throws IOException {
    SendExtensionEvent.SendExtensionEventBuilder javaBuilder = SendExtensionEvent.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int destination = in.readCard32();
    byte deviceId = in.readCard8();
    boolean propagate = in.readBool();
    short numClasses = in.readCard16();
    byte numEvents = in.readCard8();
    byte[] pad8 = in.readPad(3);
    List<EventForSendEventStruct> events = new ArrayList<>(Byte.toUnsignedInt(numEvents));
    for(int i = 0; i < Byte.toUnsignedInt(numEvents); i++) {
      events.add(EventForSendEventStruct.readEventForSendEventStruct(in));
    }
    List<Integer> classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.destination(destination);
    javaBuilder.deviceId(deviceId);
    javaBuilder.propagate(propagate);
    javaBuilder.events(events);
    javaBuilder.classes(classes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(destination);
    out.writeCard8(deviceId);
    out.writeBool(propagate);
    short numClasses = (short) classes.size();
    out.writeCard16(numClasses);
    byte numEvents = (byte) events.size();
    out.writeCard8(numEvents);
    out.writePad(3);
    for(EventForSendEventStruct t : events) {
      t.write(out);
    }
    out.writeCard32(classes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + XObject.sizeOf(events) + 4 * classes.size();
  }

  public static class SendExtensionEventBuilder {
    public int getSize() {
      return 16 + XObject.sizeOf(events) + 4 * classes.size();
    }
  }
}
