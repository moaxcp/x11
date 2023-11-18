package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetKeyboardControlReply implements XReply, XprotoObject {
  private byte globalAutoRepeat;

  private short sequenceNumber;

  private int ledMask;

  private byte keyClickPercent;

  private byte bellPercent;

  private short bellPitch;

  private short bellDuration;

  @NonNull
  private List<Byte> autoRepeats;

  public static GetKeyboardControlReply readGetKeyboardControlReply(byte globalAutoRepeat,
      short sequenceNumber, X11Input in) throws IOException {
    GetKeyboardControlReply.GetKeyboardControlReplyBuilder javaBuilder = GetKeyboardControlReply.builder();
    int length = in.readCard32();
    int ledMask = in.readCard32();
    byte keyClickPercent = in.readCard8();
    byte bellPercent = in.readCard8();
    short bellPitch = in.readCard16();
    short bellDuration = in.readCard16();
    byte[] pad9 = in.readPad(2);
    List<Byte> autoRepeats = in.readCard8(32);
    javaBuilder.globalAutoRepeat(globalAutoRepeat);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.ledMask(ledMask);
    javaBuilder.keyClickPercent(keyClickPercent);
    javaBuilder.bellPercent(bellPercent);
    javaBuilder.bellPitch(bellPitch);
    javaBuilder.bellDuration(bellDuration);
    javaBuilder.autoRepeats(autoRepeats);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(globalAutoRepeat);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(ledMask);
    out.writeCard8(keyClickPercent);
    out.writeCard8(bellPercent);
    out.writeCard16(bellPitch);
    out.writeCard16(bellDuration);
    out.writePad(2);
    out.writeCard8(autoRepeats);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 20 + 1 * autoRepeats.size();
  }

  public static class GetKeyboardControlReplyBuilder {
    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder globalAutoRepeat(
        AutoRepeatMode globalAutoRepeat) {
      this.globalAutoRepeat = (byte) globalAutoRepeat.getValue();
      return this;
    }

    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder globalAutoRepeat(
        byte globalAutoRepeat) {
      this.globalAutoRepeat = globalAutoRepeat;
      return this;
    }

    public int getSize() {
      return 20 + 1 * autoRepeats.size();
    }
  }
}
