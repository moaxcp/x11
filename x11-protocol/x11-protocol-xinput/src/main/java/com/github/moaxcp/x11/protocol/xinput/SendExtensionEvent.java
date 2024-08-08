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
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class SendExtensionEvent implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 31;

  private int destination;

  private byte deviceId;

  private boolean propagate;

  @NonNull
  private ImmutableList<EventForSendEventStruct> events;

  @NonNull
  private IntList classes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SendExtensionEvent readSendExtensionEvent(X11Input in) throws IOException {
    SendExtensionEvent.SendExtensionEventBuilder javaBuilder = SendExtensionEvent.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int destination = in.readCard32();
    byte deviceId = in.readCard8();
    boolean propagate = in.readBool();
    short numClasses = in.readCard16();
    byte numEvents = in.readCard8();
    byte[] pad8 = in.readPad(3);
    MutableList<EventForSendEventStruct> events = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(numEvents));
    for(int i = 0; i < Byte.toUnsignedInt(numEvents); i++) {
      events.add(EventForSendEventStruct.readEventForSendEventStruct(in));
    }
    IntList classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.destination(destination);
    javaBuilder.deviceId(deviceId);
    javaBuilder.propagate(propagate);
    javaBuilder.events(events.toImmutable());
    javaBuilder.classes(classes.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SendExtensionEventBuilder {
    public int getSize() {
      return 16 + XObject.sizeOf(events) + 4 * classes.size();
    }
  }
}
