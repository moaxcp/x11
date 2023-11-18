package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GrabKeyboard implements TwoWayRequest<GrabKeyboardReply>, XprotoObject {
  public static final byte OPCODE = 31;

  private boolean ownerEvents;

  private int grabWindow;

  private int time;

  private byte pointerMode;

  private byte keyboardMode;

  public XReplyFunction<GrabKeyboardReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GrabKeyboardReply.readGrabKeyboardReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabKeyboard readGrabKeyboard(X11Input in) throws IOException {
    GrabKeyboard.GrabKeyboardBuilder javaBuilder = GrabKeyboard.builder();
    boolean ownerEvents = in.readBool();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    int time = in.readCard32();
    byte pointerMode = in.readByte();
    byte keyboardMode = in.readByte();
    byte[] pad7 = in.readPad(2);
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.time(time);
    javaBuilder.pointerMode(pointerMode);
    javaBuilder.keyboardMode(keyboardMode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(ownerEvents);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard32(time);
    out.writeByte(pointerMode);
    out.writeByte(keyboardMode);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class GrabKeyboardBuilder {
    public GrabKeyboard.GrabKeyboardBuilder pointerMode(GrabMode pointerMode) {
      this.pointerMode = (byte) pointerMode.getValue();
      return this;
    }

    public GrabKeyboard.GrabKeyboardBuilder pointerMode(byte pointerMode) {
      this.pointerMode = pointerMode;
      return this;
    }

    public GrabKeyboard.GrabKeyboardBuilder keyboardMode(GrabMode keyboardMode) {
      this.keyboardMode = (byte) keyboardMode.getValue();
      return this;
    }

    public GrabKeyboard.GrabKeyboardBuilder keyboardMode(byte keyboardMode) {
      this.keyboardMode = keyboardMode;
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
